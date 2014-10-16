package de.versatel.noc.vsafe.server.remote.rmi;

import de.versatel.noc.vSafe_Server.users.UserException;
import de.versatel.noc.vsafe.common.rmi.ClientInterface;
import de.versatel.noc.vsafe.common.rmi.ClientNotification;
import de.versatel.noc.vsafe.common.rmi.ServerInterface;
import de.versatel.noc.vsafe.common.rmi.ServerRequest;
import de.versatel.noc.vsafe.common.rmi.ServerResponse;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RMIServerImpl extends UnicastRemoteObject implements ServerInterface {

    static final long serialVersionUID = 1L;
    private final RMIServerHandling rMIServerHandling;
    private List<ClientInterface> clientInterfaces = new ArrayList<ClientInterface>();

    public RMIServerImpl(RMIServerHandling rMIServerHandling) throws RemoteException {
        super();
        this.rMIServerHandling = rMIServerHandling;
    }

    public ServerResponse get(ServerRequest sReqObj) throws RemoteException {
        if (sReqObj == null) {
            throw new RemoteException("ServerRequest=null");
        }
        try {
            ServerResponse sro = rMIServerHandling.getSystemManager().getUserHandling().checkSession(sReqObj);
            if (sro != null) {
                return sro;
            }
        } catch (UserException ue) {
            rMIServerHandling.getSystemManager().handle(ue);
        }
        return rMIServerHandling.getSystemManager().getServiceHandling().get(sReqObj);
    }

    public ServerResponse set(ServerRequest sReqObj) throws RemoteException {
        if (sReqObj == null) {
            throw new RemoteException("ServerRequest=null");
        }
        try {
            ServerResponse sro = rMIServerHandling.getSystemManager().getUserHandling().checkSession(sReqObj);
            if (sro != null) {
                return sro;
            }
        } catch (UserException ue) {
            rMIServerHandling.getSystemManager().handle(ue);
        }
        return rMIServerHandling.getSystemManager().getServiceHandling().set(sReqObj);
    }

    public ServerResponse login(String userName, String passWord, ClientInterface clientInterface) throws RemoteException {
        return rMIServerHandling.getSystemManager().getServiceHandling().login(userName, passWord, clientInterface);
    }

    public ServerResponse logout(String sessionID) throws RemoteException {
        return rMIServerHandling.getSystemManager().getServiceHandling().logout(sessionID);
    }

    public void addClientInterface(ClientInterface clientInterface) throws RemoteException {
        clientInterfaces.add(clientInterface);
    }

    public void removeClientInterface(ClientInterface clientInterface) throws RemoteException {
        clientInterfaces.remove(clientInterface);
    }

    public List<ClientInterface> getClientInterfaces(ClientInterface clientInterface) throws RemoteException {
        return clientInterfaces;
    }

    public void notify(ClientNotification notObj) throws RemoteException {
        for (ClientInterface ci : clientInterfaces) {
            ci.notify(notObj);
        }
    }
}
