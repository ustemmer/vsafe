package de.versatel.noc.vsafe.server.core.security.users;

import de.versatel.noc.vSafe_Server.util.SessionIdGenerator;

import de.versatel.noc.vSafe.rmi.ClientInterface;
import de.versatel.noc.vSafe.rmi.NotificationObject;
import de.versatel.noc.vSafe.rmi.ServerRequestObject;

import java.rmi.RemoteException;

/**
 *
 * @author ulrich.stemmer
 *
 * Die SessionId wird bereits im Konstruktor erzeugt.
 */
public class UserSession implements java.io.Serializable, Comparable {

    private final int sessionIdLen = 16;
    private ClientInterface rmiClient;

    public static final int SESSIONLOST = -1;
    protected User user;
    protected String sessionid;
    protected String localHostName;
    protected long sessionInactivityTo;
    protected long sessionLifeTime;
    protected long createdAt;
    protected long lastTouch;


    /**
     *
     */
    public UserSession(User user, ClientInterface rmiClient) {
        String empty = "";
        this.user = user;
        this.rmiClient = rmiClient;
        this.sessionid = new SessionIdGenerator().generateId(sessionIdLen);
        try {
            this.rmiClient.setSessionId(sessionid);
        } catch (RemoteException e) {
        }
        this.sessionInactivityTo = user.getSessionInactivityTo();
        this.sessionLifeTime = user.getSessionLifeTime();
        this.createdAt = System.currentTimeMillis();
        this.lastTouch = 0L;
    }

    public ClientInterface getRmiClient() {
        return rmiClient;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof UserSession) {
            return ((UserSession) o).sessionid.compareToIgnoreCase(this.sessionid);
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

    // TODO: nur für Client
    /*public String getLocalHostName() {
        try {
            java.net.InetAddress address = java.net.InetAddress.getLocalHost();
            return (address.getHostName());
        } catch (java.net.UnknownHostException e) {
            return null;
        }
    }*/

    public String getLocalHostName() {
        return this.localHostName;
    }

    public long getSessionInactivityTo() {
        return sessionInactivityTo;
    }

    public long getSessionLifeTime() {
        return sessionLifeTime;
    }

    public User getUser() {
        return user;
    }

    public String getSessionid() {
        return sessionid;
    }

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

    public void notifyClient(ServerRequestObject sReqObj) {
        if (sReqObj != null) {
            NotificationObject no = new NotificationObject(
                    sReqObj.getServiceRequestObject().getServiceId(),
                    sReqObj.getServiceRequestObject().getMethodID(),
                    //TODO Ursprung-SessionID muß durch UserName ersetzt werden
                    sReqObj.getOriginUserName(),
                    sReqObj.getOriginIP(),
                    sReqObj.getOriginHost());
            this.rmiClient.notify(no);
        }
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

    public void setRmiClient(ClientInterface rmiClient) {
        this.rmiClient = rmiClient;
    }

    public void setSessionInactivityTo(long sessionInactivityTo) {
        this.sessionInactivityTo = sessionInactivityTo;
    }

    public void setSessionLifeTime(long sessionLifeTime) {
        this.sessionLifeTime = sessionLifeTime;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return sessionid;
    }
}
