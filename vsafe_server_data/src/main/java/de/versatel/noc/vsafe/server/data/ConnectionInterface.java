/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.versatel.noc.vsafe.server.data;

import java.sql.ResultSet;

/**
 *
 * @author ulrich.stemmer
 */
public interface ConnectionInterface {

    public void close();

    public boolean close(int i);

    public boolean connect(int i);

    public int addConnections();

    public ResultSet executeQuery(int i, String s);

    public boolean executeUpdate(String s);

    public String getDriver();

    public String getUrl();

    public boolean loadDriver();

    public void removeConnection(int i);

    public void removeConnections();

    public void setAdminBlocked(int i, boolean b);

    public void setDriver(String s);

    public void setIdle(int i);

    public void setPassword(String s);

    public void setUser(String s);
}
