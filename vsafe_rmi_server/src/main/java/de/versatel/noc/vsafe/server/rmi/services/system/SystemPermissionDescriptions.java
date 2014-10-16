package de.versatel.noc.vsafe.server.rmi.services.system;

import de.versatel.noc.vSafe.services.PermissionDescriptions;
import de.versatel.noc.vSafe.services.PermissionDescription;

/**
 *
 * @author ulrich.stemmer
 */
public class SystemPermissionDescriptions extends PermissionDescriptions {

    // System
    public final static int GetSystemSettings = 1;
    public final static int GetSystemProperties = 2;
    public final static int ChangeSystemProperties = 3;

    public SystemPermissionDescriptions(SystemService service) {
        super(service);
        permissions.add(new PermissionDescription(service.getServiceId(), GetSystemSettings, "GetSystemSettings", "Systemvariablen: Alle holen"));
        permissions.add(new PermissionDescription(service.getServiceId(), GetSystemProperties, "GetSystemProperties", "Systemeinstellungen: holen"));
        permissions.add(new PermissionDescription(service.getServiceId(), ChangeSystemProperties, "ChangeSystemProperties", "Systemeinstellungen: ändern"));
    }
}
