package de.versatel.noc.vsafe.server.mvc.models;

import de.versatel.noc.vSafe.mvc.AbstractModel;
import de.versatel.noc.vSafe.system.SystemProperties;
//import de.versatel.noc.vSafe.server.rmi.RMIServerInterface;
//import de.versatel.noc.vSafe.server.rmi.ServerRequestObject;
//import de.versatel.noc.vSafe.server.rmi.ServerResultObject;

/**
 *
 * @author ulrich.stemmer
 */
public class SystemPropertiesModel extends AbstractModel {

    private SystemProperties systemProperties;
    public static final String AUTOMATICRMISTART = "sp_AutomaticRMIStart";
    public static final String AUTOMATICSQLSTART = "sp_AutomaticSQLStart";
    public static final String APPLICATIONICON = "sp_ApplicationIcon";
    public static final String RMISERVERADDRESS = "sp_RMIServerAddress";
    public static final String RMIPORT = "sp_RMIPort";
    public static final String SQLSERVERDRIVER = "sp_SQLServerDriver";
    public static final String SQLSERVERURL = "sp_SQLServerURL";
    public static final String SQLSERVERIP = "sp_SQLServerIP";
    public static final String SQLSERVERPORT = "sp_SQLServerPort";
    public static final String SQLPOOLSIZE = "sp_SQLPoolsize";
    public static final String SQLMAXPOOLSIZE = "sp_SQLMaxPoolsize";
    public static final String SQLPOOLREQUESTMODE = "sp_SQLPoolRequestMode";
    public static final String SQLSERVERWATCHDOGACTIVE = "sp_SQLServerWatchdogActive";
    public static final String SQLSERVERWATCHDOGINTERVAL = "sp_SQLServerWatchdogInterval";
    public static final String SQLDEADLOCKTIMEOUT = "sp_SQLDeadlockTimeout";
    public static final String SQLSERVERDB = "sp_SQLServerDB";
    public static final String SQLSERVERUSER = "sp_SQLServerUser";
    public static final String SQLSERVERPW = "sp_SQLServerPW";
    public static final String AUTOMATICINIWINDOWCLOSE = "sp_AutomaticIniWindowClose";
    public static final String ASKFORSERVERCLOSING = "sp_AskForServerClosing";
    public static final String LOOKANDFEEL = "sp_LookAndFeel";
    public static final String SYSTEMACTIONLOGGING = "sp_SystemActionLogging";
    public static final String SYSTEMSQLLOGGING = "sp_SystemSQLLogging";
    public static final String SAVEPROPS = "sp_save";

    public SystemPropertiesModel(SystemProperties systemProperties) {

        this.systemProperties = systemProperties;
        addProperty(new MVCProperty(APPLICATIONICON, systemProperties.getApplicationIcon()));
        addProperty(new MVCProperty(ASKFORSERVERCLOSING, systemProperties.isAskForServerClosing()));
        addProperty(new MVCProperty(AUTOMATICINIWINDOWCLOSE, systemProperties.isAutomaticIniWindowClose()));
        addProperty(new MVCProperty(AUTOMATICRMISTART, systemProperties.isAutomaticRMIStart()));
        addProperty(new MVCProperty(AUTOMATICSQLSTART, systemProperties.isAutomaticSQLStart()));
        addProperty(new MVCProperty(LOOKANDFEEL, systemProperties.getLookAndFeel()));
        addProperty(new MVCProperty(RMIPORT, systemProperties.getRMIPort()));
        addProperty(new MVCProperty(RMISERVERADDRESS, systemProperties.getRMIServerAddress()));
        addProperty(new MVCProperty(SQLDEADLOCKTIMEOUT, systemProperties.getSQLDeadlockTimeout()));
        addProperty(new MVCProperty(SQLMAXPOOLSIZE, systemProperties.getSQLMaxPoolsize()));
        addProperty(new MVCProperty(SQLPOOLREQUESTMODE, systemProperties.getSQLPoolRequestMode()));
        addProperty(new MVCProperty(SQLPOOLSIZE, systemProperties.getSQLPoolsize()));
        addProperty(new MVCProperty(SQLSERVERDB, systemProperties.getSQLServerDB()));
        addProperty(new MVCProperty(SQLSERVERDRIVER, systemProperties.getSQLServerDriver()));
        addProperty(new MVCProperty(SQLSERVERIP, systemProperties.getSQLServerIP()));
        addProperty(new MVCProperty(SQLSERVERPW, systemProperties.getSQLServerPW()));
        addProperty(new MVCProperty(SQLSERVERPORT, systemProperties.getSQLServerPort()));
        addProperty(new MVCProperty(SQLSERVERURL, systemProperties.getSQLServerURL()));
        addProperty(new MVCProperty(SQLSERVERUSER, systemProperties.getSQLServerUser()));
        addProperty(new MVCProperty(SQLSERVERWATCHDOGACTIVE, systemProperties.isSQLServerWatchdogActive()));
        addProperty(new MVCProperty(SQLSERVERWATCHDOGINTERVAL, systemProperties.getSQLServerWatchdogInterval()));
        addProperty(new MVCProperty(SYSTEMACTIONLOGGING, systemProperties.isSystemActionLogging()));
        addProperty(new MVCProperty(SYSTEMSQLLOGGING, systemProperties.isSystemSQLLogging()));

    }

