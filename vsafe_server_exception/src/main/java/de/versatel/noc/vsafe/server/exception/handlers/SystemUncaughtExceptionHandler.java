package de.versatel.noc.vsafe.server.exception.handlers;

import de.versatel.noc.vsafe.server.exception.ExceptionHandler;
import de.versatel.noc.vsafe.server.exception.ExceptionManagerImpl;
import org.apache.log4j.Logger;
/**
 *
 * @author ulrich.stemmer
 */
public class SystemUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler, ExceptionHandler {

    protected final ExceptionManagerImpl exceptionManagerImpl;
    protected Logger exceptionLogger = Logger.getLogger("uncaughtexceptionlogfile");
    //protected DebuggingModel debuggingModel;

    public SystemUncaughtExceptionHandler(ExceptionManagerImpl emi) {
        this.exceptionManagerImpl = emi;
    }

    public void uncaughtException(Thread thread, Throwable t) {
        LogThread logThread = new LogThread(thread,  t);
        logThread.init();
    }

    public void handle(Throwable t, String exceptionId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void handle(Throwable t, String errorMessage, String exceptionId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void handle(Throwable t, String errorMessage, String errorContext, int exceptionCode, String errorText, String exceptionId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void raise(String errorContext, int exceptionCode, String errorText, String exceptionId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /*public void setDebuggingModel(DebuggingModel dm) {
        this.debuggingModel = dm;
    }*/

    private class LogThread implements Runnable {

        Thread logThread;
        Thread sourceThread;
        String threadname = "ErrorLogThread";
        Throwable throwable;

        public LogThread(Thread thread, Throwable t) {
            sourceThread = thread;
            throwable = t;
            logThread = new Thread(this, threadname);
        }

        public void run() {
            if (exceptionLogger != null) {
                exceptionLogger.debug("DEBUG", throwable);
            }
            /*if (debuggingModel != null) {
                debuggingModel.firePropertyChange(DebuggingModel.THROWABLE_THROWN,  null,throwable);
            }*/
        }

        private void init() {
            logThread.start();
        }
    }
}
