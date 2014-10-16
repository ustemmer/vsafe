package de.versatel.noc.vsafe.common.rmi;

import de.versatel.noc.vsafe.common.core.exception.VSafeException;

/**
 *
 * @author ulrich.stemmer
 */
public class ServerResponse {

    public enum ResponseType{RESULT, EXCEPTION};
    private final ResponseType type;
    private Object resultObject;
    private VSafeException vSafeException;

    public ServerResponse(ResponseType type) {
        this.type = type;
    }

    public ServerResponse(ResponseType type, VSafeException e) {
        this.type = type;
        this.vSafeException = e;
    }

    public ServerResponse(ResponseType type, Object resultObject) {
        this.type = type;
        this.resultObject = resultObject;
    }


    public VSafeException getVSafeException() {
        return vSafeException;
    }

    public void setVSafeException(VSafeException e) {
        this.vSafeException = e;
    }

    public Object getResultObject() {
        return resultObject;
    }

    public void setResultObject(Object resultObject) {
        this.resultObject = resultObject;
    }

    public boolean isException() {
        if (type == ResponseType.EXCEPTION){
            return true;
        }
        return false;
    }
}
