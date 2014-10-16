package de.versatel.noc.vsafe.server.data;

import de.versatel.noc.vSafe.system.exceptions.ExceptionCodes;
import de.versatel.noc.vSafe.system.exceptions.VSafeException;
import de.versatel.noc.vSafe_Server.mvc.models.MySQLConnectionObjectModel;
import de.versatel.noc.vSafe_Server.mvc.models.MySQLPoolModel;
import de.versatel.noc.vSafe_Server.system.SystemManager;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ulrich.stemmer
 */
public class MySQL_PoolHandling {

    public static enum PoolRequestMode {

        SEQUENZIELL, ZYKLISCH
    };

    public static enum PoolState {

        CONNECTED, CONNECTING, DISCONNECTED, DISCONNECTING
    };
    public static final int MAXPOOLSIZELIMIT = 30;
    //SystemLogging systemLogging;
    SystemManager systemManager;
    private PoolState poolstate;
    private PoolRequestMode mode;
    private MySQLPoolModel mySQLPoolModel;
    private int lastUsedConnection;
    private boolean loggingActive;
    private final List<MySQL_ConnectionObject> mySQL_ConnectionObjects;
    private String driver;
    private String url;
    private String user;
    private String password;
    private int maxPoolsize;
    private long timeout;
    private boolean autoconnect;
    // MySQL_Watchdog:
    private MySQL_Watchdog watchdog;
    private long interval;
    int rows;

    /**
     * Konstruktor
     * @param systemLogging
     */
    public MySQL_PoolHandling(SystemManager systemManager) {
        this.systemManager = systemManager;
        mySQL_ConnectionObjects = new ArrayList<MySQL_ConnectionObject>();
    }

    public boolean addConnection() throws VSafeException {
        if (mySQL_ConnectionObjects.size() >= this.maxPoolsize) {
            return false;
        }
        try {
            MySQL_ConnectionObject mySQL_ConnectionObject = new MySQL_ConnectionObject(url, user, password);
            mySQL_ConnectionObjects.add(mySQL_ConnectionObject);
            mySQL_ConnectionObject.setIndex(mySQL_ConnectionObjects.size() - 1);
            if (mySQLPoolModel != null && mySQLPoolModel.getMySQLPoolController() != null) {
                mySQLPoolModel.getMySQLPoolController().addConnectionModel(mySQL_ConnectionObject);
            }
            return true;
        } catch (SQLException e) {
            if (loggingActive && systemManager.getSystemLogging() != null) {
                systemManager.getSystemLogging().log(e);
            }
        }
        return false;
    }

    /**
     * Bereitet die Verbindungen des Pools vor. Anzahl der Verbindungem richtet sich nach dem Wert 'initPoolsize'
     */
    public void addConnections(int connsToAdd) throws VSafeException {
        if (connsToAdd <= 0) {
            throw new VSafeException(
                    "MySQL_PoolHandling.addConnections",
                    ExceptionCodes.MYSQL_POOL_POOLSIZEMISMATCH,
                    "Falscher Wert: '" + connsToAdd + "'");
        }
        int newSize = mySQL_ConnectionObjects.size() + connsToAdd;
        if (newSize > maxPoolsize) {
            throw new VSafeException(
                    "MySQL_PoolHandling.addConnections",
                    ExceptionCodes.MYSQL_POOL_POOLSIZEMISMATCH,
                    "Abbruch: Wert '" + connsToAdd + "' würde maximale Poolgröße überschreiten (" + maxPoolsize + ").");
        }
        for (int i = mySQL_ConnectionObjects.size(); i < newSize; i++) {
            addConnection();
        }
    }

