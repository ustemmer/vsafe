package de.versatel.noc.vsafe.server.rmi.services.mysql;

import de.versatel.noc.vSafe.services.PermissionConnector;

/**
 *
 * @author ulrich.stemmer
 */
public class MySQLPermissionConnector extends PermissionConnector {

    public MySQLPermissionConnector() {
        connectors.add(new Connector(MySQLServiceExecutor.CLOSE_ALL, MySQLPermissionDescriptions.CLOSE_ALL));
        connectors.add(new Connector(MySQLServiceExecutor.CLOSE_X, MySQLPermissionDescriptions.CLOSE_X));
        connectors.add(new Connector(MySQLServiceExecutor.CONNECT_X, MySQLPermissionDescriptions.CONNECT));
        connectors.add(new Connector(MySQLServiceExecutor.ISVALID, MySQLPermissionDescriptions.ISVALID));
        connectors.add(new Connector(MySQLServiceExecutor.LOADDRIVER, MySQLPermissionDescriptions.LOADDRIVER));
        connectors.add(new Connector(MySQLServiceExecutor.GETIDLECONNECTION, MySQLPermissionDescriptions.GETFREECONNECTION));
        connectors.add(new Connector(MySQLServiceExecutor.SETFREE, MySQLPermissionDescriptions.SETFREE));
        connectors.add(new Connector(MySQLServiceExecutor.EXECUTEQUERY, MySQLPermissionDescriptions.EXECUTEQUERY));
        connectors.add(new Connector(MySQLServiceExecutor.EXECUTEUPDATE, MySQLPermissionDescriptions.EXECUTEUPDATE));
        connectors.add(new Connector(MySQLServiceExecutor.CHECKCONNECTIONS, MySQLPermissionDescriptions.CHECKCONNECTIONS));
        connectors.add(new Connector(MySQLServiceExecutor.GETMODE, MySQLPermissionDescriptions.GETMODE));
        connectors.add(new Connector(MySQLServiceExecutor.SETMODE, MySQLPermissionDescriptions.SETMODE));
        connectors.add(new Connector(MySQLServiceExecutor.ADDCONNECTIONS, MySQLPermissionDescriptions.BUILDPOOL));
        connectors.add(new Connector(MySQLServiceExecutor.REMOVECONNECTIONS, MySQLPermissionDescriptions.DESTROYPOOL));
        connectors.add(new Connector(MySQLServiceExecutor.ISCONNECTED, MySQLPermissionDescriptions.ISCONNECTED));
        connectors.add(new Connector(MySQLServiceExecutor.GETMAXPOOLSIZE, MySQLPermissionDescriptions.GETMAXPOOLSIZE));
        connectors.add(new Connector(MySQLServiceExecutor.SETPOOLSIZE, MySQLPermissionDescriptions.SETPOOLSIZE));
        connectors.add(new Connector(MySQLServiceExecutor.GETCONNECTIONOBJECTS, MySQLPermissionDescriptions.GETPOOLOBJECTS));
        connectors.add(new Connector(MySQLServiceExecutor.SETTIMESTAMP, MySQLPermissionDescriptions.SETTIMESTAMP));
        connectors.add(new Connector(MySQLServiceExecutor.GETTIMESTAMP, MySQLPermissionDescriptions.GETTIMESTAMP));
        connectors.add(new Connector(MySQLServiceExecutor.GETCOUNTER, MySQLPermissionDescriptions.GETCOUNTER));
        connectors.add(new Connector(MySQLServiceExecutor.RESETCOUNTER, MySQLPermissionDescriptions.RESETCOUNTER));
        connectors.add(new Connector(MySQLServiceExecutor.INCREASECOUNTER, MySQLPermissionDescriptions.INCREASECOUNTER));
        connectors.add(new Connector(MySQLServiceExecutor.ISADMINBLOCKED, MySQLPermissionDescriptions.ISADMINBLOCKED));
        connectors.add(new Connector(MySQLServiceExecutor.SETADMINBLOCKED, MySQLPermissionDescriptions.SETADMINBLOCKED));
        connectors.add(new Connector(MySQLServiceExecutor.SETWATCHDOGACTIVATED, MySQLPermissionDescriptions.SETWATCHDOGACTIVATED));
        connectors.add(new Connector(MySQLServiceExecutor.ISWATCHDOGACTIVATED, MySQLPermissionDescriptions.ISWATCHDOGACTIVATED));
    }
}
