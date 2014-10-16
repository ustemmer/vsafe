package de.versatel.noc.vsafe.userservice.common;

//import de.versatel.noc.vsafe.vsafe_services_shared.Service;

/**
 *
 * @author ulrich.stemmer
 *
 * Die SessionId wird bereits im Konstruktor erzeugt.
 */
//public abstract class AbstractUserSession extends SimpleTreeObject implements ClientInterface, java.io.Serializable, Comparable {
//public class AbstractUserSession implements ClientInterface, java.io.Serializable, Comparable {
public abstract class AbstractUserSession implements java.io.Serializable, Comparable {

    public static final int SESSIONLOST = -1;
    protected AbstractUser user;
    protected String sessionid;
    protected String localHostName;
    protected long sessionInactivityTo;
    protected long sessionLifeTime;
    protected long createdAt;
    protected long lastTouch;
    //private Integer state;

    /**
     *
     */
    public AbstractUserSession(AbstractUser user) {
        this.user = user;
        this.sessionInactivityTo = user.getSessionInactivityTo();
        this.sessionLifeTime = user.getSessionLifeTime();
        this.createdAt = System.currentTimeMillis();
        this.lastTouch = 0L;
    }

    public int compareTo(Object o) {
        if (o instanceof AbstractUserSession) {
            return ((AbstractUserSession) o).sessionid.compareToIgnoreCase(this.sessionid);
        } else {
            throw new UnsupportedOperationException("Object not a UserSession.");
        }
    }

    /**
     *
     * @return
     */
    public long getCreatedAt() {
        return createdAt;
    }

    public long getLastTouch() {
        return lastTouch;
    }

    public String getLocalHostName() {
        return this.localHostName;
    }

    public long getSessionInactivityTo() {
        return sessionInactivityTo;
    }

    public long getSessionLifeTime() {
        return sessionLifeTime;
    }

    public AbstractUser getUser() {
        return user;
    }

    /**
     * @return the expired
     */
    public boolean hasExpired() {
        if (sessionLifeTime > 0L) {
            if (System.currentTimeMillis() - createdAt > sessionLifeTime) {
                return true;
            }
        }
        if (sessionInactivityTo > 0L) {
            if (System.currentTimeMillis() - lastTouch > sessionInactivityTo) {
                return true;
            }
        }
        return false;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastTouch(long lastTouch) {
        this.lastTouch = lastTouch;
    }

    public void setLocalHostName(String localHostName) {
        this.localHostName = localHostName;
    }

    public void setSessionInactivityTo(long sessionInactivityTo) {
        this.sessionInactivityTo = sessionInactivityTo;
    }

    public void setSessionLifeTime(long sessionLifeTime) {
        this.sessionLifeTime = sessionLifeTime;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public void setUser(AbstractUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return sessionid;
    }

    
}
