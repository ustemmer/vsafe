package de.versatel.noc.vsafe.server.security;

import java.util.List;

/**
 *
 * @author ulrich.stemmer
 */
public interface PermissionDescriptionsInterface {

    public List<PermissionDescription> getPermissions();
    public PermissionDescription getPermissionDescription(int id);
    public PermissionDescription getPermissionDescription(String name);
    public int getPermissionId(String name);
}
