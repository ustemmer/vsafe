package de.versatel.noc.vsafe.server.rmi.services.rmi;

import de.versatel.noc.vSafe.services.ServiceExecutor;
import de.versatel.noc.vSafe_Server.rmi.RMIServerHandling;
import java.util.List;

/**
 *
 * @author ulrich.stemmer
 */
public class RMIServiceExecutor extends ServiceExecutor {

    private RMIServerHandling rMIServerHandling;
    // Methoden
    public static final int GETSTATE = 1;

    public RMIServiceExecutor(RMIService service, RMIServerHandling rMIServerHandling) {
        super(service);
        this.rMIServerHandling = rMIServerHandling;
    }

    public Object set(int methodId, List<Object> arguments) {
        return null;
    }

    public Object get(int methodId, List<Object> arguments) {
        Object o;
        switch (methodId) {
            case GETSTATE:
                if (arguments == null || arguments.isEmpty()) {
                    return rMIServerHandling.getState();
                }
                return null;
            default:
                o = null;
        }
        return o;
    }
}
