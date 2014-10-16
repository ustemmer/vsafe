package de.versatel.noc.vsafe.server.data.database.mysql;

import de.versatel.noc.vSafe.system.exceptions.ExceptionCodes;
import de.versatel.noc.vSafe.system.exceptions.VSafeException;
import de.versatel.noc.vSafe_Server.mvc.models.MySQLConnectionObjectModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ulrich.stemmer
 */
public class MySQL_ConnectionObject {

    //protected ExceptionHandler exceptionHandler = null;
    private MySQLConnectionObjectModel mySQL_ConnectionObjectModel;
    private Connection connection;
    private Statement updateStatement;
    private Statement queryStatement;
    private String url;
    private String user;
    private String password;
    private int index;
    private boolean adminBlocked;
    private boolean idle;
    private boolean connected;
    private int counter;
    private long timestamp_prepared = 0L;
    private long timestamp_connected = 0L;
    private long timestamp_disconnected = 0L;
    private long timestamp_lastCommand = 0L;

    public MySQL_ConnectionObject(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;
        prepareConnection();
    }

    public MySQL_ConnectionObject(String url, String user, String password, boolean autoConnect) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;
        prepareConnection();
        if (autoConnect) {
            connect();
        }
    }

    public final boolean connect() throws VSafeException {
        try {
            connection = DriverManager.getConnection(url, user, password);
            updateStatement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            queryStatement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            setConnected(true);
            resetCounter();
            updateTimestamp_connected();
            setTimestamp_disconnected(0L);
            setTimestamp_lastCommand(0L);
            setIdle(true);
            return true;
        } catch (SQLException ex) {
            throw new VSafeException(
                    "MySQL_ConnectionObject.connect()",
                    ExceptionCodes.RMI_NOACCESS,
                    "Fehler beim Datenbankzugriff!",
                    ex);
        } finally {
            return false;
        }
    }

    public final boolean disconnect() throws VSafeException {
        if (isIdle()) {
            setIdle(false);
            //resultset = null;
            try {
                if (queryStatement != null) {
                    queryStatement.close();
                    queryStatement = null;
                }
                if (updateStatement != null) {
                    updateStatement.close();
                    updateStatement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
                setConnected(false);
                updateTimestamp_disconnected();
                return true;
            } catch (SQLException e) {
                throw new VSafeException(
                        "MySQL_ConnectionObject.disconnect",
                        ExceptionCodes.DB_NOCONNECTION,
                        "Fehler beim Datenbankzugriff!",
                        e);
            } finally {
                return false;
            }

        }
        return false;
    }

    public ResultSet executeQuery(String sql) throws VSafeException {
        try {
            ResultSet resultset = queryStatement.executeQuery(sql);
            updateTimestamp_lastCommand();
            return resultset;
        } catch (SQLException e) {
            throw new VSafeException(
                    "MySQL_ConnectionObject.executeQuery",
                    ExceptionCodes.DB_NOCONNECTION,
                    "Fehler beim Datenbankzugriff!",
                    e);
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
        try {
            setIdle(false);
            int i = updateStatement.executeUpdate(sql);
            updateTimestamp_lastCommand();
            setIdle(true);
            return i;
        } catch (SQLException e) {
            throw new VSafeException(
                    "MySQL_ConnectionObject.executeQuery",
                    ExceptionCodes.DB_NOCONNECTION,
                    "Fehler beim Datenbankzugriff!",
                    e);

        }
    }

    public final void prepareConnection() {
        setIdle(true);
        setAdminBlocked(false);
        resetCounter();
        updateTimestamp_prepared();
    }

    public Connection getConnection() {
        return connection;
    }

    public int getCounter() {
        return counter;
    }

    public int getIndex() {
        return index;
    }

    public MySQLConnectionObjectModel getMySQL_ConnectionObjectModel() {
        return mySQL_ConnectionObjectModel;
    }

    public Statement getQueryStatement() {
        return queryStatement;
    }

    /*public ResultSet getResultset() {
    return resultset;
    }*/
    public Statement getUpdateStatement() {
        return updateStatement;
    }

    public long getTimestamp_connected() {
        return timestamp_connected;
    }

    public long getTimestamp_disconnected() {
        return timestamp_disconnected;
    }

    public long getTimestamp_lastCommand() {
        return timestamp_lastCommand;
    }

    public long getTimestamp_prepared() {
        return timestamp_prepared;
    }

    public void increaseCounter() {
        if (counter < 65535) {
            counter += 1;
        } else {
            counter = 0;
        }

        if (mySQL_ConnectionObjectModel != null) {
            mySQL_ConnectionObjectModel.setPropertyValue(MySQLConnectionObjectModel.CONNECTION_COUNTER, counter);
        }
    }

    public boolean isAdminBlocked() {
        return adminBlocked;
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean isIdle() {
        return idle;
    }

    /**
     * Prueft ob die Verbindung noch lebt.
     *
     * @param connection
     * @return
     */
    public boolean isValid() {
        try {
            connection.getMetaData();
            return true;
        } catch (Exception e) {
            connected = false;
            return false;
        }
    }

    public void setAdminBlocked(boolean value) {
        adminBlocked = value;
        if (mySQL_ConnectionObjectModel != null) {
            mySQL_ConnectionObjectModel.setPropertyValue(MySQLConnectionObjectModel.CONNECTION_ADMINSTATE, index, adminBlocked);
        }
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
        //System.out.println("Object, Ergebnis: connected=" + connected + ", model=" + mySQL_ConnectionObjectModel);
        if (mySQL_ConnectionObjectModel != null) {
            mySQL_ConnectionObjectModel.setPropertyValue(MySQLConnectionObjectModel.CONNECTION_CONNECTED, index, connected);
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setIdle(boolean value) {
        this.idle = value;
        if (mySQL_ConnectionObjectModel != null) {
            mySQL_ConnectionObjectModel.setPropertyValue(MySQLConnectionObjectModel.CONNECTION_IDLESTATE, index, idle);
        }
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void resetCounter() {
        counter = 0;
        if (mySQL_ConnectionObjectModel != null) {
            mySQL_ConnectionObjectModel.setPropertyValue(MySQLConnectionObjectModel.CONNECTION_COUNTER, index, counter);
        }
    }

    public void setMySQLPoolObjectModel(MySQLConnectionObjectModel mySQL_ConnectionObjectModel) {
        this.mySQL_ConnectionObjectModel = mySQL_ConnectionObjectModel;
    }

    public void setQueryStatement(Statement queryStatement) {
        this.queryStatement = queryStatement;
    }

    /*public void setResultset(ResultSet resultset) {
    this.resultset = resultset;
    }*/
    public void setTimestamp_connected(long l) {
        this.timestamp_connected = l;
        if (mySQL_ConnectionObjectModel != null) {
            mySQL_ConnectionObjectModel.setPropertyValue(MySQLConnectionObjectModel.CONNECTION_TIMESTAMP_CONNECTED, index, timestamp_connected);
        }
    }

    public void setTimestamp_disconnected(long l) {
        this.timestamp_disconnected = l;
        if (mySQL_ConnectionObjectModel != null) {
            mySQL_ConnectionObjectModel.setPropertyValue(MySQLConnectionObjectModel.CONNECTION_TIMESTAMP_DISCONNECTED, index, timestamp_disconnected);
        }
    }

    public void setTimestamp_lastCommand(long l) {
        this.timestamp_lastCommand = l;
        if (mySQL_ConnectionObjectModel != null) {
            mySQL_ConnectionObjectModel.setPropertyValue(MySQLConnectionObjectModel.CONNECTION_TIMESTAMP_LASTUSE, index, timestamp_lastCommand);
        }
    }

    public void setTimestamp_prepared(long l) {
        this.timestamp_prepared = l;
        if (mySQL_ConnectionObjectModel != null) {
            mySQL_ConnectionObjectModel.setPropertyValue(MySQLConnectionObjectModel.CONNECTION_TIMESTAMP_PREPARED, index, timestamp_prepared);
        }
    }

    public void setUpdateStatement(Statement updateStatement) {
        this.updateStatement = updateStatement;
    }

    public void updateTimestamp_connected() {
        this.timestamp_connected = System.currentTimeMillis();
        if (mySQL_ConnectionObjectModel != null) {
            mySQL_ConnectionObjectModel.setPropertyValue(MySQLConnectionObjectModel.CONNECTION_TIMESTAMP_CONNECTED, index, timestamp_connected);
        }
    }

    public void updateTimestamp_disconnected() {
        this.timestamp_disconnected = System.currentTimeMillis();
        if (mySQL_ConnectionObjectModel != null) {
            mySQL_ConnectionObjectModel.setPropertyValue(MySQLConnectionObjectModel.CONNECTION_TIMESTAMP_DISCONNECTED, index, timestamp_disconnected);
        }
    }

    public void updateTimestamp_lastCommand() {
        this.timestamp_lastCommand = System.currentTimeMillis();
        if (mySQL_ConnectionObjectModel != null) {
            mySQL_ConnectionObjectModel.setPropertyValue(MySQLConnectionObjectModel.CONNECTION_TIMESTAMP_LASTUSE, index, timestamp_lastCommand);
        }
    }

    public void updateTimestamp_prepared() {
        this.timestamp_prepared = System.currentTimeMillis();
        if (mySQL_ConnectionObjectModel != null) {
            mySQL_ConnectionObjectModel.setPropertyValue(MySQLConnectionObjectModel.CONNECTION_TIMESTAMP_PREPARED, index, timestamp_prepared);
        }
    }
}
