package de.versatel.noc.vsafe.server.data;

import de.versatel.noc.vSafe.system.exceptions.VSafeException;

/**
 *
 * @author ulrich.stemmer
 */
public class DatabaseException extends VSafeException {

  

    public DatabaseException(String errorContext, int errorCode, String errorMessage) {
        super(errorContext, errorCode, errorMessage);
    }


}
