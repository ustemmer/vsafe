package de.versatel.noc.vsafe.server.security;

import de.versatel.noc.vsafe.vsafe_shared_services.Service;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ulrich.stemmer
 */
public abstract class PermissionDescriptions implements PermissionDescriptionsInterface {

    protected final List<PermissionDescription> permissions;
    protected final Service service;

    public PermissionDescriptions(Service service) {
        this.service = service;
        String remark = "Berechtigung für Dienst:" + service.getName();
        permissions = new ArrayList<PermissionDescription>();
        permissions.add(new PermissionDescription(service.getServiceId(), 0,  service.getName(), remark));
    }

    public List<PermissionDescription> getPermissions() {
        return permissions;
    }

    public PermissionDescription getPermissionDescription(int id) {
        for (PermissionDescription pd : permissions) {
            if (pd.getPermissionId() == id) {
                return pd;
            }
        }
        return null;
    }

    public PermissionDescription getPermissionDescription(String name) {
        if (name != null && !name.isEmpty()) {
            for (PermissionDescription pd : permissions) {
                if (pd.getName().equals(name)) {
                    return pd;
                }
            }
        }
        return null;
    }

    public int getPermissionId(String name) {
        if (name != null && !name.isEmpty()) {
            for (PermissionDescription pd : permissions) {
                if (pd.getName().equals(name)) {
                    return pd.getPermissionId();
                }
            }
        }
        return -1;
    }

}
