package de.versatel.noc.vsafe.common.rmi;

import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author ulrich.stemmer
 */
public class ClientResult {

    private int methodID;
    private List arguments;
    private int resultId;
    private boolean veto;
    private int vetoReason;

    public ClientResult(int methodID, List arguments) {
        this.methodID = methodID;
        this.arguments = arguments;
    }

    public int getMethodID() {
        return methodID;
    }

    public void setMethodID(int methodID) {
        this.methodID = methodID;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public Object getArgumentsObject() {
        return arguments;
    }

    public void setArguments(List arguments) {
        this.arguments = arguments;
    }

    public boolean isVeto() {
        return veto;
    }

    public void setVeto(boolean veto) {
        this.veto = veto;
    }

    public int getVetoReason() {
        return vetoReason;
    }

    public void setVetoReason(int vetoReason) {
        this.vetoReason = vetoReason;
    }

}
