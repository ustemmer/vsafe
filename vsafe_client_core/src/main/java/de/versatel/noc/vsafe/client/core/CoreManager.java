package de.versatel.noc.vsafe.client.core;


import de.versatel.noc.vsafe.client.exception.ExceptionManager;
import de.versatel.noc.vsafe.client.gui.GUIManager;
import de.versatel.noc.vsafe.client.rmi.RMIManager;
import de.versatel.noc.vsafe.client.security.SecurityManager;
import de.versatel.noc.vsafe.client.services.ServiceManager;
import de.versatel.noc.vsafe.client.system.SystemManager;
/**
 *
 * @author ulrich.stemmer
 */
public class CoreManager {
    public ExceptionManager exceptionManager;
    public GUIManager gUIManager;
    public RMIManager rmiManager;
    public de.versatel.noc.vsafe.client.security.SecurityManager securityManager;
    public ServiceManager serviceManager;
    public SystemManager systemManager;

    
}
