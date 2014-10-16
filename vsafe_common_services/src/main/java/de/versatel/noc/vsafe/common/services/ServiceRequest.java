package de.versatel.noc.vsafe.common.services;

import java.util.List;

/**
 *
 * @author ulrich.stemmer
 */
public class ServiceRequest {

    private final long serviceId;
    private final int methodID;
    private final List<Object> arguments;

    public ServiceRequest(long serviceId, int methodID, List<Object> arguments) {
        this.serviceId = serviceId;
        this.methodID = methodID;
        this.arguments = arguments;
    }

    public List<Object> getArguments() {
        return arguments;
    }

    public int getMethodID() {
        return methodID;
    }

    public long getServiceId() {
        return serviceId;
    }

}
