package de.versatel.noc.vsafe.server.rmi.services.mysql;

import de.versatel.noc.vSafe_Server.database.MySQL_PoolHandling;
import de.versatel.noc.vSafe_Server.database.MySQL_Watchdog;

import de.versatel.noc.vSafe.services.Service;
import de.versatel.noc.vSafe.system.SystemSettings;

import java.util.List;

/**
 *
 * @author ulrich.stemmer
 */
public class MySQLService extends Service {
    final MySQL_PoolHandling mySQL_Pool;

    public MySQLService(List services, MySQL_PoolHandling mySQL_Pool) {
        super(services, SystemSettings.SERVICENAME_MYSQL, SystemSettings.SERVICEID_MYSQL);
        super.permissionDescriptions = new MySQLPermissionDescriptions(this);
        super.permissionConnector = new MySQLPermissionConnector();
        super.serviceExecutor = new MySQLServiceExecutor(this, mySQL_Pool);
        this.mySQL_Pool = mySQL_Pool;
    }
}
