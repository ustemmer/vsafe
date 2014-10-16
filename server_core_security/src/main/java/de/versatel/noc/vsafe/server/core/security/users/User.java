package de.versatel.noc.vsafe.server.core.security.users;

import de.versatel.noc.vSafe.rmi.ClientInterface;
import de.versatel.noc.vSafe.services.userService.UserInterface;
import de.versatel.noc.vSafe.services.userService.UserDTO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ulrich.stemmer
 *
 */
public class User implements UserInterface, Comparable {

    public enum Userstate {

        WAITING, NEWUSER, LOGGEDIN, LOGGEDOUT, WRONGPASSWORD, MAXWRONGPASSWORDS, USERADMINBARRED, USERSYSTEMBARRED, SESSIONLIMIT
    }
    protected List<UserSession> sessions;
    protected RoleImpl userGroup;
    protected int userid;
    protected String username;
    protected String name;
    protected String firstname;
    protected String password;
    protected Userstate state;
    protected int actWrongLogins;
    protected int maxWrongLogins;
    protected int maxSessions;
    protected long sessionLifeTime;
    protected long sessionInactivityTo;

    public User() {
        this.sessions = new ArrayList<UserSession>();
        String empty = "";
        this.userid = 0;
        this.username = empty;
        this.name = empty;
        this.firstname = empty;
        this.password = empty;
        this.state = Userstate.NEWUSER;
        this.actWrongLogins = 0;
        this.maxWrongLogins = 0;
        this.maxSessions = 0;
        this.sessionLifeTime = 0L;
        this.sessionInactivityTo = 0L;
    }

    /**
     *
     */
    public User(RoleImpl userGroup) {

        this.userGroup = userGroup;
        String empty = "";
        this.userid = 0;
        this.username = empty;
        this.name = empty;
        this.firstname = empty;
        this.password = empty;
        this.state = Userstate.NEWUSER;
        this.actWrongLogins = 0;
        this.maxWrongLogins = 0;
        this.maxSessions = 0;
        this.sessionLifeTime = 0L;
        this.sessionInactivityTo = 0L;
    }

    public String addSession(ClientInterface rsi) {
        UserSession us = new UserSession(this, rsi);
        sessions.add(us);
        return us.getSessionid();
    }

    public String addSession(UserSession us) {
        sessions.add(us);
        return us.getSessionid();
    }

    public void setUser(UserDTO dto) throws UserException{
        if (actWrongLogins != dto.getActWrongLogins()) {
            actWrongLogins = dto.getActWrongLogins();
        }
        if (!firstname.equals(dto.getFirstname())) {
            firstname = dto.getFirstname();
        }
        if (maxSessions != dto.getMaxSessions()) {
            maxSessions = dto.getMaxSessions();
        }
        if (maxWrongLogins != dto.getMaxWrongLogins()) {
            maxWrongLogins = dto.getMaxWrongLogins();
        }
        if (!name.equals(dto.getName())) {
            name = dto.getName();
        }
        if (!password.equals(dto.getPassword())) {
            password = dto.getPassword();
        }
        if (sessionLifeTime != dto.getSessionLifeTime()) {
            sessionLifeTime = dto.getSessionLifeTime();
        }
        if (sessionInactivityTo != dto.getSessionLifeTime()) {
            sessionInactivityTo = dto.getSessionLifeTime();
        }
        if (!state.toString().equals(dto.getState())) {
            try{
                state = state.valueOf(dto.getState());
            }catch(IllegalArgumentException e){
                throw new UserException(e);
            }
        }
    }

    public void clearWrongPasswordCounter() {
        actWrongLogins = 0;
    }

    public int compareTo(Object o) {
        if (o instanceof UserInterface) {
            return ((UserInterface) o).getUsername().compareToIgnoreCase(username);
        } else {
            throw new UnsupportedOperationException("Object not a User.");
        }
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

    public int getActWrongLogins() {
        return actWrongLogins;
    }

    public String getFirstname() {
        return firstname;
    }

    public int getMaxSessions() {
        return maxSessions;
    }

    public int getMaxWrongLogins() {
        return maxWrongLogins;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public UserSession getSession(String sessionId) {
        for (UserSession us : sessions) {
            if (us.getSessionid().equals(sessionId)) {
                return us;
            }
        }
        return null;
    }

    public long getSessionInactivityTo() {
        return sessionInactivityTo;
    }

    public long getSessionLifeTime() {
        return sessionLifeTime;
    }

    public List<UserSession> getSessions() {
        return sessions;
    }

    public Userstate getState() {
        return state;
    }

    public RoleImpl getUserGroup() {
        return userGroup;
    }

    public int getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    /**
     * @return the expired
     */
    public boolean hasSessionExpired(String sessionId) {
        UserSession us = getSession(sessionId);
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

    public void increaseWrongPasswordCounter() {
        actWrongLogins = actWrongLogins + 1;
    }

    public boolean isPasswordValid(String input) {
        return password.equals(input);
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

    public void removeSession(UserSession us) {
        if (us != null && sessions != null && sessions.size() > 0) {
            for (UserSession uss : sessions) {
                if (uss.getSessionid().equals(us.getSessionid())) {
                    sessions.remove(us);
                    if (state == Userstate.SESSIONLIMIT) {
                        state = Userstate.WAITING;
                    }

                    if (this.sessions.isEmpty()) {
                        state = Userstate.LOGGEDOUT;
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

    public void setState(Userstate state) {
        this.state = state;
    }

    public void setUserGroup(RoleImpl userGroup) {
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

}
