/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.versatel.noc.vsafe.server.mvc.controllers;

import de.versatel.noc.vSafe.mvc.AbstractController;
import de.versatel.noc.vSafe_Server.database.MySQL_ConnectionObject;
import de.versatel.noc.vSafe_Server.mvc.models.MySQLConnectionObjectModel;

/**
 *
 * @author ulrich.stemmer
 */
public class MySQLConnectionListController extends AbstractController {

    public MySQLConnectionListController() {
    }

    public void addConnectionObjectModel(MySQL_ConnectionObject mySQL_ConnectionObject){
        MySQLConnectionObjectModel mcom = new MySQLConnectionObjectModel(mySQL_ConnectionObject);
        mySQL_ConnectionObject.setMySQLPoolObjectModel(mcom);
        super.addModel(mcom);
        //System.out.println("listController: added model: " + mySQL_ConnectionObject.getIndex());
    }

    public void removeConnectionObjectModel(MySQL_ConnectionObject mySQL_ConnectionObject){
        MySQLConnectionObjectModel mcom = new MySQLConnectionObjectModel(mySQL_ConnectionObject);
        super.removeModel(mcom);
        //System.out.println("listController: removed model: " + mySQL_ConnectionObject.getIndex());
    }

    public void setAdminBlocked(int index, boolean newValue) {
//        System.out.println("2 - MySQLConnectionListController.setAdminBlocked(int index, boolean newValue)");
        super.setModelProperty(MySQLConnectionObjectModel.CONNECTION_SETADMINSTATE, index, newValue);
    }

    public void resetCounter(int index) {
        super.setModelProperty(MySQLConnectionObjectModel.CONNECTION_RESETCOUNTER, index, null);
    }

    public void connect(int index) {
        //System.out.println("Controller, Anforderung: " +MySQLConnectionObjectModel.CONNECTION_CONNECT);
        super.setModelProperty(MySQLConnectionObjectModel.CONNECTION_CONNECT, index, null);
    }

    public void disconnect(int index) {
       //System.out.println("Controller, Anforderung: " +MySQLConnectionObjectModel.CONNECTION_DISCONNECT);
        super.setModelProperty(MySQLConnectionObjectModel.CONNECTION_DISCONNECT, index, null);
    }
}
