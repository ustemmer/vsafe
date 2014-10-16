package de.versatel.noc.vsafe.common.exception;

import de.versatel.noc.vsafe.common.exception.util.UniqueIDGeneratorDefaultImpl;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ulrich.stemmer
 */
public class VSafeException extends RuntimeException {

    public static final long serialVersionUID = -1;
    protected List<InfoItem> infoItems = new ArrayList<InfoItem>();
    protected final String exceptionID;
    protected boolean logged = false;

    protected class InfoItem {

        public String errorContext = null;
        public int exceptionInfoId = 0;
        public String errorText = null;

        public InfoItem(String contextCode, int errorCode,
                String errorText) {

            this.errorContext = contextCode;
            this.exceptionInfoId = errorCode;
            this.errorText = errorText;
        }

        public String getInfo() {
            return "[" + errorContext + ":" + exceptionInfoId + "]" + errorText;
        }

        public String getCode() {
            return "[" + errorContext + ":" + exceptionInfoId + "]";
        }
    }

    public VSafeException(String errorContext, int errorCode,
            String errorMessage) {
        addInfo(errorContext, errorCode, errorMessage);
        //this.level = ExceptionLevel.ERROR;
        this.exceptionID = UniqueIDGeneratorDefaultImpl.getInstance().getUniqueID();
    }

    public VSafeException(String errorContext, int errorCode,
            String errorMessage, Throwable cause) {
        super(cause);
        addInfo(errorContext, errorCode, errorMessage);
        //this.level = ExceptionLevel.ERROR;
        this.exceptionID = UniqueIDGeneratorDefaultImpl.getInstance().getUniqueID();
    }

    public VSafeException(/*ExceptionLevel level,*/String errorContext, int errorCode) {
        addInfo(errorContext, errorCode);
        //this.level = level;
        this.exceptionID = UniqueIDGeneratorDefaultImpl.getInstance().getUniqueID();
    }

    /*public VSafeException(/*ExceptionLevel level, String errorContext, int errorCode,
     String errorMessage) {
     addInfo(errorContext, errorCode, errorMessage);
     //this.level = level;
     this.exceptionID = UniqueIDGeneratorDefaultImpl.getInstance().getUniqueID();
     }*/

    /*public VSafeException(/*ExceptionLevel level, String errorContext, int errorCode,
     String errorMessage, Throwable cause) {
     super(cause);
     addInfo(errorContext, errorCode, errorMessage);
     //this.level = level;
     this.exceptionID = UniqueIDGeneratorDefaultImpl.getInstance().getUniqueID();
     }*/
    public final void addInfo(String errorContext, int errorCode) {
        this.infoItems.add(
                new InfoItem(errorContext, errorCode, null));
    }

    public final void addInfo(String errorContext, int errorCode, String errorText) {
        this.infoItems.add(
                new InfoItem(errorContext, errorCode, errorText));
    }

    private void appendException(StringBuilder builder, Throwable throwable) {
        if (throwable == null) {
            return;
        }
        appendException(builder, throwable.getCause());
        builder.append(throwable.toString());
        builder.append('\n');
    }
    public String getCode() {
        StringBuilder builder = new StringBuilder();
        for (int i = this.infoItems.size() - 1; i >= 0; i--) {
            InfoItem info =
                    this.infoItems.get(i);
            builder.append(info.getCode());
            if (i > 0) {
                builder.append('\n');
            }
        }

        return builder.toString();
    }

    public String getExceptionID() {
        return exceptionID;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getCode());
        builder.append('\n');
        //append additional context information.
        for (int i = this.infoItems.size() - 1; i >= 0; i--) {
            InfoItem info =
                    this.infoItems.get(i);
            builder.append(info.getInfo());
            if (i > 0) {
                builder.append('\n');
            }
        }
        //append root causes and text from this exception first.
        if (getMessage() != null) {
            builder.append('\n');
            if (getCause() == null) {
                builder.append(getMessage());
            } else if (!getMessage().equals(getCause().toString())) {
                builder.append(getMessage());
            }
        }
        appendException(builder, getCause());
        return builder.toString();
    }

}