    /**
     * Prueft, ob alle Verbindungen des Pool noch leben und
     * erweckt die eingeschlafenen Verbindungen wieder zum leben.
     */
    public synchronized void checkConnections() throws VSafeException {
        for (MySQL_ConnectionObject mySQL_ConnectionObject : mySQL_ConnectionObjects) {
            if (mySQL_ConnectionObject != null && !mySQL_ConnectionObject.isIdle()) {
                if (System.currentTimeMillis() - mySQL_ConnectionObject.getTimestamp_lastCommand() > timeout) {
                    if (mySQL_ConnectionObject.isValid()) {
                        mySQL_ConnectionObject.setIdle(true);
                        continue;
                    } else {
                        try {
                            mySQL_ConnectionObject.getConnection().close();
                            mySQL_ConnectionObject.setConnection(DriverManager.getConnection(url, user, password));
                            mySQL_ConnectionObject.setUpdateStatement(
                                    mySQL_ConnectionObject.getConnection().createStatement(
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE));
                            mySQL_ConnectionObject.setQueryStatement(mySQL_ConnectionObject.getConnection().createStatement(
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY));
                            mySQL_ConnectionObject.setIdle(true);
                        } catch (SQLException e) {
                            throw new VSafeException(
                                    "MySQL_PoolHandling.checkConnections",
                                    ExceptionCodes.DB_NOCONNECTION,
                                    "Fehler beim Datenbankzugriff!",
                                    e);
                        }
                    }
                }
            }
        }
    }

    /**
     * Verbindung zum SQL-Server abbauen.
     * Gibt true zurueck, wenn die Verbindung abgebaut wurde.
     * @param connectionNumber
     * @return boolean
     */
    public boolean close(int connectionNumber) throws VSafeException {
        MySQL_ConnectionObject mySQL_ConnectionObject = mySQL_ConnectionObjects.get(connectionNumber);
        return mySQL_ConnectionObject.disconnect();
    }

    /**
     * Trennt die Verbindungen zur Datenbank.
     */
    public void close() throws VSafeException {
        if (mySQL_ConnectionObjects == null) {
            return;
        }
        if (mySQL_ConnectionObjects.isEmpty()) {
            return;
        }
        setState(PoolState.DISCONNECTING);
        for (int i = 0; i < mySQL_ConnectionObjects.size(); i++) {
            MySQL_ConnectionObject mySQL_ConnectionObject = mySQL_ConnectionObjects.get(i);
            if (mySQL_ConnectionObject != null) {
                close(i);
                mySQL_ConnectionObject = null;
            }
        }
        setState(PoolState.DISCONNECTED);
    }

    /**
     * Methode um die Verbindung zum SQL-Server aufzubauen.
     * Gibt true zurueck, wenn der Aufbau der Verbindung erfolgreich war.
     *
     * @param connectionNumber
     * @return boolean
     *
     */
    public boolean connect(int connectionNumber) throws VSafeException {
        if (mySQL_ConnectionObjects.get(connectionNumber).connect()) {
            setState(PoolState.CONNECTED);
            return true;
        }
        return false;
    }

    public int connect() {
        for (MySQL_ConnectionObject mco : mySQL_ConnectionObjects) {
            mco.connect();
        }
        return getConnected();
    }

    /**
     * Führt eine SELECT-Anweisung aus.
     *
     * @param connectionNumber
     * @param sql
     * @return
     */
    public ResultSet executeQuery(String sql) throws VSafeException {
        int connectionNumber = getIdleConnection();
        if (connectionNumber < 0) {
            return null;
        }

        try {
            MySQL_ConnectionObject mco = mySQL_ConnectionObjects.get(connectionNumber);
            ResultSet r = mco.executeQuery(sql);
            mySQL_ConnectionObjects.get(connectionNumber).setIdle(true);
            return r;
        } catch (VSafeException e) {
            e.addInfo("MySQL_PoolHandling.executeQuery", ExceptionCodes.DB_NOCONNECTION);
            throw e;
        }
    }

