/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.versatel.noc.vsafe.server.data.database.mysql;

/**
 *
 * @author ulrich.stemmer
 */
public interface TableInterface {
    public int createTables();
    public int dropTables();
}
