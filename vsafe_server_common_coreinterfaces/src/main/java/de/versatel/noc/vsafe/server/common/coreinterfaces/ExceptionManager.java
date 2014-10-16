package de.versatel.noc.vsafe.server.common.coreinterfaces;

/**
 *
 * @author cocoline
 */
public interface ExceptionManager {
    public void handle(Throwable t);
    public void handle(Throwable t, String errorMessage);
    public void handle(Throwable t, String errorMessage, String errorContext, int exceptionCode, String errorText);
    public void raise(String errorContext, int exceptionCode, String errorText);
    
}
