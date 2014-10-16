package de.versatel.noc.vsafe.server.rmi.services.system;

import de.versatel.noc.vSafe.services.ServiceExecutor;
import de.versatel.noc.vSafe_Server.system.SystemManager;
import java.util.List;

/**
 *
 * @author ulrich.stemmer
 */
public class SystemServiceExecutor extends ServiceExecutor {

    private SystemManager systemHandling;
    // Methoden
    public static final int SETPROPERTY = 1;
    public static final int INIT = 2;
    public static final int STORE = 3;
    public static final int ISAUTOMATICRMISTART = 4;
    public static final int SETAUTOMATICRMISTART = 5;
    public static final int ISAUTOMATICSQLSTART = 6;
    public static final int SETAUTOMATICSQLSTART = 7;
    public static final int GETAPPLICATIONICON = 8;
    public static final int GETRMISERVERADDRESS = 9;
    public static final int SETRMISERVERADDRESS = 10;
    public static final int GETRMIPORT = 11;
    public static final int SETRMIPORT = 12;
    public static final int GETSQLSERVERDRIVER = 13;
    public static final int SETSQLSERVERDRIVER = 14;
    public static final int GETSQLSERVERURL = 15;
    public static final int SETSQLSERVERURL = 16;
    public static final int GETSQLSERVERIP = 17;
    public static final int SETSQLSERVERIP = 18;
    public static final int GETSQLSERVERPORT = 19;
    public static final int SETSQLSERVERPORT = 20;
    public static final int GETSQLPOOLSIZE = 21;
    public static final int SETSQLPOOLSIZE = 22;
    public static final int GETSQLMAXPOOLSIZE = 23;
    public static final int SETSQLMAXPOOLSIZE = 24;
    public static final int GETSQLPOOLREQUESTMODE = 25;
    public static final int SETSQLPOOLREQUESTMODE = 26;
    public static final int ISSQLSERVERWATCHDOGACTIVE = 27;
    public static final int SETSQLSERVERWATCHDOGACTIVE = 28;
    public static final int GETSQLSERVERWATCHDOGINTERVAL = 29;
    public static final int SETSQLSERVERWATCHDOGINTERVAL = 30;
    public static final int GETSQLDEADLOCKTIMEOUT = 31;
    public static final int SETSQLDEADLOCKTIMEOUT = 32;
    public static final int GETSQLSERVERDB = 33;
    public static final int SETSQLSERVERDB = 34;
    public static final int GETSQLSERVERUSER = 35;
    public static final int SETSQLSERVERUSER = 36;
    public static final int GETSQLSERVERPW = 37;
    public static final int SETSQLSERVERPW = 38;
    public static final int ISAUTOMATICINIWINDOWCLOSE = 39;
    public static final int SETAUTOMATICINIWINDOWCLOSE = 40;
    public static final int ISASKFORSERVERCLOSING = 41;
    public static final int SETASKFORSERVERCLOSING = 42;
    public static final int ISSYSTEMACTIONLOGGING = 43;
    public static final int SETSYSTEMACTIONLOGGING = 44;
    public static final int ISSYSTEMSQLLOGGING = 45;
    public static final int SETSYSTEMSQLLOGGING = 46;
    public static final int GETLOOKANDFEEL = 47;
    public static final int SETLOOKANDFEEL = 48;

    public SystemServiceExecutor(SystemService service, SystemManager systemHandling) {
        super(service);
        this.systemHandling = systemHandling;
    }

