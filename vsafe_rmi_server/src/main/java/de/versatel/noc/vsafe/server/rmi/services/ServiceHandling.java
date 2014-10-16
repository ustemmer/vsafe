package de.versatel.noc.vsafe.server.rmi.services;

import de.versatel.noc.vSafe.rmi.ClientInterface;
import de.versatel.noc.vSafe.rmi.ServerResultObject;
import de.versatel.noc.vSafe.rmi.ServerRequestObject;
import de.versatel.noc.vSafe.services.Service;
import de.versatel.noc.vSafe.services.ServiceRequestObject;
import de.versatel.noc.vSafe.system.exceptions.ExceptionCodes;
import de.versatel.noc.vSafe.system.exceptions.VSafeException;
import de.versatel.noc.vSafe_Server.system.SystemManager;
import de.versatel.noc.vsafe.server.rmi.services.system.SystemService;
import de.versatel.noc.vsafe.server.rmi.services.usermanagement.UserService;
import de.versatel.noc.vsafe.server.rmi.services.rmi.RMIService;
import de.versatel.noc.vsafe.server.rmi.services.mysql.MySQLService;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ulrich.stemmer
 */
public class ServiceHandling {

    private List<Service> services;
    private SystemManager systemManager;

    public ServiceHandling(SystemManager sm) {
        services = new ArrayList<Service>();
        this.systemManager = sm;
        services.add(new MySQLService(services, systemManager.getMySQL_PoolHandling()));
        services.add(new RMIService(services, systemManager.getRMIServerHandling()));
        services.add(new SystemService(services, systemManager));
        services.add(new UserService(services, systemManager.getUserHandling()));
    }

    public boolean addService(Service service) {
        if (service == null || service.getServiceId() < 100) {
            return false;
        }
        services.add(service);
        return true;
    }

    public ServerResultObject login(String userName, String passWord, ClientInterface clientInterface) {
        return systemManager.getUserHandling().login(userName, passWord, clientInterface);
    }

    public ServerResultObject logout(String sessionID) {
        return systemManager.getUserHandling().logout(sessionID);
    }

    public ServerResultObject get(ServerRequestObject serverReqObj) {

        ServiceRequestObject serviceReqObj = serverReqObj.getServiceRequestObject();
        if (serviceReqObj == null) {
            return new ServerResultObject(ServerResultObject.ResultType.EXCEPTION,
                    new VSafeException(
                    "ServiceHandling.get",
                    ExceptionCodes.SERVICE_WRONGREQUESTOBJECT,
                    "Übergebenes Objekt ist 'null'!",
                    new NullPointerException()));
        }

        // ServiceId gefunden, Methode erlaubt ?
        for (Service service : services) {
            if (service.getServiceId() == serverReqObj.getServiceRequestObject().getServiceId()) {
                ServerResultObject sro = systemManager.getUserHandling().checkPermission(
                        serverReqObj.getSessionId(), serverReqObj.getServiceRequestObject());
                if (sro != null) {
                    return sro;
                }
                //Ergebnis holen
                ServerResultObject sResObj = new ServerResultObject(ServerResultObject.ResultType.RESULT);
                sResObj.setResultObject(service.get(serviceReqObj.getMethodID(), serviceReqObj.getArguments()));
            }
        }

        return new ServerResultObject(ServerResultObject.ResultType.EXCEPTION,
                new VSafeException(
                "ServiceHandling.get",
                ExceptionCodes.SERVICE_WRONGSERVICE,
                "Service nicht gefunden."));
    }

    public ServerResultObject set(ServerRequestObject serverReqObj) {
        ServiceRequestObject serviceReqObj = serverReqObj.getServiceRequestObject();
        if (serviceReqObj == null) {
            return new ServerResultObject(ServerResultObject.ResultType.EXCEPTION, ExceptionCodes.SERVICE_WRONGREQUESTOBJECT);
        }

        // ServiceId gefunden, Methode erlaubt ?
        for (Service service : services) {
            if (service.getServiceId() == serverReqObj.getServiceRequestObject().getServiceId()) {
                ServerResultObject sro = systemManager.getUserHandling().checkPermission(
                        serverReqObj.getSessionId(), serverReqObj.getServiceRequestObject());
                if (sro != null) {
                    return sro;
                }
                //Ergebnis holen
                ServerResultObject sResObj = new ServerResultObject(ServerResultObject.ResultType.RESULT);
                sResObj.setResultObject(service.set(serviceReqObj.getMethodID(), serviceReqObj.getArguments()));
            }
        }
        return new ServerResultObject(ServerResultObject.ResultType.EXCEPTION, ExceptionCodes.SERVICE_WRONGSERVICE);

    }

    public List<Service> getServices() {
        return services;
    }

    public boolean removeService(long serviceID) {
        if (serviceID <= 0 || services.isEmpty()) {
            return false;
        }
        for (Service service : services) {
            if (service.getServiceId() == serviceID) {
                services.remove(service);
                return true;
            }
        }
        return false;
    }
}
