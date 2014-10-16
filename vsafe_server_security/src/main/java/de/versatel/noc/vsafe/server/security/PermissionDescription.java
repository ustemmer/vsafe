package de.versatel.noc.vsafe.server.security;

/**
 *
 * @author ulrich.stemmer
 */
public class PermissionDescription {

    private final long serviceId;
    private final int permissionId;
    private final String name;
    private final String description;
    //private final Service.Type type;

    public PermissionDescription(long serviceId, int permissionId, String name, String description) {
        this.serviceId = serviceId;
        this.permissionId = permissionId;
        this.name = name;
        this.description = description;
    }

    public long getServiceId() {
        return this.serviceId;
    }

    public int getPermissionId() {
        return this.permissionId;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}
