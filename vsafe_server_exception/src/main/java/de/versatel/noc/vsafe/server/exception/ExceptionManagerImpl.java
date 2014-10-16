package de.versatel.noc.vsafe.server.exception;

import de.versatel.noc.vsafe.server.common.coreinterfaces.CoreManager;
import de.versatel.noc.vsafe.server.common.coreinterfaces.ExceptionManager;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cocoline
 */
public class ExceptionManagerImpl implements ExceptionManager{

    protected final List<ExceptionHandler> exceptionHandlers = new ArrayList<ExceptionHandler>();
    protected final CoreManager coreManager;
    protected final ExceptionCodes exceptionCodes = new ExceptionCodes();
    protected long timestamp;
    protected int counter;

    public ExceptionManagerImpl(CoreManager cm) {
        coreManager = cm;
    }

    private String getExceptionID() {
        long t = System.currentTimeMillis();
        if (t - timestamp > 0) {
            timestamp = t;
            counter = 0;
        } else {
            counter += 1;
        }
        DecimalFormat fLong = new DecimalFormat("0000000000000000000");
        DecimalFormat fInt = new DecimalFormat("0000");
        return fLong.format(timestamp) + "-" + fInt.format(counter);
    }

    public void add(ExceptionHandler eh) throws NullPointerException {
        if (eh != null) {
            exceptionHandlers.add(eh);
        } else {
            throw new NullPointerException("Fehler: Neuer ExceptionHandler ist 'null'");
        }
    }

    public void remove(ExceptionHandler eh) throws NullPointerException {
        if (eh != null) {
            exceptionHandlers.remove(eh);
        } else {
            throw new NullPointerException("Fehler: Zu löschender ExceptionHandler ist 'null'");
        }
    }

    public void handle(Throwable t ) {
        String s = getExceptionID();
        if (t != null) {
            for (ExceptionHandler eh : exceptionHandlers) {
                eh.handle(t, s);
            }
        }
    }

    public void handle(Throwable t, String errorMessage) {
        String s = getExceptionID();
        for (ExceptionHandler eh : exceptionHandlers) {
            eh.handle(t, errorMessage, s);
        }
    }

    public void handle(Throwable t, String errorMessage, String errorContext, int exceptionCode, String errorText) {
        String s = getExceptionID();
        for (ExceptionHandler eh : exceptionHandlers) {
            eh.handle(t, errorMessage, errorContext, exceptionCode, errorText, s);
        }
    }

    public void raise(String errorContext, int exceptionCode, String errorText) {
        String s = getExceptionID();
        for (ExceptionHandler eh : exceptionHandlers) {
            eh.raise(errorContext, exceptionCode, errorText, s);
        }
    }
}
