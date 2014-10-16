package de.versatel.noc.vsafe.server.rmi.services.rmi;

import de.versatel.noc.vSafe.services.PermissionDescription;
import de.versatel.noc.vSafe.services.PermissionDescriptions;

/**
 *
 * @author ulrich.stemmer
 */
public class RMIPermissionDescriptions extends PermissionDescriptions {

    public final static int GETSTATE = 1;

        //public final static String StartStopRmiServer = "StartStopRmiServer";

    public RMIPermissionDescriptions(RMIService service) {
        super(service);
        permissions.add(new PermissionDescription(service.getServiceId(), GETSTATE, "GETSTATE", ""));
    }
}