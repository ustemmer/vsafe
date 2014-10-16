package de.versatel.noc.vsafe.userservice.common;

import java.util.List;
import java.util.ArrayList;
//import de.versatel.noc.vSafe.services.userService.UserInterface;

/**
 *
 * @author ulrich.stemmer
 *
 */
//public class AbstractUser extends SimpleTreeObject implements java.io.Serializable, Comparable {
public abstract class AbstractUser implements UserInterface, java.io.Serializable, Comparable {

    public enum Userstate {

        WAITING, NEWUSER, LOGGEDIN, LOGGEDOUT, WRONGPASSWORD, MAXWRONGPASSWORDS, USERADMINBARRED, USERSYSTEMBARRED, SESSIONLIMIT
    }
    protected List<AbstractUserSession> sessions;
    protected AbstractUserGroup userGroup;
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

    public AbstractUser() {

        this.sessions = new ArrayList<AbstractUserSession>();

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
    public AbstractUser(AbstractUserGroup userGroup) {

        this.sessions = new ArrayList<AbstractUserSession>();
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

    public String addSession(AbstractUserSession us) {
        //AbstractUserSession us = new AbstractUserSession(this);
        sessions.add(us);
        return us.getSessionid();
    }

    public int compareTo(Object o) {
        if (o instanceof AbstractUser) {
            return ((AbstractUser) o).username.compareToIgnoreCase(username);
        } else {
            throw new UnsupportedOperationException("Object not a User.");
        }
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

    public AbstractUserSession getSession(String sessionId) {
        for (AbstractUserSession us : sessions) {
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

    public List<AbstractUserSession> getSessions() {
        return sessions;
    }

    public Userstate getState() {
        return state;
    }

    public AbstractUserGroup getUserGroup() {
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
}
