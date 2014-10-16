package de.versatel.noc.vsafe.server.core.security.users;

import de.versatel.noc.vSafe_Server.database.MySQL_PoolHandling;
import de.versatel.noc.vSafe_Server.database.sql.SQLPermissions;
import de.versatel.noc.vSafe_Server.database.sql.SQLUser;
import de.versatel.noc.vSafe_Server.database.sql.SQLUserGroup;
import de.versatel.noc.vSafe_Server.system.SystemManager;
import de.versatel.noc.vSafe.rmi.ClientInterface;
//import de.versatel.noc.vSafe.rmi.NotificationObject;
import de.versatel.noc.vSafe.rmi.ServerRequestObject;
import de.versatel.noc.vSafe.rmi.ServerResultObject;
import de.versatel.noc.vSafe.services.Service;
import de.versatel.noc.vSafe.services.ServiceRequestObject;
import de.versatel.noc.vSafe.services.userService.UserGroupDTO;
import de.versatel.noc.vSafe.services.userService.UserDTO;
import de.versatel.noc.vSafe.system.exceptions.ExceptionCodes;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.List;

public class UserHandling {

    //private MySQL_PoolHandling MySQL_PoolHandling;
    private SystemManager systemManager;
    private SQLPermissions sQLPermissions;
    private SQLUserGroup sQLUserGroup;
    private SQLUser sQLUser;
    private List<Service> services;
    private List<RoleImpl> userGroups = new ArrayList<RoleImpl>();
    private List<User> users = new ArrayList<User>();
    

    /**
     *
     */
    public UserHandling(SystemManager systemManager) {
        this.systemManager = systemManager;
        services = systemManager.getServiceHandling().getServices();
        sQLUserGroup = new SQLUserGroup(systemManager.getMySQL_PoolHandling());
        sQLUser = new SQLUser(systemManager.getMySQL_PoolHandling());
        sQLPermissions = new SQLPermissions(systemManager.getMySQL_PoolHandling());
    }

    /**
     *
     * @param mySQL_ConnectionPool
     * @param lineSeperator
     * @param actionLogActive
     * @param systemLogging
     */
    public void init() {
        if (systemManager.getMySQL_PoolHandling().getPoolstate().equals(MySQL_PoolHandling.PoolState.CONNECTED)) {
            List<UserGroupDTO> ugdtos;
            try {
                ugdtos = sQLUserGroup.getUserGroups();
            } catch (SQLException e) {
                throw new UserException("UserHandling.init", e);
            }
            
            
            if (ugdtos != null && !ugdtos.isEmpty()) {

                for (UserGroupDTO ugdto : ugdtos) {
                    RoleImpl ug = new RoleImpl(this, ugdto);
                    //List<UserDTO> udtos = sQLUser.getUsers();
                    try {
                        List<UserDTO> udtos = sQLUser.getUsers(ug.getUserGroupId());
                        for (UserDTO udto : udtos) {
                            User u = new User(ug, udto);
                            //u.setUserGroup(ug);
                            users.add(u);
                        }
                    } catch (SQLException e) {
                    }
                    sQLUserGroup.getUserPermissions(ug.getUserGroupId());
                }
            }
        }
    }

    public void setServices(List<Service> services) {
        this.services = services;
        for (RoleImpl ug : userGroups) {
            ug.setServices(services);
        }
    }