    /**
     * Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE statement or an SQL statement that returns nothing,
     * such as an SQL DDL statement.
     * @param sql - an SQL Data Manipulation Language (DML) statement, such as INSERT, UPDATE or DELETE;
     * or an SQL statement that returns nothing, such as a DDL statement.
     * @throws SQLException - if a database access error occurs, this method is called on a closed Statement
     * or the given SQL statement produces a ResultSet object
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing.
     */
    public int executeUpdate(String sql) throws VSafeException {
        int connectionNumber = getIdleConnection();
        if (connectionNumber < 0) {
            return connectionNumber;
        }
        MySQL_ConnectionObject mySQL_ConnectionObject = mySQL_ConnectionObjects.get(connectionNumber);
        try {
            int i = mySQL_ConnectionObject.executeUpdate(sql);
            mySQL_ConnectionObjects.get(connectionNumber).setIdle(true);
            return i;

        } catch (VSafeException e) {
            e.addInfo("MySQL_PoolHandling.executeUpdate", ExceptionCodes.DB_NOCONNECTION);
            throw e;
        } finally {
            return -1;
        }
    }

    public int getConnected() throws VSafeException {
        int i = 0;
        for (MySQL_ConnectionObject mco : mySQL_ConnectionObjects) {
            if (mco.isConnected()) {
                i += 1;
            }
        }
        return i;
    }

    public List<MySQL_ConnectionObject> getConnectionObjects() {
        return mySQL_ConnectionObjects;
    }

    public long getCounter(int connection) throws VSafeException {
        MySQL_ConnectionObject MySQL_ConnectionObject = mySQL_ConnectionObjects.get(connection);
        if (MySQL_ConnectionObject != null) {
            return MySQL_ConnectionObject.getCounter();
        }
        return -1;
    }

    public String getDriver() {
        return driver;
    }

    /**
     * Gibt eine freie Verbindung zurueck und reserviert diese.
     * @return
     */
    private int getIdleConnection() throws VSafeException {
        int n;
        if (mode.equals(PoolRequestMode.ZYKLISCH)) {
            int temp = lastUsedConnection;
            for (n = 0; n < mySQL_ConnectionObjects.size(); n++) {
                if (temp >= mySQL_ConnectionObjects.size()) {
                    temp = 0;
                } else {
                    temp += 1;
                }
                if (mySQL_ConnectionObjects.get(temp) != null && mySQL_ConnectionObjects.get(temp).isIdle()) {
                    lastUsedConnection = temp;
                    mySQL_ConnectionObjects.get(temp).setIdle(false);
                    mySQL_ConnectionObjects.get(temp).increaseCounter();
                    return lastUsedConnection;
                }
            }
        } else {
            for (n = 0; n < mySQL_ConnectionObjects.size(); n++) {
                if (mySQL_ConnectionObjects.get(n) != null && mySQL_ConnectionObjects.get(n).isIdle()) {
                    mySQL_ConnectionObjects.get(n).setIdle(false);
                    mySQL_ConnectionObjects.get(n).increaseCounter();
                    return n;
                }
            }
        }
        return -1;
    }

    public long getInterval() {
        return interval;
    }

    public int getMaxPoolsize() {
        return maxPoolsize;
    }

    public PoolRequestMode getMode() {
        return mode;
    }

    public int getPoolsize() {
        return mySQL_ConnectionObjects.size();
    }

    public PoolState getPoolstate() {
        return poolstate;
    }

    public long getTimeout() {
        return timeout;
    }

    public String getUrl() {
        return url;
    }

    public long getTimestamp(int connection) {
        MySQL_ConnectionObject MySQL_ConnectionObject = mySQL_ConnectionObjects.get(connection);
        if (MySQL_ConnectionObject != null) {
            return MySQL_ConnectionObject.getTimestamp_lastCommand();
        }
        return -1;
    }

    public void increaseCounter(int connection) {
        MySQL_ConnectionObject MySQL_ConnectionObject = mySQL_ConnectionObjects.get(connection);
        if (MySQL_ConnectionObject != null) {
            MySQL_ConnectionObject.increaseCounter();
        }
    }

