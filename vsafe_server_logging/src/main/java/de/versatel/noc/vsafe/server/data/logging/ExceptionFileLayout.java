package de.versatel.noc.vsafe.server.data.logging;

import org.apache.log4j.Layout;
import org.apache.log4j.helpers.Transform;
import org.apache.log4j.spi.LoggingEvent;

import java.text.SimpleDateFormat;

public class ExceptionFileLayout extends Layout {

    private final int DEFAULT_SIZE = 256;
    private final int UPPER_LIMIT = 2048;
    private StringBuffer buf = new StringBuffer(DEFAULT_SIZE);
    private boolean locationInfo = false;
    private boolean properties = false;
    private final String separator = System.getProperty("line.separator");

    /** No options to activate. */
    @Override
    public void activateOptions() {
    }

    /**
     * Formats a {@link org.apache.log4j.spi.LoggingEvent} in conformance with the log4j.dtd.
     * */
    @Override
    public String format(final LoggingEvent event) {

        // Reset working buffer. If the buffer is too large, then we need a new
        // one in order to avoid the penalty of creating a large array.
        if (buf.capacity() > UPPER_LIMIT) {
            buf = new StringBuffer(DEFAULT_SIZE);
        } else {
            buf.setLength(0);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        buf.append(simpleDateFormat.format(event.timeStamp));
        buf.append(";");
        buf.append(Transform.escapeTags(event.getLoggerName()));
        buf.append(";");
        buf.append(Transform.escapeTags(String.valueOf(event.getLevel())));
        buf.append(";");
        buf.append(Transform.escapeTags(event.getThreadName()));
        buf.append(";");
        Transform.appendEscapingCDATA(buf, event.getRenderedMessage());
        String ndc = event.getNDC();
        if (ndc != null) {
            Transform.appendEscapingCDATA(buf, ndc);
            buf.append(";");
        }

        if (event.getThrowableInformation() != null && event.getThrowableInformation().getThrowable() != null) {
            Throwable t = event.getThrowableInformation().getThrowable();
            buf.append(t.toString());
            buf.append(";");
            StackTraceElement[] ste = t.getStackTrace();
            if (ste.length > 0) {
                for (StackTraceElement st : ste) {
                    buf.append(st.toString());
                }
            }
        }

        buf.append(separator);
        return buf.toString();
    }

    /**
    The JOptionPaneLayout prints and does not ignore exceptions. Hence the
    return value <code>false</code>.
     */
    @Override
    public boolean ignoresThrowable() {
        return false;
    }
}