    /**
     * Geschäftslogik, um einen Client-User anzumelden, und die Anmeldedaten in
     * die Datenbank einzutragen.
     *
     * @param username
     * @param password
     * @param ClientInterface
     * @return User mit den geänderten Daten
     *
     */
    public ServerResultObject login(String username, String password, ClientInterface session) {

        User au = getUser(username);
        if (au instanceof User) {
            User user = (User) au;

            // User nicht vorhanden ? -> return
            if (user == null) {
                throw new UserException("UserHandling.login", ExceptionCodes.USER_NOUSER, "Benutzer '" + username + "' nicht vorhanden");
            }

            // Password falsch ? -> return ?
            if (!user.isPasswordValid(password)) {
                user.increaseWrongPasswordCounter();

                // Password-Limit erreicht ?
                if (user.isWrongLoginLimitReached()) {
                    user.setState(User.Userstate.MAXWRONGPASSWORDS);
                    throw new UserException("UserHandling.login", ExceptionCodes.USER_MAXWRONGPASSWORDS);
                } else {// max.fehlerhafte_Logins_nicht_erreicht.
                    user.setState(User.Userstate.WRONGPASSWORD);
                    throw new UserException("UserHandling.login", ExceptionCodes.USER_WRONGPASSWORD);
                }
            }

            // Status gültig ?
            if (user.getState() == User.Userstate.USERADMINBARRED) {
                throw new UserException("UserHandling.login", ExceptionCodes.USER_USERADMINBARRED);
            } else if (user.getState() == User.Userstate.USERSYSTEMBARRED) {
                throw new UserException("UserHandling.login", ExceptionCodes.USER_USERSYSTEMBARRED);
            } else if (user.getState() == User.Userstate.MAXWRONGPASSWORDS) {
                throw new UserException("UserHandling.login)", ExceptionCodes.USER_MAXWRONGPASSWORDS);
            } else if (user.getState() == User.Userstate.SESSIONLIMIT) {
                throw new UserException("UserHandling.login", ExceptionCodes.USER_SESSIONLIMIT);
            }

            // max Anzahl gleichzeitiger Sessions erreicht ?
            int sessions = getUser_SessionCount(user.getUserid());
            if (sessions >= user.getMaxSessions()) {
                user.setState(User.Userstate.SESSIONLIMIT);
                throw new UserException("UserHandling.login", ExceptionCodes.USER_SESSIONLIMIT);
            }

            // Alles ok - es wird eingeloggt.
            user.clearWrongPasswordCounter();
            String sessionID = user.addSession(session);
            user.setState(User.Userstate.LOGGEDIN);

            return new ServerResultObject(ServerResultObject.ResultType.RESULT, sessionID);
        }
        return null;
    }

    /**
     * Geschaeftslogik, um einen Client-User auszuloggen, und die Abmeldedaten in
     * die Datenbank einzutragen.
     *
     * @param userId
     * @param SessionId
     * @return int Status des Abmeldeversuchs
     *
     */
    public ServerResultObject logout(String SessionId) {
        for (RoleImpl aug : userGroups) {
            if (aug instanceof RoleImpl) {
                RoleImpl ug = (RoleImpl) aug;
                if (ug.hasUsers()) {
                    for (User u : ug.getUsers()) {
                        if (u.hasSessions()) {
                            for (UserSession us : u.getSessions()) {
                                if (us.getSessionid().equals(SessionId)) {
                                    u.removeSession(us);
                                    us = null;
                                    throw new UserException("UserHandling.logout", ExceptionCodes.USER_LOGGEDOUT);
                                }
                            }
                        }
                    }
                }
            }
            throw new UserException("UserHandling.logout", ExceptionCodes.USER_LOGOUTERROR);
        }
        return null;
    }

    public void addUserGroup(String name, String remark) {
        if (name == null || name.isEmpty()) {
            throw new UserException("UserHandling.addUserGroup", ExceptionCodes.USERGROUP_WRONGGROUPNAME);
        }
        if (remark == null) {
            remark = "";
        }

        RoleImpl ug = new RoleImpl(this);
        ug.setName(name);
        ug.setRemark(remark);
        int result = sQLUserGroup.add(ug);
        if (result >= 0) {
            int index = sQLUserGroup.getUserGroupId(name);
            if (index >= 0) {
                ug.setUserGroupId(index);
                if (!userGroups.add(ug)) {
                    sQLUserGroup.remove(index);
                    throw new UserException("UserHandling.addUserGroup", ExceptionCodes.USERGROUP_NOTADDED);
                }
            } else {
                sQLUserGroup.remove(name);
            }
        }
    }

    public void removeUserGroup(RoleImpl ug) throws UserException {
        if (ug == null) {
            throw new UserException("UserHandling.removeUserGroup", ExceptionCodes.USERGROUP_NOUSERGROUP);
        }
        if (ug.hasUsers()) {
            throw new UserException("UserHandling.removeUserGroup", ExceptionCodes.USERGROUP_HASUSERS);
        }

        ug.removeServicePermissions();
        sQLUserGroup.remove(ug.getUserGroupId());
        try {
            userGroups.remove(ug);
        } catch (ClassCastException cce) {
            throw new UserException("UserHandling.removeUserGroup", ExceptionCodes.GENERAL_CLASSCASTEXCEPTION, cce);
        } catch (NullPointerException npe) {
            throw new UserException("UserHandling.removeUserGroup", ExceptionCodes.GENERAL_NULLPOINTEREXCEPTION, npe);
        } catch (UnsupportedOperationException uoe) {
            throw new UserException("UserHandling.removeUserGroup", ExceptionCodes.GENERAL_UNSUPPURTEDOPERATIONEXCEPTION, uoe);
        }

    }