    public void init (
            String drivername,
            String serverURL,
            int poolSize,
            int maxPoolsize,
            PoolRequestMode requestMode,
            String mySQLUser,
            String mySQLPassword,
            boolean activateWatchdog,
            boolean autoConnect) {
        int retVal = 0;

        // Treiber setzen & URL
        if (drivername == null || drivername.isEmpty()) {
            retVal += 1;
        } else {
            setDriver(drivername);
        }
        if (serverURL == null || serverURL.isEmpty()) {
            retVal += 2;
        } else {
            setUrl(serverURL);
        }
        switch (retVal) {
            case 0:
                break;
            case 1:
                throw new VSafeException(
                        "MySQL_PoolHandling.init",
                        ExceptionCodes.MYSQL_POOL_EMPTYDRIVER);
            case 2:
                throw new VSafeException(
                        "MySQL_PoolHandling.init",
                        ExceptionCodes.MYSQL_POOL_EMPTYSERVERURL);
            case 3:
                throw new VSafeException(
                        "MySQL_PoolHandling.init",
                        ExceptionCodes.MYSQL_POOL_EMPTYDRIVERSERVERURL);
        }

        // Abfrage-Modus setzen
        if (requestMode.equals(PoolRequestMode.ZYKLISCH)) {
            setMode(PoolRequestMode.ZYKLISCH);
        } else if (requestMode.equals(PoolRequestMode.SEQUENZIELL)) {
            setMode(PoolRequestMode.SEQUENZIELL);
        } else {
            setMode(PoolRequestMode.SEQUENZIELL);
            throw new VSafeException(
                    "MySQL_PoolHandling.init",
                    ExceptionCodes.MYSQL_POOL_UNKNOWNMODE,
                    "Setze Default:" + PoolRequestMode.SEQUENZIELL);
        }

        // Username setzen
        if (mySQLUser == null || mySQLUser.isEmpty()) {
            throw new VSafeException(
                    "MySQL_PoolHandling.init",
                    ExceptionCodes.MYSQL_POOL_EMPTYUSERNAME);
        }
        setUser(mySQLUser);

        // Passwort setzen
        if (mySQLPassword == null || mySQLPassword.isEmpty()) {
            throw new VSafeException(
                    "MySQL_PoolHandling.init",
                    ExceptionCodes.MYSQL_POOL_EMPTYPASSWORD);
        }
        setPassword(mySQLPassword);

        // Default State setzen
        poolstate = poolstate.DISCONNECTED;

        // Pool erzeugen
        // Maximale Poolgröße setzen
        setMaxPoolsize(maxPoolsize);

        // Poolgröße setzen
        setPoolSize(poolSize);

        if (autoConnect) {
            connect();
        }

        // Watchdog aktivieren
        if (activateWatchdog) {
            watchdog = new MySQL_Watchdog(this, interval);
        } else {
            if (watchdog != null) {
                watchdog.thread.interrupt();
                watchdog = null;
            }
        }
    }

    public boolean isAdminBlocked(int connection) {
        MySQL_ConnectionObject MySQL_ConnectionObject = mySQL_ConnectionObjects.get(connection);
        if (MySQL_ConnectionObject != null) {
            return MySQL_ConnectionObject.isAdminBlocked();
        }
        return true;
    }

    public boolean isAutoconnect() {
        return autoconnect;
    }

    public boolean isLoggingActive() {
        return loggingActive;
    }

    public boolean isWatchdogActivated() {
        if (watchdog == null || !watchdog.active) {
            return false;
        }
        return true;
    }

