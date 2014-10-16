package de.versatel.noc.vsafe.server.rmi.services.rmi;

import de.versatel.noc.vSafe_Server.rmi.RMIServerHandling;

import de.versatel.noc.vSafe.services.Service;
import de.versatel.noc.vSafe.system.SystemSettings;

import java.util.List;

/**
 *
 * @author ulrich.stemmer
 */
public class RMIService extends Service {
    final long serviceID = 2L;
    final RMIServerHandling rMIServerHandling;

    public RMIService(List services, RMIServerHandling rMIServerHandling) {
        super(services, SystemSettings.SERVICENAME_RMI, SystemSettings.SERVICEID_RMI);
        super.permissionDescriptions = new RMIPermissionDescriptions(this);
        super.permissionConnector = new RMIPermissionConnector();
        super.serviceExecutor = new RMIServiceExecutor(this, rMIServerHandling);
        this.rMIServerHandling = rMIServerHandling;
    }
}