    public Object set(int methodId, List<Object> arguments) {
        Object o;
        switch (methodId) {
            case SETPROPERTY:
                if (arguments.size() == 2) {
                    systemHandling.getSystemProperties().setProperty((String) arguments.get(0), (Object) arguments.get(1));
                }
                return null;
            case INIT:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().init();
                }
                return null;
            case STORE:
                if (arguments == null || arguments.isEmpty()) {
                    try {
                        systemHandling.getSystemProperties().store();
                    } catch (Exception e) {
                    }
                }
                return null;
            case SETAUTOMATICRMISTART:
                if (arguments.size() == 1) {
                    systemHandling.getSystemProperties().setAutomaticRMIStart((Boolean) arguments.get(0));
                }
                return null;
            case SETAUTOMATICSQLSTART:
                if (arguments.size() == 1) {
                    systemHandling.getSystemProperties().setAutomaticSQLStart((Boolean) arguments.get(0));
                }
                return null;
            case SETRMISERVERADDRESS:
                if (arguments.size() == 1) {
                    systemHandling.getSystemProperties().setRMIServerAddress((String) arguments.get(0));
                }
                return null;
            case SETRMIPORT:
                if (arguments.size() == 1) {
                    systemHandling.getSystemProperties().setRMIPort((Integer) arguments.get(0));
                }
                return null;
            case SETSQLSERVERDRIVER:
                if (arguments.size() == 1) {
                    systemHandling.getSystemProperties().setSQLServerDriver((String) arguments.get(0));
                }
                return null;
            case SETSQLSERVERURL:
                if (arguments.size() == 1) {
                    systemHandling.getSystemProperties().setSQLServerURL((String) arguments.get(0));
                }
                return null;
            case SETSQLSERVERIP:
                if (arguments.size() == 1) {
                    systemHandling.getSystemProperties().setSQLServerIP((String) arguments.get(0));
                }
                return null;
            case SETSQLSERVERPORT:
                if (arguments.size() == 1) {
                    systemHandling.getSystemProperties().setSQLServerPort((Integer) arguments.get(0));
                }
                return null;
            case SETSQLPOOLSIZE:
                if (arguments.size() == 1) {
                    systemHandling.getSystemProperties().setSQLPoolsize((Integer) arguments.get(0));
                }
                return null;
            case SETSQLMAXPOOLSIZE:
                if (arguments.size() == 1) {
                    systemHandling.getSystemProperties().setSQLMaxPoolsize((Integer) arguments.get(0));
                }
                return null;
            case SETSQLPOOLREQUESTMODE:
                if (arguments.size() == 1) {
                    systemHandling.getSystemProperties().setSQLPoolRequestMode((String) arguments.get(0));
                }
                return null;
            case SETSQLSERVERWATCHDOGACTIVE:
                if (arguments.size() == 1) {
                    systemHandling.getSystemProperties().setSQLServerWatchdogActive((Boolean) arguments.get(0));
                }
                return null;
            case SETSQLSERVERWATCHDOGINTERVAL:
                if (arguments.size() == 1) {
                    systemHandling.getSystemProperties().setSQLServerWatchdogInterval((Long) arguments.get(0));
                }
                return null;
            case SETSQLDEADLOCKTIMEOUT:
                if (arguments.size() == 1) {
                    systemHandling.getSystemProperties().setSQLDeadlockTimeout((Long) arguments.get(0));
                }
                return null;
            case SETSQLSERVERDB:
                if (arguments.size() == 1) {
                    systemHandling.getSystemProperties().setSQLServerDB((String) arguments.get(0));
                }
                return null;
            case SETSQLSERVERUSER:
                if (arguments.size() == 1) {
                    systemHandling.getSystemProperties().setSQLServerUser((String) arguments.get(0));
                }
                return null;
            case SETSQLSERVERPW:
                if (arguments.size() == 1) {
                    systemHandling.getSystemProperties().setSQLServerPW((String) arguments.get(0));
                }
                return null;
            case SETAUTOMATICINIWINDOWCLOSE:
                if (arguments.size() == 1) {
                    systemHandling.getSystemProperties().setAutomaticIniWindowClose((Boolean) arguments.get(0));
                }
                return null;
            case SETASKFORSERVERCLOSING:
                if (arguments.size() == 1) {
                    systemHandling.getSystemProperties().setAskForServerClosing((Boolean) arguments.get(0));
                }
                return null;
            case SETSYSTEMACTIONLOGGING:
                if (arguments.size() == 1) {
                    systemHandling.getSystemProperties().setSystemActionLogging((Boolean) arguments.get(0));
                }
                return null;
            case SETSYSTEMSQLLOGGING:
                if (arguments.size() == 1) {
                    systemHandling.getSystemProperties().setSystemSQLLogging((Boolean) arguments.get(0));
                }
                return null;
            case SETLOOKANDFEEL:
                if (arguments.size() == 1) {
                    systemHandling.getSystemProperties().setLookAndFeel((String) arguments.get(0));
                }
                return null;

            default:
                o = null;
        }
        return o;
    }

    public Object get(int methodId, List<Object> arguments) {
        Object o;
        switch (methodId) {
            case ISAUTOMATICRMISTART:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().isAutomaticRMIStart();
                }
                return null;
            case ISAUTOMATICSQLSTART:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().isAutomaticSQLStart();
                }
                return null;
            case GETAPPLICATIONICON:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().getApplicationIcon();
                }
                return null;
            case GETRMISERVERADDRESS:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().getRMIServerAddress();
                }
                return null;
            case GETRMIPORT:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().getRMIPort();
                }
                return null;
            case GETSQLSERVERDRIVER:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().getSQLServerDriver();
                }
                return null;
            case GETSQLSERVERURL:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().getSQLServerURL();
                }
                return null;
            case GETSQLSERVERIP:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().getSQLServerIP();
                }
                return null;
            case GETSQLSERVERPORT:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().getSQLServerPort();
                }
                return null;
            case GETSQLPOOLSIZE:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().getSQLPoolsize();
                }
                return null;
            case GETSQLMAXPOOLSIZE:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().getSQLMaxPoolsize();
                }
                return null;
            case GETSQLPOOLREQUESTMODE:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().getSQLPoolRequestMode();
                }
                return null;
            case ISSQLSERVERWATCHDOGACTIVE:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().isSQLServerWatchdogActive();
                }
                return null;
            case GETSQLSERVERWATCHDOGINTERVAL:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().getSQLServerWatchdogInterval();
                }
                return null;
            case GETSQLDEADLOCKTIMEOUT:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().getSQLDeadlockTimeout();
                }
                return null;
            case GETSQLSERVERDB:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().getSQLServerDB();
                }
                return null;
            case GETSQLSERVERUSER:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().getSQLServerUser();
                }
                return null;
            case GETSQLSERVERPW:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().getSQLServerPW();
                }
                return null;
            case ISAUTOMATICINIWINDOWCLOSE:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().isAutomaticIniWindowClose();
                }
                return null;
            case ISASKFORSERVERCLOSING:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().isAskForServerClosing();
                }
                return null;
            case ISSYSTEMACTIONLOGGING:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().isSystemActionLogging();
                }
                return null;
            case ISSYSTEMSQLLOGGING:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().isSystemSQLLogging();
                }
                return null;
            case GETLOOKANDFEEL:
                if (arguments == null || arguments.isEmpty()) {
                    return systemHandling.getSystemProperties().getLookAndFeel();
                }
                return null;
            default:
                o = null;
        }
        return o;
    }
}
