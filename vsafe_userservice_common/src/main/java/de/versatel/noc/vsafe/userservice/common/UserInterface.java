package de.versatel.noc.vsafe.userservice.common;

/**
 *
 * @author ulrich.stemmer
 */
public interface UserInterface {

    public int getActWrongLogins();
    public String getFirstname();
    public int getMaxSessions();
    public int getMaxWrongLogins();
    public String getName();
    public String getPassword();
    public long getSessionInactivityTo();
    public long getSessionLifeTime();
    public int getUserid();
    public String getUsername();
    public void setActWrongLogins(int actWrongLogins);
    public void setFirstname(String firstname);
    public void setMaxSessions(int maxSessions);
    public void setMaxWrongLogins(int maxWrongLogins);
    public void setName(String name);
    public void setPassword(String password);
    public void setSessionInactivityTo(long sessionInactivityTo);
    public void setSessionLifeTime(long sessionLifeTime);
    public void setUserGroup(AbstractUserGroup userGroup);
    public void setUserid(int userid);
    public void setUsername(String username);
    

}
