package de.versatel.noc.vsafe.server.rmi.services.usermanagement;

import de.versatel.noc.vSafe.rmi.ClientInterface;
import de.versatel.noc.vSafe.services.ServiceExecutor;
import de.versatel.noc.vSafe.services.userService.UserDTO;
import de.versatel.noc.vSafe_Server.users.User;
import de.versatel.noc.vSafe_Server.users.UserGroup;
import de.versatel.noc.vSafe_Server.users.UserHandling;
import java.util.List;

/**
 *
 * @author ulrich.stemmer
 */
public class UserServiceExecutor extends ServiceExecutor {

    private UserHandling userHandling;
    // Methoden
    public static final int login = 1;
    public static final int logout = 2;
    //public static final int getService = 3;
    public static final int addUserGroup = 4;
    public static final int removeUserGroup = 5;
    public static final int hasUserGroups = 6;
    public static final int getUsers = 7;
    public static final int getUserGroup = 8;
    public static final int getUserGroup_UserGroupId = 9;
    public static final int getUserGroup_Name = 10;
    public static final int getUserGroup_PermissionBits = 11;
    public static final int getUserGroup_Remark = 12;
    public static final int setUserGroup_Name = 13;
    public static final int setUserGroup_PermissionBit = 14;
    public static final int setUserGroup_Remark = 15;
    public static final int getUserGroup_Users = 16;
    public static final int hasUserGroup_Users = 17;
    public static final int addUser = 18;
    public static final int removeUser = 19;
    public static final int getUserViaId = 20;
    public static final int getUserViaName = 21;
    public static final int getUser_UserGroup = 22;
    public static final int getUser_UserId = 23;
    public static final int getUser_ActWrongLogins = 24;
    public static final int getUser_Firstname = 25;
    public static final int getUser_MaxSessions = 26;
    public static final int getUser_MaxWrongLogins = 27;
    public static final int getUser_Name = 28;
    public static final int getUser_Password = 29;
    public static final int getUser_SessionInactivityTimeout = 30;
    public static final int getUser_SessionLifetime = 31;
    public static final int getUser_State = 32;
    public static final int getUser_Username = 33;
    public static final int isUserBarred = 34;
    public static final int isUserPasswordValid = 35;
    public static final int setUser_Values = 36;
    public static final int setUser_ActWrongLogins = 37;
    public static final int setUser_Firstname = 38;
    public static final int setUser_MaxSessions = 39;
    public static final int setUser_MaxWrongLogins = 40;
    public static final int setUser_Name = 41;
    public static final int setUser_Password = 42;
    public static final int setUser_SessionInactivityTimeout = 43;
    public static final int setUser_SessionLifetime = 44;
    public static final int setUser_State = 45;
    //public static final int changeUserState = 46;
    public static final int setUser_UserGroup = 47;
    public static final int setUser_Username = 48;
    public static final int hasUser_Sessions = 49;
    public static final int getUser_SessionCount = 50;
    public static final int getUser_Sessions = 51;
    public static final int addSession = 52;
    public static final int getSession = 53;
    public static final int getSession_CreatedAt = 54;
    public static final int getSession_Ipv4 = 55;
    public static final int getSession_LastTouch = 56;
    public static final int getSession_LocalHostName = 57;
    public static final int getSession_SessionId = 58;
    public static final int hasSessionExpired = 59;
    public static final int setSession_CreatedAt = 60;
    public static final int setSession_LastTouch = 61;
    public static final int setSession_SessionId = 62;

    public UserServiceExecutor(UserService service, UserHandling userHandling) {
        super(service);
        this.userHandling = userHandling;
    }

