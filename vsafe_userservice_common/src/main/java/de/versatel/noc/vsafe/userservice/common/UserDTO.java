package de.versatel.noc.vsafe.userservice.common;

/**
 *
 * @author ulrich.stemmer
 */
public class UserDTO implements UserInterface{

    protected AbstractUserGroup aug;
    
    protected int actWrongLogins;
    protected String firstname;
    protected int maxSessions;
    protected int maxWrongLogins;
    protected String name;
    protected String password;
    protected long sessionInactivityTo;
    protected long sessionLifeTime;
    protected String state;
    protected int userGroupId ;
    protected int userid;
    protected String username;

    public UserDTO() {
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

    public long getSessionInactivityTo() {
        return sessionInactivityTo;
    }

    public long getSessionLifeTime() {
        return sessionLifeTime;
    }

    public String getState() {
        return state;
    }

    public int getUserid() {
        return userid;
    }

    public int getUserGroupId() {
        return userGroupId;
    }

    public String getUsername() {
        return username;
    }

    public void setActWrongLogins(int actWrongLogins) {
        this.actWrongLogins = actWrongLogins;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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

    public void setState(String state){
        this.state = state;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setUserGroup(AbstractUserGroup userGroup) {
        this.aug = userGroup;
    }
    
    public void setUserGroupId(int userGroupId) {
        this.userGroupId = userGroupId;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
