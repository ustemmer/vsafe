package de.versatel.noc.vsafe.common.core.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author ulrich.stemmer
 */
public class UniqueIDGeneratorDefaultImpl implements IUniqueIDGenerator {

    private static IUniqueIDGenerator instance =
            new UniqueIDGeneratorDefaultImpl();
    private long counter = 0;

    public String getUniqueID(){
        String exceptionID = null;
        try {
            exceptionID = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ue) {
            exceptionID = "";
        }
        exceptionID = exceptionID + System.currentTimeMillis() + counter++;
        return exceptionID;
    }

    public static IUniqueIDGenerator getInstance() {
        return instance;
    }

    public long getCounter() {
        return counter;
    }
    

}
