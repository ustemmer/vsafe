package de.versatel.noc.vsafe.server.rmi.services.mysql;

import de.versatel.noc.vSafe_Server.database.MySQL_PoolHandling;

import de.versatel.noc.vSafe.services.ServiceExecutor;

import java.util.List;

/**
 *
 * @author ulrich.stemmer
 */
public class MySQLServiceExecutor extends ServiceExecutor {

    private final MySQL_PoolHandling mySQL_Pool;
    // Methoden
    public final static int CLOSE_ALL = 1;
    public final static int CLOSE_X = 2;
    public final static int CONNECT_X = 3;
    public final static int ISVALID = 4;
    public final static int LOADDRIVER = 5;
    public final static int GETIDLECONNECTION = 6;
    public final static int SETFREE = 7;
    public final static int EXECUTEQUERY = 8;
    public final static int EXECUTEUPDATE = 9;
    public final static int CHECKCONNECTIONS = 10;
    public final static int GETMODE = 11;
    public final static int SETMODE = 12;
    public final static int ADDCONNECTIONS = 13;
    public final static int REMOVECONNECTIONS = 14;
    public final static int ISCONNECTED = 15;
    public final static int GETMAXPOOLSIZE = 16;
    public final static int SETPOOLSIZE = 17;
    public final static int GETCONNECTIONOBJECTS = 18;
    public final static int SETTIMESTAMP = 50;
    public final static int GETTIMESTAMP = 51;
    public final static int GETCOUNTER = 52;
    public final static int RESETCOUNTER = 53;
    public final static int INCREASECOUNTER = 54;
    public final static int ISADMINBLOCKED = 55;
    public final static int SETADMINBLOCKED = 56;
    public final static int SETWATCHDOGACTIVATED = 80;
    public final static int ISWATCHDOGACTIVATED = 81;

    public MySQLServiceExecutor(MySQLService service, MySQL_PoolHandling mySQL_Pool) {
        super(service);
        this.mySQL_Pool = mySQL_Pool;
    }

    public Object set(int methodId, List<Object> arguments) {
        Object o;
        switch (methodId) {
            case CLOSE_ALL:
                if (arguments.size() == 2) {
                    mySQL_Pool.close();
                }
                return null;
            case CLOSE_X:
                if (arguments.size() == 1) {
                    boolean closed = mySQL_Pool.close((Integer) arguments.get(0));
                    return closed;
                }
                return null;
            case CONNECT_X:
                if (arguments.size() == 1) {
                    return mySQL_Pool.connect((Integer) arguments.get(0));
                }
                return null;
            case LOADDRIVER:
                if (arguments == null || arguments.isEmpty()) {
                    mySQL_Pool.loadDriver();
                }
                return null;
            case SETFREE:
                if (arguments.size() == 1) {
                    mySQL_Pool.setIdle((Integer) arguments.get(0));
                }
                return null;
            case CHECKCONNECTIONS:
                if (arguments == null || arguments.isEmpty()) {
                    mySQL_Pool.checkConnections();
                }
                return null;
            case SETMODE:
                if (arguments.size() == 1) {
                    mySQL_Pool.setMode((MySQL_PoolHandling.PoolRequestMode) arguments.get(0));
                }
                return null;
            case ADDCONNECTIONS:
                if (arguments.size() == 1) {
                    mySQL_Pool.addConnections((Integer) arguments.get(0));
                }
                return null;
            case REMOVECONNECTIONS:
                if (arguments == null || arguments.isEmpty()) {
                    mySQL_Pool.removeConnections();
                }
                return null;
            case SETPOOLSIZE:
                if (arguments.size() == 1) {
                    mySQL_Pool.setPoolSize((Integer) arguments.get(0));
                }
                return null;
            case SETTIMESTAMP:
                if (arguments.size() == 1) {
                    //mySQL_Pool.setTimestamp((Integer) arguments.get(0), (Long) arguments.get(1));
                }
                return null;
            case RESETCOUNTER:
                if (arguments == null || arguments.isEmpty()) {
                    mySQL_Pool.resetCounter((Integer) arguments.get(0));
                }
                return null;
            case INCREASECOUNTER:
                if (arguments == null || arguments.isEmpty()) {
                    mySQL_Pool.increaseCounter((Integer) arguments.get(0));
                }
                return null;
            case SETADMINBLOCKED:
                if (arguments.size() == 1) {
                    mySQL_Pool.setAdminBlocked((Integer) arguments.get(0), (Boolean) arguments.get(1));
                }
                return null;
            case SETWATCHDOGACTIVATED:
                if (arguments.size() == 1) {
                    mySQL_Pool.setWatchdogActivated((Boolean) arguments.get(0));
                }
                return null;
            default:
                return null;
        }
    }

    public Object get(int methodId, List<Object> arguments) {
        Object o;
        switch (methodId) {
            /*case ISVALID:
                if (arguments.size() == 1) {
                    return mySQL_Pool..isValid((java.sql.Connection) arguments.get(0));
                }
                return null;*/
            /*case GETIDLECONNECTION:
                if (arguments == null || arguments.isEmpty()) {
                    return mySQL_Pool.getIdleConnection();
                }
                return null;*/
            case EXECUTEQUERY:
                if (arguments.size() == 2) {
                    return mySQL_Pool.executeQuery((String) arguments.get(1));
                }
                return null;
            case EXECUTEUPDATE:
                if (arguments.size() == 1) {
                    return mySQL_Pool.executeUpdate((String) arguments.get(0));
                }
                return null;
            case GETMODE:
                if (arguments == null || arguments.isEmpty()) {
                    return mySQL_Pool.getMode();
                }
                return null;
            case GETMAXPOOLSIZE:
                if (arguments == null || arguments.isEmpty()) {
                    return mySQL_Pool.getMaxPoolsize();
                }
                return null;
            case GETCONNECTIONOBJECTS:
                if (arguments == null || arguments.isEmpty()) {
                    return mySQL_Pool.getConnectionObjects();
                }
                return null;

            case GETTIMESTAMP:
                if (arguments == null || arguments.isEmpty()) {
                    return mySQL_Pool.getTimestamp((Integer) arguments.get(0));
                }
                return null;
            case GETCOUNTER:
                if (arguments == null || arguments.isEmpty()) {
                    return mySQL_Pool.getCounter((Integer) arguments.get(0));
                }
                return null;
            case ISADMINBLOCKED:
                if (arguments == null || arguments.isEmpty()) {
                    return mySQL_Pool.isAdminBlocked((Integer) arguments.get(0));
                }
                return null;
            case ISWATCHDOGACTIVATED:
                if (arguments == null || arguments.isEmpty()) {
                    return mySQL_Pool.isWatchdogActivated();
                }
                return null;
            default:
                o = null;
        }
        return o;
    }

}
