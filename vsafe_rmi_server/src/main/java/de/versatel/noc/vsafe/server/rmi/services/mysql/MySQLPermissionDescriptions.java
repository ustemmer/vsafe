package de.versatel.noc.vsafe.server.rmi.services.mysql;

import de.versatel.noc.vSafe.services.PermissionDescriptions;
import de.versatel.noc.vSafe.services.PermissionDescription;

/**
 *
 * @author ulrich.stemmer
 */
public class MySQLPermissionDescriptions extends PermissionDescriptions {

    public final static int CLOSE_ALL = 1;
    public final static int CLOSE_X = 2;
    public final static int CONNECT = 3;
    public final static int ISVALID = 4;
    public final static int LOADDRIVER = 5;
    public final static int GETFREECONNECTION = 6;
    public final static int SETFREE = 7;
    public final static int EXECUTEQUERY = 8;
    public final static int EXECUTEUPDATE = 9;
    public final static int CHECKCONNECTIONS = 10;
    public final static int GETMODE = 11;
    public final static int SETMODE = 12;
    public final static int BUILDPOOL = 13;
    public final static int DESTROYPOOL = 14;
    public final static int ISCONNECTED = 15;
    public final static int GETMAXPOOLSIZE = 16;
    public final static int SETPOOLSIZE = 17;
    public final static int GETPOOLOBJECTS = 18;
    public final static int SETTIMESTAMP = 50;
    public final static int GETTIMESTAMP = 51;
    public final static int GETCOUNTER = 52;
    public final static int RESETCOUNTER = 53;
    public final static int INCREASECOUNTER = 54;
    public final static int ISADMINBLOCKED = 55;
    public final static int SETADMINBLOCKED = 56;
    public final static int SETWATCHDOGACTIVATED = 80;
    public final static int ISWATCHDOGACTIVATED = 81;

    public MySQLPermissionDescriptions(MySQLService service) {
        super(service);

        permissions.add(new PermissionDescription(service.getServiceId(), CLOSE_ALL, "CLOSE_ALL", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), CLOSE_X, "CLOSE_X", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), CONNECT, "CONNECT", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), ISVALID, "ISVALID", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), LOADDRIVER, "LOADDRIVER", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), GETFREECONNECTION, "GETFREECONNECTION", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), SETFREE, "SETFREE", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), EXECUTEQUERY, "EXECUTEQUERY", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), EXECUTEUPDATE, "EXECUTEUPDATE", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), CHECKCONNECTIONS, "CHECKCONNECTIONS", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), GETMODE, "GETMODE", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), SETMODE, "SETMODE", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), BUILDPOOL, "BUILDPOOL", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), DESTROYPOOL, "DESTROYPOOL", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), ISCONNECTED, "ISCONNECTED", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), GETMAXPOOLSIZE, "GETMAXPOOLSIZE", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), SETPOOLSIZE, "SETPOOLSIZE", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), GETPOOLOBJECTS, "GETPOOLOBJECTS", ""));

        permissions.add(new PermissionDescription(service.getServiceId(), SETTIMESTAMP, "SETTIMESTAMP", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), GETTIMESTAMP, "GETTIMESTAMP", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), GETCOUNTER, "GETCOUNTER", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), RESETCOUNTER, "RESETCOUNTER", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), INCREASECOUNTER, "INCREASECOUNTER", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), ISADMINBLOCKED, "ISADMINBLOCKED", ""));
        permissions.add(new PermissionDescription(service.getServiceId(), SETADMINBLOCKED, "SETADMINBLOCKED", ""));
    }
}
