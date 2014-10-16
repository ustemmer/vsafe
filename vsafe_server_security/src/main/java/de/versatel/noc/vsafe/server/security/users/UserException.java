package de.versatel.noc.vsafe.server.security.users;

import de.versatel.noc.vSafe.system.exceptions.VSafeException;

/**
 *
 * @author ulrich.stemmer
 */
public class UserException extends VSafeException {

    public UserException() {
        super();
    }

    public UserException(Throwable cause) {
        super(cause);
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserException(String errorContext, int errorCode) {
    }

    public UserException(String errorContext, int errorCode, Throwable cause) {
    }

    public UserException(String errorContext, int errorCode, String errorMessage, Throwable cause) {
    }

    public UserException(String errorContext, int errorCode, String errorMessage) {
        super(errorContext, errorCode, errorMessage);
    }


}
