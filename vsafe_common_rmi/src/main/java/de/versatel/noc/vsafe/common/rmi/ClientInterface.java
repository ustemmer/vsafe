package de.versatel.noc.vsafe.common.rmi;

/**
 *
 * @author ulrich.stemmer
 */
public interface ClientInterface{
    public ClientResult doClientMethod(ClientRequest cr);
    public String getLocalHostName();
    public String getSessionId();
    public void setSessionId(String sessionId);
    public String getIPV4();
    public void notify(ClientNotification cn);

}