    public Object set(int methodId, List<Object> arguments) {
        Object o;
        switch (methodId) {
            case login:
                if (arguments.size() == 3) {
                    return userHandling.login(
                            (String) arguments.get(0),
                            (String) arguments.get(1),
                            (ClientInterface) arguments.get(2));
                }
                return null;
            case logout:
                if (arguments.size() == 1) {
                    return userHandling.logout(
                            (String) arguments.get(0));
                }
                return null;
            // case getService:
            // o = userHandling.getS;
            // break;
            case addUserGroup:
                if (arguments.size() == 2) {
                    userHandling.addUserGroup(
                            (String) arguments.get(0),
                            (String) arguments.get(1));
                }
                return null;
            case removeUserGroup:
                if (arguments.size() == 1) {
                    userHandling.removeUserGroup(
                            (UserGroup) arguments.get(0));
                }
                return null;
            case setUserGroup_Name:
                if (arguments.size() == 2) {
                    userHandling.setUserGroup_Name(
                            (Integer) arguments.get(0),
                            (String) arguments.get(1));
                }
                return null;
            case setUserGroup_PermissionBit:
                if (arguments.size() == 4) {
                    userHandling.setUserGroup_PermissionBit(
                            (Integer) arguments.get(0),
                            (Integer) arguments.get(1),
                            (Integer) arguments.get(2),
                            (Boolean) arguments.get(3));
                }
                return null;
            case setUserGroup_Remark:
                if (arguments.size() == 2) {
                    userHandling.setUserGroup_Remark(
                            (Integer) arguments.get(0),
                            (String) arguments.get(1));
                }
                return null;
            case addUser:
                if (arguments.size() == 2) {
                    return userHandling.addUser(
                            (Integer) arguments.get(0),
                            (User) arguments.get(0));
                }
                return null;
            case removeUser:
                if (arguments.size() == 2) {
                    return userHandling.removeUser(
                            (Integer) arguments.get(0),
                            (User) arguments.get(0));
                }
                return null;
            case setUser_Values:
                if (arguments.size() == 2) {
                    userHandling.setUser_Values(
                            (Integer) arguments.get(0),
                            (UserDTO) arguments.get(1));
                }
                return null;
            case setUser_ActWrongLogins:
                if (arguments.size() == 2) {
                    userHandling.setUser_ActWrongLogins((Integer) arguments.get(0), (Integer) arguments.get(1));
                }
                return null;
            case setUser_Firstname:
                if (arguments.size() == 2) {
                    userHandling.setUser_Firstname((Integer) arguments.get(0), (String) arguments.get(1));
                }
                return null;
            case setUser_MaxSessions:
                if (arguments.size() == 2) {
                    userHandling.setUser_MaxSessions((Integer) arguments.get(0), (Integer) arguments.get(1));
                }
                return null;
            case setUser_MaxWrongLogins:
                if (arguments.size() == 2) {
                    userHandling.setUser_MaxWrongLogins((Integer) arguments.get(0), (Integer) arguments.get(1));
                }
                return null;
            case setUser_Name:
                if (arguments.size() == 2) {
                    userHandling.setUser_Name((Integer) arguments.get(0), (String) arguments.get(1));
                }
                return null;
            case setUser_Password:
                if (arguments.size() == 2) {
                    userHandling.setUser_Password((Integer) arguments.get(0), (String) arguments.get(1));
                }
                return null;
            case setUser_SessionInactivityTimeout:
                if (arguments.size() == 2) {
                    userHandling.setUser_SessionInactivityTimeout((Integer) arguments.get(0), (Long) arguments.get(1));
                }
                return null;
            case setUser_SessionLifetime:
                if (arguments.size() == 2) {
                    userHandling.setUser_SessionLifetime((Integer) arguments.get(0), (Long) arguments.get(1));
                }
                return null;
            case setUser_State:
                if (arguments.size() == 2) {
                    userHandling.setUser_State((Integer) arguments.get(0), (User.Userstate) arguments.get(1));
                }
                return null;
            case setUser_UserGroup:
                if (arguments.size() == 2) {
                    userHandling.setUser_UserGroup((Integer) arguments.get(0), (UserGroup) arguments.get(1));
                }
                return null;
            case setUser_Username:
                if (arguments.size() == 2) {
                    userHandling.setUser_Username((Integer) arguments.get(0), (String) arguments.get(1));
                }
                return null;
            case setSession_CreatedAt:
                if (arguments.size() == 2) {
                    userHandling.setSession_CreatedAt((String) arguments.get(0), (Long) arguments.get(1));
                }
                return null;
            case setSession_LastTouch:
                if (arguments.size() == 2) {
                    userHandling.setSession_LastTouch((String) arguments.get(0), (Long) arguments.get(1));
                }
                return null;
            default:
                o = null;
        }
        return o;
    }

