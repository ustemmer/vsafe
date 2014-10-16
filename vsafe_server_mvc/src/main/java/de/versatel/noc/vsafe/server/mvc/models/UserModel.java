package de.versatel.noc.vsafe.server.mvc.models;

import de.versatel.noc.vSafe.services.userService.AbstractUser;
import de.versatel.noc.vSafe.services.userService.AbstractUserGroup;
import de.versatel.noc.vSafe.services.userService.AbstractUserSession;
import de.versatel.noc.vSafe.services.userService.UserInterface;
import de.versatel.noc.vSafe.system.ExceptionInfos;
import de.versatel.noc.vSafe_Server.database.MySQL_PoolHandling;
import de.versatel.noc.vSafe_Server.database.sql.SQLUser;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ulrich.stemmer
 */
public class UserModel extends AbstractUser implements UserInterface {

    private final MySQL_PoolHandling mySQL_ConnectionPool;
    public static final String USERID = "u_userid";
    public static final String USERNAME = "u_username";
    public static final String NAME = "u_name";
    public static final String FIRSTNAME = "u_firstname";
    public static final String PASSWORD = "u_password";
    public static final String USERGROUPID = "u_userGroupId";
    public static final String STATE = "u_state";
    public static final String ACTWRONGLOGINS = "u_actWrongLogins";
    public static final String MAXWRONGLOGINS = "u_maxWrongLogins";
    public static final String MAXSESSIONS = "u_maxSessions";
    public static final String SESSIONTIMEOUT = "u_sessionTimeout";

    public UserModel(int index) {
        super(index);
    }

    public UserModel(MySQL_PoolHandling mySQL_ConnectionPool, AbstractUserGroup userGroup) {
        super(userGroup);
        this.mySQL_ConnectionPool = mySQL_ConnectionPool;
        this.sessions = new ArrayList<AbstractUserSession>();
        this.userGroup = userGroup;

        String empty = "";
        this.userid = 0;
        this.username = empty;
        this.name = empty;
        this.firstname = empty;
        this.password = empty;
        this.state = State.NEWUSER;
        this.actWrongLogins = 0;
        this.maxWrongLogins = 0;
        this.maxSessions = 0;
        this.sessionLifeTime = 0L;
        this.sessionInactivityTo = 0L;
    }

    public String addSession(AbstractUserSession us) {
        //AbstractUserSession us = new AbstractUserSession(this);
        sessions.add(us);
        return us.getSessionid();
    }

    public AbstractUserSession getSession(String sessionId) {
        for (AbstractUserSession us : sessions) {
            if (us.getSessionid().equals(sessionId)) {
                return us;
            }
        }
        return null;
    }

    public List<AbstractUserSession> getSessions() {
        return sessions;
    }

    /**
     * @return the expired
     */
    public boolean hasSessionExpired(String sessionId) {
        AbstractUserSession us = getSession(sessionId);
        if (us != null) {
            return us.hasExpired();
        }
        return true;
    }

