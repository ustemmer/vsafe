package de.versatel.noc.vsafe.common.rmi;

/**
 *
 * @author ulrich.stemmer
 */
public class ClientNotification {

    private final long serviceId;
    private final int methodId;
    private final String originUserName;
    private final String originIP;
    private final String originHost;
    private long timestamp;

    public ClientNotification(
            long serviceId,
            int methodId,
            String originUserName,
            String originIP,
            String originHost) {
        this.serviceId = serviceId;
        this.methodId = methodId;
        this.originUserName = originUserName;
        this.originIP = originIP;
        this.originHost = originHost;
    }

    public int getMethodId() {
        return methodId;
    }

    public long getServiceId() {
        return serviceId;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