    public Object get(int methodId, List<Object> arguments) {
        Object o;
        switch (methodId) {
            case login:
                if (arguments.size() == 3) {
                    return userHandling.login(
                            (String) arguments.get(0),
                            (String) arguments.get(1),
                            (ClientInterface) arguments.get(2));
                }
                return null;
            case logout:
                if (arguments.size() == 1) {
                    return userHandling.logout(
                            (String) arguments.get(0));
                }
                return null;
            case hasUserGroups:
                if (arguments.isEmpty()) {
                    return userHandling.hasUserGroups();
                }
                return null;
            case getUsers:
                if (arguments.isEmpty()) {
                    return userHandling.getUsers();
                }
                return null;
            case getUserGroup:
                if (arguments.size() == 1) {
                    return userHandling.getUserGroup(
                            (Integer) arguments.get(0));
                }
                return null;
            /*case getUserGroup_UserGroupId:
            if (arguments.size() == 1) {
            return userHandling.getDBUserGroupId(
            (String) arguments.get(1));
            }
            return null;*/
            case getUserGroup_Name:
                if (arguments.size() == 1) {
                    return userHandling.getUserGroup_Name(
                            (Integer) arguments.get(0));
                }
                return null;
            case getUserGroup_PermissionBits:
                if (arguments.size() == 2) {
                    return userHandling.getUserGroup_PermissionBits(
                            (Integer) arguments.get(0),
                            (Integer) arguments.get(0));
                }
                return null;
            case getUserGroup_Remark:
                if (arguments.size() == 1) {
                    return userHandling.getUserGroup_Remark(
                            (Integer) arguments.get(0));
                }
                return null;
            case getUserGroup_Users:
                if (arguments.size() == 1) {
                    return userHandling.getUserGroup_Users(
                            (Integer) arguments.get(0));
                }
                return null;
            case hasUserGroup_Users:
                if (arguments.size() == 1) {
                    return userHandling.hasUserGroup_Users(
                            (Integer) arguments.get(0));
                }
                return null;
            case getUserViaId:
                if (arguments.size() == 1) {
                    return userHandling.getUser((Integer) arguments.get(0));
                }
                return null;
            case getUserViaName:
                if (arguments.size() == 1) {
                    return userHandling.getUser((String) arguments.get(0));
                }
                return null;
            case getUser_UserGroup:
                if (arguments.size() == 1) {
                    return userHandling.getUser_UserGroup((Integer) arguments.get(0));
                }
                return null;
            case getUser_UserId:
                if (arguments.size() == 1) {
                    return userHandling.getUser_UserId((Integer) arguments.get(0));
                }
                return null;
            case getUser_ActWrongLogins:
                if (arguments.size() == 1) {
                    return userHandling.getUser_ActWrongLogins((Integer) arguments.get(0));
                }
                return null;
            case getUser_Firstname:
                if (arguments.size() == 1) {
                    return userHandling.getUser_Firstname((Integer) arguments.get(0));
                }
                return null;
            case getUser_MaxSessions:
                if (arguments.size() == 1) {
                    return userHandling.getUser_MaxSessions((Integer) arguments.get(0));
                }
                return null;
            case getUser_MaxWrongLogins:
                if (arguments.size() == 1) {
                    return userHandling.getUser_MaxWrongLogins((Integer) arguments.get(0));
                }
                return null;
            case getUser_Name:
                if (arguments.size() == 1) {
                    return userHandling.getUser_Name((Integer) arguments.get(0));
                }
                return null;
            case getUser_Password:
                if (arguments.size() == 1) {
                    return userHandling.getUser_Password((Integer) arguments.get(0));
                }
                return null;
            case getUser_SessionInactivityTimeout:
                if (arguments.size() == 1) {
                    return userHandling.getUser_SessionInactivityTimeout((Integer) arguments.get(0));
                }
                return null;
            case getUser_SessionLifetime:
                if (arguments.size() == 1) {
                    return userHandling.getUser_SessionLifetime((Integer) arguments.get(0));
                }
                return null;
            case getUser_State:
                if (arguments.size() == 1) {
                    return userHandling.getUser_State((Integer) arguments.get(0));
                }
                return null;
            case getUser_Username:
                if (arguments.size() == 1) {
                    return userHandling.getUser_Username((Integer) arguments.get(0));
                }
                return null;
            case isUserBarred:
                if (arguments.size() == 1) {
                    return userHandling.isUserBarred((Integer) arguments.get(0));
                }
                return null;
            case isUserPasswordValid:
                if (arguments.size() == 2) {
                    return userHandling.isUserPasswordValid((Integer) arguments.get(0), (String) arguments.get(1));
                }
                return null;
            case hasUser_Sessions:
                if (arguments.size() == 1) {
                    return userHandling.hasUser_Sessions((Integer) arguments.get(0));
                }
                return null;
            case getUser_SessionCount:
                if (arguments.size() == 1) {
                    return userHandling.getUser_SessionCount((Integer) arguments.get(0));
                }
                return null;
            case getUser_Sessions:
                if (arguments.size() == 1) {
                    return userHandling.getUser_Sessions((Integer) arguments.get(0));
                }
                return null;
            case getSession:
                if (arguments.size() == 1) {
                    return userHandling.getSession((String) arguments.get(0));
                }
                return null;
            case getSession_CreatedAt:
                if (arguments.size() == 1) {
                    return userHandling.getSession_CreatedAt((String) arguments.get(0));
                }
                return null;
            case getSession_Ipv4:
                if (arguments.size() == 1) {
                    return userHandling.getSession_Ipv4((String) arguments.get(0));
                }
                return null;
            case getSession_LastTouch:
                if (arguments.size() == 1) {
                    return userHandling.getSession_LastTouch((String) arguments.get(0));
                }
                return null;
            case getSession_LocalHostName:
                if (arguments.size() == 1) {
                    return userHandling.getSession_LocalHostName((String) arguments.get(0));
                }
                return null;
            case getSession_SessionId:
                if (arguments.size() == 1) {
                    return userHandling.getSession_SessionId((String) arguments.get(0));
                }
                return null;
            case hasSessionExpired:
                if (arguments.size() == 1) {
                    return userHandling.hasSessionExpired((String) arguments.get(0));
                }
                return null;
            default:
                o = null;
        }
        return o;
    }
}
