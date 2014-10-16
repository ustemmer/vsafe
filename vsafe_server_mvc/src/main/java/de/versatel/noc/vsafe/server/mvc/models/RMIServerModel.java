package de.versatel.noc.vsafe.server.mvc.models;

import de.versatel.noc.vSafe.mvc.AbstractModel;
import de.versatel.noc.vSafe_Server.rmi.RMIServerHandling;

/**
 *
 * @author ulrich.stemmer
 */
public class RMIServerModel extends AbstractModel {

    private final RMIServerHandling rMIServer;
    public static final String RMI_START = "rmi_start";
    public static final String RMI_STOP = "rmi_stop";
    public static final String RMI_STATE = "rmi_state";
    public static final String RMI_URL = "rmi_url";
    public static final String RMI_Port = "rmi_port";

    public RMIServerModel(RMIServerHandling rMIServer) {
        this.rMIServer = rMIServer;
        addProperty(new MVCProperty(RMI_STATE, RMIServerHandling.ServerState.STOPPED));
        addProperty(new MVCProperty(RMI_URL, ""));
        addProperty(new MVCProperty(RMI_Port, ""));
    }

    @Override
    public void setPropertyValue(String propertyName, Object newValue) {
        for (MVCProperty p : mVCProperties) {
            if (p.getName().equals(RMI_START)) {
                rMIServer.start();
            } else if (p.getName().equals(RMI_STOP)) {
                rMIServer.stop();
            } else if (p.getName().equals(propertyName)) {
                p.setValue(newValue);
            }
        }
    }
}