    public boolean hasSessions() {
        if (sessions.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean isWrongLoginLimitReached() {
        if (this.actWrongLogins >= this.maxWrongLogins) {
            return true;
        } else {
            return false;
        }
    }

    public void setActWrongLogins(int actWrongLogins) {
        this.actWrongLogins = actWrongLogins;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void removeSession(AbstractUserSession us) {
        if (us != null && sessions != null && sessions.size() > 0) {
            for (AbstractUserSession uss : sessions) {
                if (uss.getSessionid().equals(us.getSessionid())) {
                    sessions.remove(us);
                    if (state == AbstractUser.State.SESSIONLIMIT) {
                        state = AbstractUser.State.WAITING;
                    }

                    if (this.sessions.isEmpty()) {
                        state = AbstractUser.State.LOGGEDOUT;
                    }
                }
            }
        }
    }

    public void setMaxSessions(int maxSessions) {
        this.maxSessions = maxSessions;
    }

    public void setMaxWrongLogins(int maxWrongLogins) {
        this.maxWrongLogins = maxWrongLogins;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSessionInactivityTo(long sessionInactivityTo) {
        this.sessionInactivityTo = sessionInactivityTo;
    }

    public void setSessionLifeTime(long sessionLifeTime) {
        this.sessionLifeTime = sessionLifeTime;
    }

    public void setState(AbstractUser.State state) {
        this.state = state;
    }

    public void setUserGroup(AbstractUserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return username;
    }

    public String generateUserPassword(int length) {
        char[] pwChar = new char[length];
        Random rand = new Random();
        String letters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!-_?#*";
        for (int i = 0; i
                < length;
                ++i) {
            pwChar[i] = letters.toCharArray()[rand.nextInt(letters.length() - 1)];
            if (pwChar[i] == 0) {
                i--;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(pwChar);
        String pwString = sb.toString();
        return pwString;
    }

    public String cryptUserPassword(String toCrypt) {
        StringBuffer sb;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            byte[] digest = md.digest(toCrypt.getBytes());
            sb = new StringBuffer();
            for (byte d : digest) {
                sb.append(Integer.toHexString(d & 0xFF));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            javax.swing.JFrame fframe1 = new javax.swing.JFrame();
            javax.swing.JOptionPane.showMessageDialog(fframe1, e, "Passwort konnte nicht verschluesselt werden.",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    /**
     *
     * @param user
     * @param tempUser
     * @return
     */
    public int changeUser(UserModel tempUser) {
        // UserId und Username können nicht geändert werden.
        // boolean-Array wird erzeugt, um festzulegen, welcher Wert geändert wurde.
        boolean[] hasChanged = new boolean[10];
        for (boolean b : hasChanged) {
            b = false;
        }
        List<String> attributes = new ArrayList<String>();
        List<String> oldValues = new ArrayList<String>();
        List<String> newValues = new ArrayList<String>();
        int counter = 0;
        if (actWrongLogins != tempUser.actWrongLogins) {
            hasChanged[0] = true;
            counter += 1;
        }
        if (!firstname.equals(tempUser.firstname)) {
            hasChanged[1] = true;
            counter += 1;
        }
        if (maxSessions != tempUser.maxSessions) {
            hasChanged[2] = true;
            counter += 1;
        }
        if (maxWrongLogins != tempUser.maxWrongLogins) {
            hasChanged[3] = true;
            counter += 1;
        }
        if (!name.equals(tempUser.name)) {
            hasChanged[4] = true;
            counter += 1;
        }
        if (!password.equals(tempUser.password)) {
            hasChanged[5] = true;
            counter += 1;
        }
        if (sessionLifeTime != tempUser.sessionLifeTime) {
            hasChanged[6] = true;
            counter += 1;
        }
        if (sessionInactivityTo != tempUser.sessionInactivityTo) {
            hasChanged[7] = true;
            counter += 1;
        }
        if (state != tempUser.state) {
            hasChanged[8] = true;
            counter += 1;
        }
        if (userGroup.getUserGroupId() != tempUser.getUserGroup().getUserGroupId()) {
            hasChanged[8] = true;
            counter += 1;
        }
        if (counter == 0) {
            return ExceptionInfos.GENERAL_NOTHING_TO_CHANGE;
        }
        String sql = SQLUser.changeUser(this, tempUser, hasChanged);
        if (sql != null && sql.length() > 0 && mySQL_ConnectionPool.executeUpdate(sql)) {
            for (int n = 0; n
                    < 9; n++) {
                switch (n) {
                    case 0:
                        if (hasChanged[n]) {
                            actWrongLogins = tempUser.getActWrongLogins();
                            attributes.add("ActWrongLogins");
                            oldValues.add(String.valueOf(actWrongLogins));
                            newValues.add(String.valueOf(tempUser.actWrongLogins));
                        }
                        break;

                    case 1:
                        if (hasChanged[n]) {
                            firstname = tempUser.getFirstname();
                            attributes.add("Firstname");
                            oldValues.add(firstname);
                            newValues.add(tempUser.firstname);
                        }
                        break;

                    case 2:
                        if (hasChanged[n]) {
                            maxSessions = tempUser.getMaxSessions();
                            attributes.add("MaxSessions");
                            oldValues.add(String.valueOf(maxSessions));
                            newValues.add(String.valueOf(tempUser.maxSessions));
                        }
                        break;

                    case 3:
                        if (hasChanged[n]) {
                            maxWrongLogins = tempUser.getMaxWrongLogins();
                            attributes.add("MaxWrongLogins");
                            oldValues.add(String.valueOf(maxWrongLogins));
                            newValues.add(String.valueOf(tempUser.maxWrongLogins));
                        }
                        break;

                    case 4:
                        if (hasChanged[n]) {
                            name = tempUser.getName();
                            attributes.add("Name");
                            oldValues.add(name);
                            newValues.add(tempUser.name);
                        }
                        break;

                    case 5:
                        if (hasChanged[n]) {
                            password = tempUser.getPassword();
                            attributes.add("Password");
                            oldValues.add(password);
                            newValues.add(tempUser.password);
                        }
                        break;

                    case 6:
                        if (hasChanged[n]) {
                            sessionLifeTime = tempUser.getSessionLifeTime();
                            attributes.add("SessionLifeTime");
                            oldValues.add(String.valueOf(sessionLifeTime));
                            newValues.add(String.valueOf(tempUser.sessionLifeTime));
                        }
                        break;
                    case 7:
                        if (hasChanged[n]) {
                            sessionInactivityTo = tempUser.getSessionInactivityTo();
                            attributes.add("SessionInactivityTimeout");
                            oldValues.add(String.valueOf(sessionInactivityTo));
                            newValues.add(String.valueOf(tempUser.sessionInactivityTo));
                        }
                        break;

                    case 8:
                        if (hasChanged[n]) {
                            setState(tempUser.getState());
                            attributes.add("State");
                            oldValues.add(String.valueOf(state));
                            newValues.add(String.valueOf(tempUser.state));
                        }
                        break;
                    /*case 8:
                    if (hasChanged[n]) {
                    user.setUserGroupId(tempUser.getUserGroupId());
                    attributes.add("UserGroupId");
                    oldValues.add(String.valueOf(user.getUserGroupId()));
                    newValues.add(String.valueOf(tempUser.getUserGroupId()));
                    }
                    break;*/
                }
            }
            //if (actionLogActive) {         }

            return ExceptionInfos.GENERAL_OK;
        } else {
            return ExceptionInfos.DB_DBMISMATCH;
        }
    }

    public void wrongPassword() {
        String sql = SQLUser.changeActWrongLogins(userid, actWrongLogins + 1);
        if (sql != null && sql.length() > 0 && mySQL_ConnectionPool.executeUpdate(sql)) {
            actWrongLogins = actWrongLogins + 1;
        }
    }

    public void passwordOk() {
        String sql = SQLUser.changeActWrongLogins(userid, 0);
        if (sql != null && sql.length() > 0 && mySQL_ConnectionPool.executeUpdate(sql)) {
            actWrongLogins = 0;
        }



    }

    public boolean isPasswordValid(String input) {
        return password.equals(input);
    }

    /**
     *
     * @param user
     * @param state
     * @return
     */
    public boolean changeState(State newState) {
        String sql = SQLUser.changeState(userid, newState.toString());
        if (sql != null && sql.length() > 0 && mySQL_ConnectionPool.executeUpdate(sql)) {
            state = newState;
            return true;
        }
        return false;
    }

    public String addSession(ClientInterface rsi) {
        UserSession us = new UserSession(this, rsi);
        sessions.add(us);
        return us.getSessionid();
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }
}
