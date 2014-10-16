package de.versatel.noc.vsafe.common.services;

import de.versatel.noc.vsafe.vsafe_server_security.PermissionConnector;
import de.versatel.noc.vsafe.vsafe_server_security.PermissionDescriptions;
import java.util.List;
/**
 *
 * @author ulrich.stemmer
 */
public interface ServiceInterface {

    public String getName();

    public long getServiceId();

    public PermissionDescriptions getPermissionsDescriptions();

    public PermissionConnector getPermissionConnector();

    public ServiceExecutor getServiceExecutor();

    public Object set(int methodId, List<Object> arguments);
    public Object get(int methodId, List<Object> arguments);
    
    public int getPermissionId(int methodId);

    public void init ();
}
