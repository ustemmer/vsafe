package de.versatel.noc.vsafe.server.exception.handlers;

import de.versatel.noc.vsafe.server.exception.ExceptionHandler;
import java.awt.Container;
import javax.swing.JOptionPane;

/**
 *
 * @author Ulrich Stemmer
 */
public class JOptionPaneExceptionHandler  extends JOptionPane implements ExceptionHandler{

    Container contentPane;

    public JOptionPaneExceptionHandler(Container contentPane) {
        this.contentPane = contentPane;
    }

    public void handle(Throwable t, String exceptionId) {
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

    public String getThrowableInfos(Throwable t) {
        StringBuilder buf = new StringBuilder();
        buf.append("Ausnahme: '");
        buf.append(t.toString());
        buf.append("'");
        buf.append("/n");

        StackTraceElement[] ste = t.getStackTrace();
        if (ste.length > 0) {
            buf.append("Details: '");
            for (StackTraceElement st : ste) {
                buf.append(st.toString());
            }
            buf.append("'");
            buf.append("/n");
        }
        return null;
    }
}
