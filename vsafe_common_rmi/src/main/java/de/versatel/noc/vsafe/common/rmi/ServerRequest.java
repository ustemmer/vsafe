package de.versatel.noc.vsafe.common.rmi;

import de.versatel.noc.vsafe.common.services.ServiceRequest;

/**
 *
 * @author ulrich.stemmer
 */
public class ServerRequest {

    private final String sessionId;
    private String originUserName;
    private String originIP;
    private String originHost;
    private final ServiceRequest serviceRequest;

    public ServerRequest(String sessionId, ServiceRequest serviceRequest) {
        this.sessionId = sessionId;
        this.serviceRequest = serviceRequest;
    }

    public String getOriginHost() {
        return originHost;
    }

    public String getOriginIP() {
        return originIP;
    }

    public String getOriginUserName() {
        return originUserName;
    }

    public ServiceRequest getServiceRequestObject() {
        return serviceRequest;
    }

    public String getSessionId() {
        return sessionId;
    }
    public void setOriginIP(String originIP) {
        this.originIP = originIP;
    }

    public void setOriginHost(String originHost) {
        this.originHost = originHost;
    }

    public void setOriginUserName(String originUserName) {
        this.originUserName = originUserName;
    }
}
