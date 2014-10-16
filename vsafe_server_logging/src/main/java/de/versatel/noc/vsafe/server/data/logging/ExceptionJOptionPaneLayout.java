package de.versatel.noc.vsafe.server.data.logging;

import org.apache.log4j.xml.XMLLayout;
import org.apache.log4j.helpers.Transform;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

import java.util.Set;
import java.util.Arrays;

import java.text.SimpleDateFormat;

public class ExceptionJOptionPaneLayout extends XMLLayout {

    private final int DEFAULT_SIZE = 256;
    private final int UPPER_LIMIT = 2048;
    private StringBuffer buf = new StringBuffer(DEFAULT_SIZE);
    private boolean locationInfo = false;
    private boolean properties = false;
    private final String separator = System.getProperty("line.separator");

    @Override
    public void setLocationInfo(boolean flag) {
        locationInfo = flag;
    }

    /**
    Returns the current value of the <b>LocationInfo</b> option.
     */
    @Override
    public boolean getLocationInfo() {
        return locationInfo;
    }

    /**
     * Sets whether MDC key-value pairs should be output, default false.
     * @param flag new value.
     * @since 1.2.15
     */
    @Override
    public void setProperties(final boolean flag) {
        properties = flag;
    }

    /**
     * Gets whether MDC key-value pairs should be output.
     * @return true if MDC key-value pairs are output.
     * @since 1.2.15
     */
    @Override
    public boolean getProperties() {
        return properties;
    }

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

        buf.append("Auslösende Klasse: '");
        buf.append(Transform.escapeTags(event.getLoggerName()));
        buf.append("'");
        buf.append(separator);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        buf.append("Zeitstempel: '");
        buf.append(simpleDateFormat.format(event.timeStamp));
        buf.append("'");
        buf.append(separator);

        buf.append("Level: '");
        buf.append(Transform.escapeTags(String.valueOf(event.getLevel())));
        buf.append("'");
        buf.append(separator);

        buf.append("Thread: '");
        buf.append(Transform.escapeTags(event.getThreadName()));
        buf.append("'");
        buf.append(separator);

        buf.append("Nachricht: '");
        Transform.appendEscapingCDATA(buf, event.getRenderedMessage());
        buf.append("'");
        buf.append(separator);

        String ndc = event.getNDC();
        if (ndc != null) {
            buf.append("NDC: '");
            Transform.appendEscapingCDATA(buf, ndc);
            buf.append("'");
            buf.append(separator);
        }

        if (event.getThrowableInformation() != null && event.getThrowableInformation().getThrowable() != null) {

            Throwable t = event.getThrowableInformation().getThrowable();

            buf.append("Ausnahme: '");
            buf.append(t.toString());
            buf.append("'");
            buf.append(separator);

            StackTraceElement[] ste = t.getStackTrace();
            if (ste.length > 0) {
                buf.append("Details: '");
                for (StackTraceElement st : ste) {
                    buf.append(st.toString());
                }
                buf.append("'");
                buf.append(separator);
            }
        }
        if (locationInfo) {
            buf.append(separator);
            LocationInfo li = event.getLocationInformation();
            buf.append("LocationInfo class: '");
            buf.append(Transform.escapeTags(li.getClassName()));
            buf.append("' method='");
            buf.append(Transform.escapeTags(li.getMethodName()));
            buf.append("' file='");
            buf.append(Transform.escapeTags(li.getFileName()));
            buf.append("' line='");
            buf.append(li.getLineNumber());
            buf.append("'");
            
        }

        if (properties) {
            Set keySet = event.getPropertyKeySet();
            if (keySet.size() > 0) {
                Object[] keys = keySet.toArray();
                Arrays.sort(keys);
                buf.append(separator);
                for (int i = 0; i < keys.length; i++) {
                    String key = keys[i].toString();
                    Object val = event.getMDC(key);
                    if (val != null) {
                        buf.append("Properties: data name='");
                        buf.append(Transform.escapeTags(key));
                        buf.append("' value='");
                        buf.append(Transform.escapeTags(String.valueOf(val)));
                        buf.append("'");
                        buf.append(separator);
                    }
                }
            }
        }

        return buf.toString();
    }

    /**
    The ExceptionJOptionPaneLayout prints and does not ignore exceptions. Hence the
    return value <code>false</code>.
     */
    @Override
    public boolean ignoresThrowable() {
        return false;
    }
}