    @Override
    public void setPropertyValue(String propertyName, Object newValue) {
        if (propertyName.equals(APPLICATIONICON)) {
            systemProperties.setProperty(SystemProperties.ApplicationIcon, newValue);
        } else if (propertyName.equals(ASKFORSERVERCLOSING)) {
            systemProperties.setProperty(SystemProperties.AskForServerClosing, newValue);
        } else if (propertyName.equals(AUTOMATICINIWINDOWCLOSE)) {
            systemProperties.setProperty(SystemProperties.AutomaticIniWindowClose, newValue);
        } else if (propertyName.equals(AUTOMATICRMISTART)) {
            systemProperties.setProperty(SystemProperties.AutomaticRMIStart, newValue);
        } else if (propertyName.equals(AUTOMATICSQLSTART)) {
            systemProperties.setProperty(SystemProperties.AutomaticSQLStart, newValue);
        } else if (propertyName.equals(LOOKANDFEEL)) {
            systemProperties.setProperty(SystemProperties.LookAndFeel, newValue);
        } else if (propertyName.equals(RMIPORT)) {
            systemProperties.setProperty(SystemProperties.RMIPort, newValue);
        } else if (propertyName.equals(RMISERVERADDRESS)) {
            systemProperties.setProperty(SystemProperties.RMIServerAddress, newValue);
        } else if (propertyName.equals(SQLDEADLOCKTIMEOUT)) {
            systemProperties.setProperty(SystemProperties.SQLDeadlockTimeout, newValue);
        } else if (propertyName.equals(SQLMAXPOOLSIZE)) {
            systemProperties.setProperty(SystemProperties.SQLMaxPoolsize, newValue);
        } else if (propertyName.equals(SQLPOOLREQUESTMODE)) {
            systemProperties.setProperty(SystemProperties.SQLPoolRequestMode, newValue);
        } else if (propertyName.equals(SQLPOOLSIZE)) {
            systemProperties.setProperty(SystemProperties.SQLPoolsize, newValue);
        } else if (propertyName.equals(SQLSERVERDB)) {
            systemProperties.setProperty(SystemProperties.SQLServerDB, newValue);
        } else if (propertyName.equals(SQLSERVERDRIVER)) {
            systemProperties.setProperty(SystemProperties.SQLServerDriver, newValue);
        } else if (propertyName.equals(SQLSERVERIP)) {
            systemProperties.setProperty(SystemProperties.SQLServerIP, newValue);
        } else if (propertyName.equals(SQLSERVERPW)) {
            systemProperties.setProperty(SystemProperties.SQLServerPW, newValue);
        } else if (propertyName.equals(SQLSERVERPORT)) {
            systemProperties.setProperty(SystemProperties.SQLServerPort, newValue);
        } else if (propertyName.equals(SQLSERVERURL)) {
            systemProperties.setProperty(SystemProperties.SQLServerURL, newValue);
        } else if (propertyName.equals(SQLSERVERUSER)) {
            systemProperties.setProperty(SystemProperties.SQLServerUser, newValue);
        } else if (propertyName.equals(SQLSERVERWATCHDOGACTIVE)) {
            systemProperties.setProperty(SystemProperties.SQLServerWatchdogActive, newValue);
        } else if (propertyName.equals(SQLSERVERWATCHDOGINTERVAL)) {
            systemProperties.setProperty(SystemProperties.SQLServerWatchdogInterval, newValue);
        } else if (propertyName.equals(SYSTEMACTIONLOGGING)) {
            systemProperties.setProperty(SystemProperties.SystemActionLogging, newValue);
        } else if (propertyName.equals(SYSTEMSQLLOGGING)) {
            systemProperties.setProperty(SystemProperties.SystemSQLLogging, newValue);
        } else if (propertyName.equals(SAVEPROPS)) {
            try {
                systemProperties.store();
            } catch (Exception e) {
            }
        }else{
            super.setPropertyValue(propertyName, newValue);
        }
    }
}
