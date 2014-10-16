
package de.versatel.noc.vsafe.server.data;

import de.versatel.noc.vSafe.system.exceptions.ExceptionLevel;
import de.versatel.noc.vSafe.system.exceptions.ExceptionCodes;
import de.versatel.noc.vSafe.system.exceptions.VSafeException;

/**
 *
 * @author ulrich.stemmer
 */
public class MySQL_Watchdog implements Runnable {

    Thread thread;
    String threadname = "MySQL_Watchdog";
    public long sleepInterval;
    public boolean active;
    public MySQL_PoolHandling mySQL_ConnectionPool;

    public MySQL_Watchdog(MySQL_PoolHandling mySQL_ConnectionPool, long interval) {
        this.mySQL_ConnectionPool = mySQL_ConnectionPool;
        this.sleepInterval = interval;
        thread = new Thread(this, threadname);
        thread.start();
    }

    public void run() {
        while (active) {
            try {
                mySQL_ConnectionPool.checkConnections();
                Thread.sleep(sleepInterval);
            } catch (InterruptedException e) {
                            throw new VSafeException(
                    "MySQL_Watchdog.run",
                    ExceptionCodes.DB_NOCONNECTION,
                    "Fehler beim Datenbankzugriff!",
                    e);
            }
        }
    }

    public void setActive(boolean active){
        this.active = active;
    }
   
}