    /**
     *
     * @param userGroupId
     * @return
     */
    public RoleImpl getUserGroup(int userGroupId) {
        for (RoleImpl ug : userGroups) {
            if (ug.getUserGroupId() == userGroupId) {
                return ug;
            }
        }
        return null;
    }

    /**
     *
     * @param timestamp
     * @return
     */
    public Date sqlTimestampToDate(Timestamp timestamp) {
        Date date = new java.util.Date(timestamp.getTime());
        return date;
    }

    /**
     *
     * @param date
     * @return
     */
    public Timestamp dateToSqlTimestamp(java.util.Date date) {
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }

    public int getUser_SessionCount(int userid) {
        if (!hasUserGroups()) {
            return 0;
        }

        for (RoleImpl ug : userGroups) {
            if (ug.hasUsers()) {
                for (User u : ug.getUsers()) {
                    if (u.getUserid() == userid) {
                        return u.getSessions().size();
                    }
                }
            }
        }
        return 0;
    }

    public boolean hasUserGroups() {
        if (userGroups.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param userId
     * @return
     */
    public boolean isUserBarred(int userId) {
        if (!hasUserGroups()) {
            return true;
        }
        for (RoleImpl ug : userGroups) {
            if (ug.hasUsers()) {
                for (User u : ug.getUsers()) {
                    if (u.getUserid() == userId) {
                        if (u.getState() == User.Userstate.USERADMINBARRED
                                || u.getState() == User.Userstate.USERSYSTEMBARRED
                                || u.getState() == User.Userstate.MAXWRONGPASSWORDS
                                || u.getState() == User.Userstate.SESSIONLIMIT) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public ServerResultObject checkPermission(String sessionId, ServiceRequestObject sReqObj) {
        UserSession us = getSession(sessionId);
        if (us == null) {
            throw new UserException("UserHandling.checkPermission", ExceptionCodes.SERVICE_METHODNOTPERMITTED);
        }

        if (!us.getUser().getUserGroup().isPermitted(sReqObj.getServiceId(), sReqObj.getMethodID())) {
            throw new UserException("UserHandling.checkPermission", ExceptionCodes.SERVICE_METHODNOTPERMITTED);
        }
        return null;
    }

    public ServerResultObject checkSession(ServerRequestObject sReqObj) throws UserException {
        UserSession aus = getSession(sReqObj.getSessionId());
        if (aus instanceof UserSession) {
            UserSession us = (UserSession) aus;
            if (us == null) {
                throw new UserException("UserHandling.checkSession", ExceptionCodes.SESSION_SESSIONIDNOTFOUND);
            }
            if (us.hasExpired()) {
                throw new UserException("UserHandling.checkSession", ExceptionCodes.SESSION_HASEXPIRED);
            }

            sReqObj.setOriginUserName(us.getUser().getUsername());
            try {

                sReqObj.setOriginIP(us.getRmiClient().getIPV4());
                sReqObj.setOriginHost(us.getRmiClient().getLocalHostName());
            } catch (RemoteException e) {
                throw new UserException("UserHandling.checkSession", ExceptionCodes.SESSION_WRONGHOSTORIP);

            }
        }
        return null;
    }

    /**
     * 
     */
    public void cleanUserSessions() {
        for (RoleImpl ug : userGroups) {
            if (ug.hasUsers()) {
                for (User u : ug.getUsers()) {
                    if (u.hasSessions()) {
                        for (UserSession us : u.getSessions()) {
                            if (us == null) {
                                u.removeSession(us);
                            } else {

                                if (us.hasExpired()) {
                                    u.removeSession(us);
                                    us.setRmiClient(null);
                                    us = null;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @return
     */
    public List<User> getUsers() {
        List us = new ArrayList();
        for (RoleImpl ug : userGroups) {
            if (ug.hasUsers()) {
                for (User u : ug.getUsers()) {
                    us.add(u);
                }
            }
        }
        return users;
    }

    public User getUser(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public User getUser(int userId) {
        for (User u : users) {
            if (u.getUserid() == userId) {
                return u;
            }
        }
        return null;
    }

    public UserSession getSession(String sessionid) {
        for (RoleImpl ug : userGroups) {
            if (ug.hasUsers()) {
                for (User u : ug.getUsers()) {
                    if (u.hasSessions()) {
                        for (UserSession us : u.getSessions()) {
                            if (us.getSessionid().equals(sessionid)) {
                                return us;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private Service getService(long serviceId) {
        for (Service service : services) {
            if (service.getServiceId() == serviceId) {
                return service;
            }

        }
        return null;
    }

    public void setUserGroup_Name(int userGroupId, String s) {
        RoleImpl ug = getUserGroup(userGroupId);
        if (ug != null) {
            ug.setName(s);
        }
    }
    // über RMI, GUI

    public void setUserGroup_Remark(int userGroupId, String s) {
        RoleImpl ug = getUserGroup(userGroupId);
        if (ug != null) {
            ug.setRemark(s);
        }
    }

    // über RMI, GUI
    public int getUserGroup_UserGroupId(int userGroupId) {
        RoleImpl ug = getUserGroup(userGroupId);
        if (ug != null) {
            return ug.getUserGroupId();
        }
        return -1;
    }
    // über RMI, GUI

    public String getUserGroup_Name(int userGroupId) {
        RoleImpl ug = getUserGroup(userGroupId);
        if (ug != null) {
            return ug.getName();
        }
        return null;
    }
    // über RMI, GUI

    public String getUserGroup_Remark(int userGroupId) {
        RoleImpl ug = getUserGroup(userGroupId);
        if (ug != null) {
            return ug.getRemark();
        }
        return null;
    }

    public BitSet getUserGroup_PermissionBits(int userGroupId, int serviceId) {
        RoleImpl ug = getUserGroup(userGroupId);
        if (ug != null) {
            return ug.getPermissionBits(serviceId);
        }
        return null;
    }

    public void setUserGroup_PermissionBit(int userGroupId, int serviceId, int index, boolean value) {
        RoleImpl ug = getUserGroup(userGroupId);
        if (ug != null) {
            ug.setPermissionBit(serviceId, index, value);
        }
    }

    public boolean addUser(int userGroupId, User user) {
        RoleImpl ug = getUserGroup(userGroupId);
        if (ug != null && user != null) {
            ug.addUser(user);
            return true;
        }
        return false;
    }

    public boolean removeUser(int userGroupId, User user) {
        RoleImpl ug = getUserGroup(userGroupId);
        if (ug != null && user != null) {
            ug.removeUser(user);
            return true;
        }
        return false;
    }

    public List<User> getUserGroup_Users(int userGroupId) {
        RoleImpl ug = getUserGroup(userGroupId);
        if (ug != null) {
            return ug.getUsers();
        }
        return null;
    }

    public boolean hasUserGroup_Users(int userGroupId) {
        RoleImpl ug = getUserGroup(userGroupId);
        if (ug != null) {
            return ug.hasUsers();
        }
        return false;
    }

    public void setUser_Username(int userId, String s) {
        User u = getUser(userId);
        if (u != null) {
            u.setUsername(s);
        }
    }

    public void setUser_Name(int userId, String s) {
        User u = getUser(userId);
        if (u != null) {
            u.setName(s);
        }
    }

    public void setUser_Firstname(int userId, String s) {
        User u = getUser(userId);
        if (u != null) {
            u.setFirstname(s);
        }
    }

    public void setUser_Password(int userId, String s) {
        User u = getUser(userId);
        if (u != null) {
            u.setPassword(s);
        }
    }

    public void setUser_UserGroup(int userId, RoleImpl ug) {
        User u = getUser(userId);
        if (u != null) {
            u.setUserGroup(ug);
        }
    }

    public void setUser_State(int userId, User.Userstate newState) {
        User u = getUser(userId);
        if (u != null) {
            u.setState(newState);
        }
    }

    public void setUser_ActWrongLogins(int userId, int i) {
        User u = getUser(userId);
        if (u != null) {
            u.setActWrongLogins(i);
        }
    }

    public void setUser_MaxWrongLogins(int userId, int i) {
        User u = getUser(userId);
        if (u != null) {
            u.setMaxWrongLogins(i);
        }
    }

    public void setUser_MaxSessions(int userId, int i) {
        User u = getUser(userId);
        if (u != null) {
            u.setMaxSessions(i);
        }
    }

    public void setUser_SessionInactivityTimeout(int userId, long l) {
        User u = getUser(userId);
        if (u != null) {
            u.setSessionInactivityTo(l);
        }
    }

    public void setUser_SessionLifetime(int userId, long l) {
        User u = getUser(userId);
        if (u != null) {
            u.setSessionLifeTime(l);
        }
    }

    public int getUser_UserId(int userId) throws UserException {
        User u = getUser(userId);
        if (u != null) {
            return u.getUserid();
        }
        throw new UserException("UserHandling.getUser_UserId", ExceptionCodes.USER_NOUSER);
    }

    public String getUser_Username(int userId) {
        User u = getUser(userId);
        if (u != null) {
            return u.getUsername();
        }
        return null;
    }

    public String getUser_Name(int userId) {
        User u = getUser(userId);
        if (u != null) {
            return u.getName();
        }
        return null;
    }

    public String getUser_Firstname(int userId) {
        User u = getUser(userId);
        if (u != null) {
            return u.getFirstname();
        }
        return null;
    }

    public String getUser_Password(int userId) {
        User u = getUser(userId);
        if (u != null) {
            return u.getPassword();
        }
        return null;
    }

    public RoleImpl getUser_UserGroup(int userId) {
        User u = getUser(userId);
        if (u != null) {
            return u.getUserGroup();
        }
        return null;
    }

    public int getUser_State(int userId) throws UserException {
        User u = getUser(userId);
        if (u != null) {
            u.getState();
        }
        throw new UserException("UserHandling.getUser_State", ExceptionCodes.USER_NOUSER);
    }

    public int getUser_ActWrongLogins(int userId) throws UserException {
        User u = getUser(userId);
        if (u != null) {
            u.getActWrongLogins();
        }
        throw new UserException("UserHandling.getUser_ActWrongLogins", ExceptionCodes.USER_NOUSER);
    }

    public int getUser_MaxWrongLogins(int userId) throws UserException {
        User u = getUser(userId);
        if (u != null) {
            u.getMaxWrongLogins();
        }
        throw new UserException("UserHandling.getUser_MaxWrongLogins", ExceptionCodes.USER_NOUSER);
    }

    public int getUser_MaxSessions(int userId) throws UserException {
        User u = getUser(userId);
        if (u != null) {
            u.getMaxSessions();
        }
        throw new UserException("UserHandling.getUser_MaxSessions", ExceptionCodes.USER_NOUSER);
    }

    public long getUser_SessionInactivityTimeout(int userId) throws UserException {
        User u = getUser(userId);
        if (u != null) {
            u.getSessionInactivityTo();
        }
        throw new UserException("UserHandling.getUser_SessionInactivityTimeout", ExceptionCodes.USER_NOUSER);
    }

    public long getUser_SessionLifetime(int userId) throws UserException {
        User u = getUser(userId);
        if (u != null) {
            u.getSessionLifeTime();
        }
        throw new UserException("UserHandling.getUser_SessionLifetime", ExceptionCodes.USER_NOUSER);
    }

    public String generateUserPassword(int userId, int length) {
        User au = getUser(userId);
        if (au instanceof User) {
            User u = (User) au;
            if (u != null) {
                return u.generateUserPassword(length);
            }
        }
        return null;
    }

    public String cryptUserPassword(int userId, String toCrypt) {
        User au = getUser(userId);
        if (au instanceof User) {
            User u = (User) au;
            if (u != null) {
                return u.cryptUserPassword(toCrypt);
            }
        }
        return null;
    }

    public void setUser_Values(int userId, UserDTO userDTO) throws UserException {
        User u = getUser(userId);
        if (u == null) {
            throw new UserException("UserHandling.setUser_Values", ExceptionCodes.USER_NOUSER);
        }
        u.setUser(userDTO);
    }

    public void setUser_WrongPassword(int userId) {
        User au = getUser(userId);
        if (au instanceof User) {
            User u = (User) au;
            if (u != null) {
                u.increaseWrongPasswordCounter();
            }
        }
    }

    public void setUser_PasswordOk(int userId) {
        User u = getUser(userId);
        if (u != null) {
            u.clearWrongPasswordCounter();
        }
    }

    public boolean isUserPasswordValid(int userId, String input) {
        User au = getUser(userId);
        if (au instanceof User) {
            User u = (User) au;

            if (u != null) {
                return u.isPasswordValid(input);
            }
        }
        return false;
    }

    public void setUserState(int userId, User.Userstate newState) {
        User u = getUser(userId);
        if (u != null) {
            u.setState(newState);
        }
    }

    public boolean hasUser_WrongLoginLimitReached(int userId) {
        User u = getUser(userId);
        if (u != null) {
            return u.isWrongLoginLimitReached();
        }
        return true;
    }

    public void addUser_Session(int userId, ClientInterface session) {
        User au = getUser(userId);
        if (au instanceof User) {
            User u = (User) au;

            if (u != null) {
                u.addSession(session);
            }
        }
    }

    public List<UserSession> getUser_Sessions(int userId) {
        User u = getUser(userId);
        if (u != null) {
            return u.getSessions();
        }
        return null;
    }

    public boolean hasUser_Sessions(int userId) {
        User u = getUser(userId);
        if (u != null) {
            return u.hasSessions();
        }
        return false;
    }

    //UserSession
    public void setSession_SessionId(String sessionid, String newId) {
        UserSession us = getSession(sessionid);
        if (us != null) {
            us.setSessionid(sessionid);
        }
    }

    public void setSession_CreatedAt(String sessionid, long l) {
        UserSession us = getSession(sessionid);
        if (us != null) {
            us.setCreatedAt(l);
        }
    }

    public void setSession_LastTouch(String sessionid, long l) {
        UserSession us = getSession(sessionid);
        if (us != null) {
            us.setLastTouch(l);
        }
    }

    public String getSession_SessionId(String sessionid) {
        UserSession us = getSession(sessionid);
        if (us == null) {
            return null;
        }
        return us.getSessionid();
    }

    public String getSession_LocalHostName(String sessionid) {
        UserSession aus = getSession(sessionid);
        if (aus instanceof UserSession) {
            UserSession us = (UserSession) aus;

            if (us != null) {
                try {
                    return us.getRmiClient().getLocalHostName();
                } catch (RemoteException e) {
                }
            }
        }
        return null;
    }

    public String getSession_Ipv4(String sessionid) {
        UserSession aus = getSession(sessionid);
        if (aus instanceof UserSession) {
            UserSession us = (UserSession) aus;
            if (us != null) {
                try {
                    return us.getRmiClient().getIPV4();
                } catch (RemoteException e) {
                }
            }
        }
        return null;
    }

    public long getSession_CreatedAt(String sessionid) {
        UserSession us = getSession(sessionid);
        if (us == null) {
            return UserSession.SESSIONLOST;
        }
        return us.getCreatedAt();
    }

    public long getSession_LastTouch(String sessionid) {
        UserSession us = getSession(sessionid);
        if (us == null) {
            return UserSession.SESSIONLOST;
        }
        return us.getLastTouch();
    }

    public boolean hasSessionExpired(String sessionid) {
        UserSession us = getSession(sessionid);
        if (us == null) {
            return true;
        }
        return us.hasExpired();
    }

    public void notifyClient(ServerRequestObject sReqObj) {
        if (sReqObj == null) {
            return;
        }
        for (RoleImpl ug : userGroups) {
            if (ug.hasUsers()) {
                for (User u : ug.getUsers()) {
                    if (u.hasSessions()) {
                        for (UserSession us : u.getSessions()) {
                            if (us != null && !us.getSessionid().equals(sReqObj.getSessionId())) {
                                us.notifyClient(sReqObj);
                            }
                        }
                    }
                }
            }
        }
    }

    public void notifyClients(ServerRequestObject sReqObj) {
        if (sReqObj == null) {
            return;
        }
        for (RoleImpl ug : userGroups) {
            if (ug.hasUsers()) {
                for (User u : ug.getUsers()) {
                    if (u.hasSessions()) {
                        for (UserSession aus : u.getSessions()) {
                            if (aus instanceof UserSession) {
                                UserSession us = (UserSession) aus;
                                if (us != null) {
                                    us.notifyClient(sReqObj);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void increaseWrongPasswordCounter(int userid) {
        for (User u : users) {
            if (u.getUserid() == userid) {
                u.increaseWrongPasswordCounter();
                sQLUser.setActWrongLogins(userid, u.getActWrongLogins());
            }
        }
    }

    public void clearWrongPasswordCounter(int userid) {
        for (User u : users) {
            if (u.getUserid() == userid) {
                u.setActWrongLogins(0);
                sQLUser.setActWrongLogins(userid, 0);
            }
        }
    }

    public void setState(int userid, User.Userstate newState) {
        for (User u : users) {
            if (u.getUserid() == userid) {
                u.setState(newState);
                sQLUser.setState(userid, newState);
            }
        }
    }
}
