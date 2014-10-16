package de.versatel.noc.vsafe.server.rmi.services.rmi;

//import de.versatel.noc.vSafe_Server.system.SystemServiceExecutor;
//import de.versatel.noc.vSafe_Server.system.SystemPermissionDescriptions;
import de.versatel.noc.vSafe.services.PermissionConnector;
import de.versatel.noc.vsafe.server.rmi.services.system.SystemPermissionDescriptions;
import de.versatel.noc.vsafe.server.rmi.services.system.SystemServiceExecutor;

/**
 *
 * @author ulrich.stemmer
 */
public class RMIPermissionConnector extends PermissionConnector {

    public RMIPermissionConnector() {
        connectors.add(new Connector(SystemServiceExecutor.SETPROPERTY, SystemPermissionDescriptions.ChangeSystemProperties));
        //connectors.add(new Connector(SystemServiceExecutor.INIT, SystemPermissionDescriptions));
        //connectors.add(new Connector(SystemServiceExecutor.STORE, SystemPermissionDescriptions));
        connectors.add(new Connector(SystemServiceExecutor.ISAUTOMATICRMISTART, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.SETAUTOMATICRMISTART, SystemPermissionDescriptions.ChangeSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.ISAUTOMATICSQLSTART, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.SETAUTOMATICSQLSTART, SystemPermissionDescriptions.ChangeSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.GETAPPLICATIONICON, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.GETRMISERVERADDRESS, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.SETRMISERVERADDRESS, SystemPermissionDescriptions.ChangeSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.GETRMIPORT, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.SETRMIPORT, SystemPermissionDescriptions.ChangeSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.GETSQLSERVERDRIVER, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.SETSQLSERVERDRIVER, SystemPermissionDescriptions.ChangeSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.GETSQLSERVERURL, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.SETSQLSERVERURL, SystemPermissionDescriptions.ChangeSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.GETSQLSERVERIP, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.SETSQLSERVERIP, SystemPermissionDescriptions.ChangeSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.GETSQLSERVERPORT, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.SETSQLSERVERPORT, SystemPermissionDescriptions.ChangeSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.GETSQLPOOLSIZE, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.SETSQLPOOLSIZE, SystemPermissionDescriptions.ChangeSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.GETSQLMAXPOOLSIZE, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.SETSQLMAXPOOLSIZE, SystemPermissionDescriptions.ChangeSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.GETSQLPOOLREQUESTMODE, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.SETSQLPOOLREQUESTMODE, SystemPermissionDescriptions.ChangeSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.ISSQLSERVERWATCHDOGACTIVE, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.SETSQLSERVERWATCHDOGACTIVE, SystemPermissionDescriptions.ChangeSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.GETSQLSERVERWATCHDOGINTERVAL, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.SETSQLSERVERWATCHDOGINTERVAL, SystemPermissionDescriptions.ChangeSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.GETSQLDEADLOCKTIMEOUT, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.SETSQLDEADLOCKTIMEOUT, SystemPermissionDescriptions.ChangeSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.GETSQLSERVERDB, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.SETSQLSERVERDB, SystemPermissionDescriptions.ChangeSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.GETSQLSERVERUSER, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.SETSQLSERVERUSER, SystemPermissionDescriptions.ChangeSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.GETSQLSERVERPW, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.SETSQLSERVERPW, SystemPermissionDescriptions.ChangeSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.ISAUTOMATICINIWINDOWCLOSE, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.SETAUTOMATICINIWINDOWCLOSE, SystemPermissionDescriptions.ChangeSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.ISASKFORSERVERCLOSING, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.SETASKFORSERVERCLOSING, SystemPermissionDescriptions.ChangeSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.ISSYSTEMACTIONLOGGING, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.SETSYSTEMACTIONLOGGING, SystemPermissionDescriptions.ChangeSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.ISSYSTEMSQLLOGGING, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.SETSYSTEMSQLLOGGING, SystemPermissionDescriptions.ChangeSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.GETLOOKANDFEEL, SystemPermissionDescriptions.GetSystemProperties));
        connectors.add(new Connector(SystemServiceExecutor.SETLOOKANDFEEL, SystemPermissionDescriptions.ChangeSystemProperties));
    }
}
