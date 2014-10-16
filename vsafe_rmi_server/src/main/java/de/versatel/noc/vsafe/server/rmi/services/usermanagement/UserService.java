package de.versatel.noc.vsafe.server.rmi.services.usermanagement;

import de.versatel.noc.vSafe.services.Service;
import de.versatel.noc.vSafe.system.SystemSettings;

import de.versatel.noc.vSafe_Server.users.UserHandling;

import java.util.List;

/**
 *
 * @author ulrich.stemmer
 */
public class UserService extends Service {
    final UserHandling userHandling;
    final long serviceID = 1L;

    public UserService(List services, UserHandling userHandling) {
        super(services, SystemSettings.SERVICENAME_USER, SystemSettings.SERVICEID_USER);
        super.permissionDescriptions = new UserPermissionDescriptions(this);
        super.permissionConnector = new UserPermissionConnector();
        super.serviceExecutor = new UserServiceExecutor(this, userHandling);
        this.userHandling = userHandling;
    }
}
