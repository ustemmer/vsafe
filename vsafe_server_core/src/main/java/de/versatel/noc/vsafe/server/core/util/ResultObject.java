package de.versatel.noc.vsafe.server.core.util;

/**
 *
 * @author ulrich.stemmer
 */
public class ResultObject {

    public enum Type {

        ERROR, VOID, SET, GET, INT, BOOL
    }
    private final Type type;
    private int errorId;
    private int intResult;
    private Object resultObject;
    private boolean boolResult;

    public ResultObject(Type type) {
        this.type = type;
    }

    public ResultObject(Type type, int result) {
        this.type = type;
        if (type == Type.ERROR) {
            this.errorId = result;
        } else if (type == Type.INT) {
            this.intResult = result;
        }
    }

    public ResultObject(Type type, boolean result) {
        this.type = type;
        if (type == Type.BOOL) {
            this.boolResult = result;
        }
    }

    public ResultObject(Type type, Object result) {
        this.type = type;
        if (type == Type.GET) {
            this.resultObject = result;
        }
    }

    public boolean isBoolResult() {
        return boolResult;
    }

    public int getIntResult() {
        return intResult;
    }

    public Type getType() {
        return type;
    }

    public int getErrorId() {
        return errorId;
    }

    public void setErrorId(int errorId) {
        this.errorId = errorId;
    }

    public Object getResultObject() {
        return resultObject;
    }

    public void setResultObject(Object resultObject) {
        this.resultObject = resultObject;
    }

    public boolean isError() {
        if (type == Type.ERROR) {
            return true;
        }
        return false;
    }
}
