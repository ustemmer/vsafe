package de.versatel.noc.vsafe.server.mvc.models;

import de.versatel.noc.vSafe.mvc.AbstractModel;
import de.versatel.noc.vSafe_Server.database.MySQL_PoolHandling;
import de.versatel.noc.vSafe_Server.mvc.controllers.MySQLPoolController;

/**
 *
 * @author ulrich.stemmer
 */
public class MySQLPoolModel extends AbstractModel {

    // Variablen
    private final MySQL_PoolHandling mySQL_Pool;
    private MySQLPoolController mySQLPoolController;
    // Model-Properties
    public static final String POOL_ERROR = "pool_error";
    public static final String POOL_REQUESTMODE = "pool_requestmode";
    public static final String POOL_DRIVER = "pool_driver";
    public static final String POOL_URL = "pool_url";
    public static final String POOL_USER = "pool_user";
    public static final String POOL_PASSWORD = "pool_password";
    public static final String POOL_SIZE = "pool_size";
    public static final String POOL_MAXSIZE = "pool_maxsize";
    public static final String POOL_TIMEOUT = "pool_timeout";
    public static final String POOL_CONNECTED = "pool_connected";
    public static final String POOL_WDACTIVE = "pool_wdactive";
    public static final String POOL_WDINTERVAL = "pool_wdinterval";
    public static final String POOL_LOGGING = "pool_logging";
    public static final String POOL_STATE = "pool_state";
    //für void Methoden: View > Model > Pool
    public static final String POOL_ADDCONNECTION = "pool_addconnection";
    public static final String POOL_CHECKCONNECTIONS = "pool_checkconnections";
    public static final String POOL_REMOVECONNECTION = "pool_removeconnection";
    public static final String POOL_CONNECT = "pool_connect";
    public static final String POOL_DISCONNECT = "pool_disconnect";
    public static final String POOL_SETREQUESTMODE = "pool_setrequestmode";
    public static final String POOL_SETDRIVER = "pool_setdriver";
    public static final String POOL_SETURL = "pool_seturl";
    public static final String POOL_SETUSER = "pool_setuser";
    public static final String POOL_SETPASSWORD = "pool_setpassword";
    public static final String POOL_SETSIZE = "pool_setsize";
    public static final String POOL_SETMAXSIZE = "pool_setmaxsize";
    public static final String POOL_SETTIMEOUT = "pool_settimeout";
    public static final String POOL_SETWDACTIVE = "pool_setwdactive";
    public static final String POOL_SETWDINTERVAL = "pool_setwdinterval";
    public static final String POOL_SETLOGGING = "pool_setlogging";

    public MySQLPoolModel(MySQL_PoolHandling mySQL_Pool, MySQLPoolController mySQLPoolController) {
        super();
        this.mySQL_Pool = mySQL_Pool;
        this.mySQLPoolController = mySQLPoolController;
        addProperty(new MVCProperty(POOL_REQUESTMODE, mySQL_Pool.getMode()));
        addProperty(new MVCProperty(POOL_DRIVER, mySQL_Pool.getDriver()));
        addProperty(new MVCProperty(POOL_URL, mySQL_Pool.getUrl()));
        addProperty(new MVCProperty(POOL_USER, ""));
        addProperty(new MVCProperty(POOL_PASSWORD, ""));
        addProperty(new MVCProperty(POOL_SIZE, mySQL_Pool.getPoolsize()));
        addProperty(new MVCProperty(POOL_MAXSIZE, mySQL_Pool.getMaxPoolsize()));
        addProperty(new MVCProperty(POOL_TIMEOUT, mySQL_Pool.getTimeout()));
        addProperty(new MVCProperty(POOL_CONNECTED, mySQL_Pool.getConnected()));
        addProperty(new MVCProperty(POOL_LOGGING, mySQL_Pool.isLoggingActive()));
        addProperty(new MVCProperty(POOL_WDACTIVE, mySQL_Pool.isWatchdogActivated()));
        addProperty(new MVCProperty(POOL_WDINTERVAL, mySQL_Pool.isWatchdogActivated()));
        addProperty(new MVCProperty(POOL_STATE, mySQL_Pool.getPoolstate()));
    }

    @Override
    public void setPropertyValue(String propertyName, Object newValue) {
        if (propertyName.equals(POOL_ADDCONNECTION)) {
            mySQL_Pool.addConnection();
        } else if (propertyName.equals(POOL_CHECKCONNECTIONS)) {
            mySQL_Pool.checkConnections();
        } else if (propertyName.equals(POOL_REMOVECONNECTION)) {
            mySQL_Pool.removeConnection();
        } else if (propertyName.equals(POOL_CONNECT)) {
            mySQL_Pool.connect();
        } else if (propertyName.equals(POOL_DISCONNECT)) {
            mySQL_Pool.close();

        } else if (propertyName.equals(POOL_SETREQUESTMODE)) {
            mySQL_Pool.setMode((MySQL_PoolHandling.PoolRequestMode) newValue);
        } else if (propertyName.equals(POOL_SETDRIVER)) {
            mySQL_Pool.setDriver((String) newValue);
        } else if (propertyName.equals(POOL_SETURL)) {
            mySQL_Pool.setUrl((String) newValue);
        } else if (propertyName.equals(POOL_SETUSER)) {
            mySQL_Pool.setUser((String) newValue);
        } else if (propertyName.equals(POOL_SETPASSWORD)) {
            mySQL_Pool.setPassword((String) newValue);
        } else if (propertyName.equals(POOL_SETSIZE)) {
            //System.out.println("poolmodel, newsize=" + (Integer) newValue);
            mySQL_Pool.setPoolSize((Integer) newValue);
        } else if (propertyName.equals(POOL_SETMAXSIZE)) {
            mySQL_Pool.setMaxPoolsize((Integer) newValue);
        } else if (propertyName.equals(POOL_SETTIMEOUT)) {
            mySQL_Pool.setTimeout((Long) newValue);
        } else if (propertyName.equals(POOL_SETLOGGING)) {
            mySQL_Pool.setLoggingActive((Boolean) newValue);
        } else if (propertyName.equals(POOL_SETWDACTIVE)) {
            mySQL_Pool.setWatchdogActivated((Boolean) newValue);
        } else if (propertyName.equals(POOL_SETWDINTERVAL)) {
            mySQL_Pool.setInterval((Long) newValue);

        } else {
            super.setPropertyValue(propertyName, newValue);
        }


    }

    public MySQLPoolController getMySQLPoolController() {
        return mySQLPoolController;
    }

    public void setMySQLPoolController(MySQLPoolController mySQLPoolController) {
        this.mySQLPoolController = mySQLPoolController;
    }
}
