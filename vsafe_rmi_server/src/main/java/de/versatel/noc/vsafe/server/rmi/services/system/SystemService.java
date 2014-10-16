package de.versatel.noc.vsafe.server.rmi.services.system;

import de.versatel.noc.vSafe.services.Service;
import de.versatel.noc.vSafe.system.SystemSettings;

import de.versatel.noc.vSafe_Server.system.SystemManager;

import java.util.List;

/**
 *
 * @author ulrich.stemmer
 */
public class SystemService extends Service {
    final SystemManager systemHandling;

    public SystemService(List services, SystemManager systemHandling) {
        super(services, SystemSettings.SERVICENAME_SYSTEM,SystemSettings.SERVICEID_SYSTEM);
        super.permissionDescriptions = new SystemPermissionDescriptions(this);
        super.permissionConnector = new SystemPermissionConnector();
        super.serviceExecutor = new SystemServiceExecutor(this, systemHandling);
        this.systemHandling = systemHandling;
    }
}