    /**
     * Methode um den Treiber zu laden.
     * Gibt true zurueck, enn der Treiber erfolgreich geladen wurde.
     *
     * @return
     */
    public void loadDriver() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new VSafeException(
                    "MySQL_PoolHandling.loadDriver",
                    ExceptionCodes.MYSQL_POOL_DRIVERNOTFOUND,
                    null,
                    e);
        }

    }

    public void resetCounter(int connection) {
        MySQL_ConnectionObject MySQL_ConnectionObject = mySQL_ConnectionObjects.get(connection);
        if (MySQL_ConnectionObject != null) {
            MySQL_ConnectionObject.resetCounter();
        }
    }

    public void removeConnection() {
        int index = mySQL_ConnectionObjects.size() - 1;
        if (index >= 0) {
            removeConnection(mySQL_ConnectionObjects.size() - 1);
        }
    }

    public void removeConnection(int index) {
        MySQL_ConnectionObject mco = mySQL_ConnectionObjects.get(index);
        if (mco != null) {
            boolean result = mco.disconnect();
            MySQLConnectionObjectModel mcom = mco.getMySQL_ConnectionObjectModel();
            if (mcom != null) {
                mySQLPoolModel.getMySQLPoolController().removeModel(mcom);
                mcom = null;
            }
            //System.out.println("poolHandling, model removed:" + index);
            mySQL_ConnectionObjects.remove(mco);
            mco = null;
            //System.out.println("poolHandling, connobject removed:" + index);
        }
    }

    public void removeConnections() {
        int index = mySQL_ConnectionObjects.size() - 1;
        for (int i = index; index > 0; index--) {
            removeConnection(i);
        }
    }

    public void setAdminBlocked(int connection, boolean value) {
        MySQL_ConnectionObject MySQL_ConnectionObject = mySQL_ConnectionObjects.get(connection);
        if (MySQL_ConnectionObject != null) {
            MySQL_ConnectionObject.setAdminBlocked(value);
        }
    }

    public void setAutoconnect(boolean autoconnect) {
        this.autoconnect = autoconnect;
    }

    public void setDriver(String driver) {
        this.driver = driver;
        if (mySQLPoolModel != null) {
            mySQLPoolModel.setPropertyValue(MySQLPoolModel.POOL_DRIVER, driver);
        }
    }

    /**
     * Markiert die entsprechende Verbindung als frei.
     *
     * @param connectionNumber
     */
    public synchronized void setIdle(int connectionNumber) {
        mySQL_ConnectionObjects.get(connectionNumber).setIdle(true);
    }

    public void setInterval(long interval) {
        this.interval = interval;
        if (mySQLPoolModel != null) {
            mySQLPoolModel.setPropertyValue(MySQLPoolModel.POOL_WDINTERVAL, interval);
        }
    }

    public void setLoggingActive(boolean loggingActive) {
        this.loggingActive = loggingActive;
        if (mySQLPoolModel != null) {
            mySQLPoolModel.setPropertyValue(MySQLPoolModel.POOL_LOGGING, loggingActive);
        }
    }

    public void setMaxPoolsize(int maxPoolsize) {
        if (maxPoolsize > MAXPOOLSIZELIMIT) {
            throw new VSafeException("MySQL_PoolHandling.setMaxPoolsize", ExceptionCodes.MYSQL_POOL_POOLSIZEOVERSIZED);
        }
        if (maxPoolsize < 0) {
            throw new VSafeException("MySQL_PoolHandling.setMaxPoolsize", ExceptionCodes.MYSQL_POOL_POOLSIZEUNDERSIZED);
        }
        this.maxPoolsize = maxPoolsize;
        if (mySQLPoolModel != null) {
            mySQLPoolModel.setPropertyValue(MySQLPoolModel.POOL_MAXSIZE, maxPoolsize);
        }
    }

    public void setMode(PoolRequestMode mode) {
        this.mode = mode;
        if (mySQLPoolModel != null) {
            mySQLPoolModel.setPropertyValue(MySQLPoolModel.POOL_REQUESTMODE, mode);
        }
    }

    public void setMySQLPoolModel(MySQLPoolModel mySQLPoolModel) {
        this.mySQLPoolModel = mySQLPoolModel;
    }

    public synchronized void setPoolSize(int newSize) {
        if (newSize < 0) {
            throw new VSafeException(
                    "MySQL_PoolHandling.setPoolSize",
                    ExceptionCodes.MYSQL_POOL_POOLSIZEUNDERSIZED,
                    "Wert kleiner 0 : '" + newSize + "'.");
        }
        if (newSize > maxPoolsize) {
            throw new VSafeException(
                    "MySQL_PoolHandling.setPoolSize",
                    ExceptionCodes.MYSQL_POOL_POOLSIZEOVERSIZED,
                    "Wert zu groß(max.'" + maxPoolsize + "'): '" + newSize + "'.");

        }
        if (newSize < mySQL_ConnectionObjects.size()) {
            for (int i = mySQL_ConnectionObjects.size(); i > newSize; i--) {
                removeConnection(i - 1);
            }
            if (mySQLPoolModel != null) {
                mySQLPoolModel.setPropertyValue(MySQLPoolModel.POOL_SIZE, mySQL_ConnectionObjects.size());
            }
        } else if (newSize > mySQL_ConnectionObjects.size()) {
            for (int i = mySQL_ConnectionObjects.size(); i < newSize; i++) {
                addConnection();
            }
            if (mySQLPoolModel != null) {
                mySQLPoolModel.setPropertyValue(MySQLPoolModel.POOL_SIZE, mySQL_ConnectionObjects.size());

            }

        }
    }

    public void setPassword(String password)  throws VSafeException {
        if (password == null || password.isEmpty()){
              throw new VSafeException(
                    "MySQL_PoolHandling.setPassword",
                    ExceptionCodes.MYSQL_POOL_POOLSIZEOVERSIZED,
                    "Passwort ungültig.", new NullPointerException());
        }
        this.password = password;
        for (MySQL_ConnectionObject mco : mySQL_ConnectionObjects) {
            mco.setPassword(password);
        }
        if (mySQLPoolModel != null) {
            mySQLPoolModel.setPropertyValue(MySQLPoolModel.POOL_PASSWORD, password);
        }
    }

    public void setState(PoolState newState) {
        if (!poolstate.equals(newState)) {
            if (mySQLPoolModel != null) {
                mySQLPoolModel.setPropertyValue(MySQLPoolModel.POOL_STATE, newState);
            }
        }
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
        if (mySQLPoolModel != null) {
            mySQLPoolModel.setPropertyValue(MySQLPoolModel.POOL_TIMEOUT, timeout);
        }

    }

    public void setUrl(String url) {
        this.url = url;
        for (MySQL_ConnectionObject mco : mySQL_ConnectionObjects) {
            mco.setUrl(url);
        }
        if (mySQLPoolModel != null) {
            mySQLPoolModel.setPropertyValue(MySQLPoolModel.POOL_URL, url);
        }
    }

    public void setUser(String user) {
        this.user = user;
        for (MySQL_ConnectionObject mco : mySQL_ConnectionObjects) {
            mco.setUser(user);
        }
        if (mySQLPoolModel != null) {
            mySQLPoolModel.setPropertyValue(MySQLPoolModel.POOL_USER, user);
        }
    }

    public void activateWatchdog() throws VSafeException {
        if (watchdog != null && !watchdog.active) {
            watchdog = null;
        }
        if (watchdog == null) {
            watchdog = new MySQL_Watchdog(this, interval);
            return;
        }
        throw new VSafeException(
                "MySQL_PoolHandling.activateWatchdog",
                ExceptionCodes.MYSQL_WATCHDOG_ALREADYACTIVATED);
    }

    public void deactivateWatchdog() throws VSafeException {
        if (watchdog != null) {
            if (watchdog.active) {
                watchdog.active = false;
                watchdog = null;

            } else {
                watchdog = null;
                throw new VSafeException(
                        "MySQL_PoolHandling.deactivateWatchdog",
                        ExceptionCodes.MYSQL_WATCHDOG_ALREADYDEACTIVATED);
            }

        } else {
            throw new VSafeException(
                    "MySQL_PoolHandling.deactivateWatchdog",
                    ExceptionCodes.MYSQL_WATCHDOG_ALREADYDEACTIVATED);
        }
    }

    public void setWatchdogActivated(boolean value) throws VSafeException {
        try {
            if (value) {
                activateWatchdog();
            } else {
                deactivateWatchdog();
            }
        } catch (VSafeException e) {
            e.addInfo("MySQL_PoolHandling.setWatchdogActivated", ExceptionCodes.GENERAL_NOTHING_TO_CHANGE);
            throw e;
        }
    }
}
