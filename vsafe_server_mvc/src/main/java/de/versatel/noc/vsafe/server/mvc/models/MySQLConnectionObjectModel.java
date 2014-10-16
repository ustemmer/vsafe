package de.versatel.noc.vsafe.server.mvc.models;

import de.versatel.noc.vSafe_Server.database.MySQL_ConnectionObject;

import de.versatel.noc.vSafe.mvc.AbstractModel;

/**
 *
 * @author ulrich.stemmer
 */
public class MySQLConnectionObjectModel extends AbstractModel {

    MySQL_ConnectionObject mySQL_ConnectionObject;
    public static final String CONNECTION_ADMINSTATE = "connection_adminstate";
    public static final String CONNECTION_CONNECTED = "connection_connected";
    public static final String CONNECTION_COUNTER = "connection_counter";
    public static final String CONNECTION_IDLESTATE = "connection_idlestate";
    public static final String CONNECTION_INDEX = "connection_index";
    public static final String CONNECTION_TIMESTAMP_CONNECTED = "connection_timestamp_connected";
    public static final String CONNECTION_TIMESTAMP_DISCONNECTED = "connection_timestamp_disconnected";
    public static final String CONNECTION_TIMESTAMP_LASTUSE = "connection_timestamp_lastuse";
    public static final String CONNECTION_TIMESTAMP_PREPARED = "connection_timestamp_prepared";
    //Für void
    public static final String CONNECTION_CONNECT = "connection_connect";
    public static final String CONNECTION_DISCONNECT = "connection_disconnect";
    public static final String CONNECTION_RESETCOUNTER = "connection_resetcounter";
    public static final String CONNECTION_SETADMINSTATE = "connection_setadminstate";
    public static final String CONNECTION_ERROR = "connection_error";

    public MySQLConnectionObjectModel(MySQL_ConnectionObject mySQL_ConnectionObject) {
        super();
        if (mySQL_ConnectionObject == null) {
            return;
        }

        this.mySQL_ConnectionObject = mySQL_ConnectionObject;
        addProperty(new MVCProperty(CONNECTION_ADMINSTATE, mySQL_ConnectionObject.isAdminBlocked()));
        addProperty(new MVCProperty(CONNECTION_CONNECTED, mySQL_ConnectionObject.isConnected()));
        addProperty(new MVCProperty(CONNECTION_COUNTER, mySQL_ConnectionObject.getCounter()));
        addProperty(new MVCProperty(CONNECTION_IDLESTATE, mySQL_ConnectionObject.isIdle()));
        addProperty(new MVCProperty(CONNECTION_INDEX, mySQL_ConnectionObject.getIndex()));
        addProperty(new MVCProperty(CONNECTION_TIMESTAMP_CONNECTED, mySQL_ConnectionObject.getTimestamp_connected()));
        addProperty(new MVCProperty(CONNECTION_TIMESTAMP_DISCONNECTED, mySQL_ConnectionObject.getTimestamp_disconnected()));
        addProperty(new MVCProperty(CONNECTION_TIMESTAMP_LASTUSE, mySQL_ConnectionObject.getTimestamp_lastCommand()));
        addProperty(new MVCProperty(CONNECTION_TIMESTAMP_PREPARED, mySQL_ConnectionObject.getTimestamp_prepared()));

//        addProperty(new MVCProperty(CONNECTION_CONNECT, null));
//        addProperty(new MVCProperty(CONNECTION_DISCONNECT, null));
//        addProperty(new MVCProperty(CONNECTION_RESETCOUNTER, null));
//        addProperty(new MVCProperty(CONNECTION_SETADMINSTATE, null));
//        addProperty(new MVCProperty(CONNECTION_ERROR, null));
    }

    @Override
    public void setPropertyValue(String propertyName, int index, Object newValue) {
        if (propertyName.equals(CONNECTION_SETADMINSTATE)) {
            //System.out.println("3 - MySQLConnectionObjectModel.setPropertyValue(String propertyName, int " + index + ", Object " + newValue + ")");
            mySQL_ConnectionObject.setAdminBlocked((Boolean) newValue);
        } else if (propertyName.equals(CONNECTION_RESETCOUNTER)) {
            mySQL_ConnectionObject.resetCounter();
        } else if (propertyName.equals(CONNECTION_CONNECT)) {
            //System.out.println("Model, Anforderung: " +MySQLConnectionObjectModel.CONNECTION_CONNECT);
            mySQL_ConnectionObject.connect();
        } else if (propertyName.equals(CONNECTION_DISCONNECT)) {
            //System.out.println("Model, Anforderung: " +MySQLConnectionObjectModel.CONNECTION_DISCONNECT);
            mySQL_ConnectionObject.disconnect();
        } else {
            //System.out.println("Model, Rückgabe:" + newValue);
            super.setPropertyValue(propertyName, index, newValue);
        }
    }
}
