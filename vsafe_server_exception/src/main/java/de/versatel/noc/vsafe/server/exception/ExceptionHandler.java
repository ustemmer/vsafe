package de.versatel.noc.vsafe.server.exception;

/**
 *
 * @author cocoline
 */
public interface ExceptionHandler {
    public void handle(Throwable t, String exceptionId);
    public void handle(Throwable t, String errorMessage, String exceptionId);
    public void handle(Throwable t, String errorMessage, String errorContext, int exceptionCode, String errorText, String exceptionId);
    public void raise(String errorContext, int exceptionCode, String errorText, String exceptionId);
}
