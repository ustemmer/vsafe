package de.versatel.noc.vsafe.common.services;

import de.versatel.noc.vsafe.vsafe_server_security.PermissionConnector;
import de.versatel.noc.vsafe.vsafe_server_security.PermissionDescriptions;
import java.util.List;

/**
 *
 * @author ulrich.stemmer
 */
public abstract class Service implements ServiceInterface {

    protected List services;
    protected final long serviceId;
    protected String name;
    protected PermissionDescriptions permissionDescriptions;
    protected PermissionConnector permissionConnector;
    protected ServiceExecutor serviceExecutor;

    public Service(String serviceName, long serviceId) {
        this.name = serviceName;
        this.serviceId = serviceId;
    }

    public Service(List services, String serviceName, long serviceId) {
        this.services = services;
        this.name = serviceName;
        this.serviceId = serviceId;
    }

    public String getName() {
        return this.name;
    }

    public long getServiceId() {
        return this.serviceId;
    }

    public PermissionDescriptions getPermissionsDescriptions() {
        return this.permissionDescriptions;
    }

    public PermissionConnector getPermissionConnector() {
        return this.permissionConnector;
    }

    public ServiceExecutor getServiceExecutor() {
        return this.serviceExecutor;
    }

    public Object set(int methodId, List<Object> arguments) {
        if (this.serviceExecutor != null) {
            return serviceExecutor.set(methodId, arguments);
        }
        return null;
    }

    public Object get(int methodId, List<Object> arguments) {
        if (this.serviceExecutor == null) {
            return null;
        }
        return serviceExecutor.get(methodId, arguments);
    }

    public int getPermissionId(int methodId) {
        if (permissionConnector == null) {
            return -1;
        }
        return permissionConnector.getPermissionId(methodId);
    }

    public List getServices() {
        return services;

    }

    public void init() {
    }
}
