package de.versatel.noc.vsafe.server.mvc.controllers;

import de.versatel.noc.vSafe.mvc.AbstractController;
//import de.versatel.noc.vSafe.system.SystemSettings.MySQLPoolRequestMode;
import de.versatel.noc.vSafe_Server.database.MySQL_PoolHandling.PoolRequestMode;
import de.versatel.noc.vSafe_Server.database.MySQL_ConnectionObject;
import de.versatel.noc.vSafe_Server.mvc.models.MySQLPoolModel;



/**
 *
 * @author ulrich.stemmer
 */
public class MySQLPoolController extends AbstractController {

    public MySQLConnectionListController mySQLConnectionListController;

    public MySQLPoolController() {
        super();
    }

    public void addConnectionModel(MySQL_ConnectionObject mySQL_ConnectionObject) {
        if (mySQLConnectionListController != null){
            //System.out.println("poolController: add model: " + mySQL_ConnectionObject.getIndex());
            mySQLConnectionListController.addConnectionObjectModel (mySQL_ConnectionObject);
        }
        
    }

    public void addConnection() {
        super.setModelProperty(MySQLPoolModel.POOL_ADDCONNECTION, null);
    }

    public void checkConnections() {
        super.setModelProperty(MySQLPoolModel.POOL_CHECKCONNECTIONS, null);
    }

    public void connect() {
        super.setModelProperty(MySQLPoolModel.POOL_CONNECT, null);
    }

    public void disconnect() {
        super.setModelProperty(MySQLPoolModel.POOL_DISCONNECT, null);
    }

    public MySQLConnectionListController getMySQLConnectionListController() {
        return mySQLConnectionListController;
    }

    /*@Override
    public void propertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals(MySQLPoolModel.POOL_CONNECTIONADDED)) {
    if (mySQLConnectionListController != null) {
    MySQL_ConnectionObject mco = (MySQL_ConnectionObject) evt.getNewValue();
    MySQLConnectionObjectModel mcom = new MySQLConnectionObjectModel(mco);
    mco.setMySQLPoolObjectModel(mcom);
    mySQLConnectionListController.addModel(mcom);
    }
    }

    for (AbstractViewPanel view : registeredViews) {
    System.out.println("MySQLPoolController.propertyChange() - evt.getPropertyName()=" + evt.getPropertyName());
    view.modelPropertyChange(evt);
    }
    }*/
    public void removeConnection() {
        super.setModelProperty(MySQLPoolModel.POOL_REMOVECONNECTION, null);
    }

    public void removeConnectionModel(MySQL_ConnectionObject mySQL_ConnectionObject) {
        if (mySQLConnectionListController != null){
            mySQLConnectionListController.removeConnectionObjectModel(mySQL_ConnectionObject);
        }
        //System.out.println("poolController: removed model: " + mySQL_ConnectionObject.getIndex());
    }

    public void setMySQLConnectionListController(MySQLConnectionListController mySQLConnectionListController) {
        this.mySQLConnectionListController = mySQLConnectionListController;
    }

    public void setLogging(boolean newState) {
        setModelProperty(MySQLPoolModel.POOL_SETLOGGING, newState);
    }

    public void setUser(String userName) {
        setModelProperty(MySQLPoolModel.POOL_SETUSER, userName);
    }

    public void setPassword(String password) {
        setModelProperty(MySQLPoolModel.POOL_SETPASSWORD, password);
    }
    public void setPoolSize(int size) {
        //System.out.println("poolcontroller, newsize=" + size);
        setModelProperty(MySQLPoolModel.POOL_SETSIZE, size);
    }
    public void setMaxPoolSize(int maxSize) {
        setModelProperty(MySQLPoolModel.POOL_SETMAXSIZE, maxSize);
    }
    public void setRequestMode(PoolRequestMode mode) {
        setModelProperty(MySQLPoolModel.POOL_SETREQUESTMODE, mode);
    }
    public void setWatchdogActive(boolean state) {
        setModelProperty(MySQLPoolModel.POOL_SETWDACTIVE, state);
    }
    public void setWatchdogInterval(long interval) {
        setModelProperty(MySQLPoolModel.POOL_SETUSER, interval);
    }

}
