package de.versatel.noc.vsafe.server.exception.handlers;


import de.versatel.noc.vsafe.server.exception.ExceptionCodes;
import de.versatel.noc.vsafe.server.exception.ExceptionHandler;

/**
 *
 * @author cocoline
 */
public class LoggableExceptionHandler implements ExceptionHandler {

    protected ExceptionCodes exceptionCodes;

    public LoggableExceptionHandler() {
    }

    public void handle(Throwable t, String exceptionId) {
//        if (t instanceof NotificationException) {
            
//        } else if (t instanceof UserException) {
//        } else if (t instanceof NotificationException) {
//        } else if (t instanceof NotificationException) {
//        }
    }

    public void handle(Throwable t, String errorMessage, String exceptionId) {
    }

    public void handle(Throwable t, String errorMessage, String errorContext, int exceptionCode, String errorText, String exceptionId) {
    }

    public void raise(String errorContext, int exceptionCode, String errorText, String exceptionId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
