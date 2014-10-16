package de.versatel.noc.vsafe.server.exception.handlers;

import de.versatel.noc.vsafe.common.exception.VSafeException;
import de.versatel.noc.vsafe.server.exception.ExceptionCodes;
import de.versatel.noc.vsafe.server.exception.ExceptionHandler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ulrich.stemmer
 */
public class SystemExceptionHandler implements ExceptionHandler {

    protected List exceptions = new ArrayList();
    protected ExceptionCodes exceptionCodes;

    public SystemExceptionHandler() {
    }

    public void handle(Throwable t, String exceptionId) {
        this.exceptions.add(t);
    }

    public void handle(Throwable t, String message, String exceptionId) {
        this.exceptions.add(t);
    }

    public void handle(Throwable t, String message, String errorContext, int exceptionCode, String errorText, String exceptionId) {

        if (t instanceof VSafeException) {
            ((VSafeException) t).addInfo(
                    errorContext, exceptionCode, errorText);
            throw new VSafeException(
                    errorContext, exceptionCode, errorText, t);
        } else {
            ((VSafeException) t).addInfo(
                    errorContext, exceptionCode, errorText);
        }
    }

    public void raise(String errorContext, int exceptionCode,
            String errorText, String exceptionId) {
        throw new VSafeException(
                errorContext, exceptionCode, errorText);
    }

    public List getExceptions() {
        return this.exceptions;
    }

        public void setExceptionCodes(ExceptionCodes ec){
        this.exceptionCodes = ec;
    }
}
