package de.versatel.noc.vsafe.common.rmi;

//import de.versatel.noc.vSafe_Server.plugin.PluginDataExchangeObject;
import java.util.List;

/**
 *
 * @author ulrich.stemmer
 */
public class ClientRequest {

    public static enum Destination{SYSTEM, PLUGIN};
    public static enum SystemDestination{SYSTEM, USER, POOL, NONE}

    private String sessionId;
    private Destination destination;
    
    //Systemmethoden
    private SystemDestination systemDestination;
    private int methodID;
    private List<Object> arguments;

    //Pluginmethoden
    private String pluginName;

    public ClientRequest(String sessionId, Destination destination, SystemDestination systemDestination,
            int methodID, List<Object> arguments, String pluginName ) {
        this.sessionId = sessionId;
        this.destination = destination;
        this.systemDestination = systemDestination;
        this.methodID = methodID;
        this.arguments = arguments;
        this.pluginName = pluginName;
        //this.pluginDataExchangeObject = pluginDataExchangeObject;
    }

     public List<Object> getArguments() {
        return arguments;
    }

    public void setArguments(List<Object> arguments) {
        this.arguments = arguments;
    }

    public int getMethodID() {
        return methodID;
    }

    public void setMethodID(int methodID) {
        this.methodID = methodID;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    //public PluginDataExchangeObject getPluginDataExchangeObject() {
    //    return pluginDataExchangeObject;
    //}

    //public void setPluginDataExchangeObject(PluginDataExchangeObject pluginDataExchangeObject) {
    //    this.pluginDataExchangeObject = pluginDataExchangeObject;
    //}

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public SystemDestination getSystemDestination() {
        return systemDestination;
    }

    public void setSystemDestination(SystemDestination systemDestination) {
        this.systemDestination = systemDestination;
    }

    
}
