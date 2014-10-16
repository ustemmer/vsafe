package de.versatel.noc.vsafe.server.remote.rmi;

import de.versatel.noc.vsafe.common.core.exception.ExceptionCodes;
import de.versatel.noc.vsafe.common.core.exception.VSafeException;
import de.versatel.noc.vsafe.server.core.CoreManager;
import de.versatel.noc.vsafe.server.mvc.models.RMIServerModel;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Die Klasse
 * <code>RMIServerHandling</code> implementiert
 *
 *
 * Die Klasse enthaelt den RMI-Server und die Methoden, um den diesen zu
 * starten, zu stoppen.
 *
 * @author ustemmer
 */
public class RMIServerHandling implements BundleActivator {

    public static enum ServerState {

        STOPPED, STARTING, STARTED, STOPPING
    };
    /**
     *
     */
    public Registry serverRegistry;
    /**
     *
     */
    public RMIServerImpl rMIServerImpl;
    private ServerState state;
    private String rmiURL;
    private int rmiPort;
    private CoreManager coreManager;

    public RMIServerHandling(CoreManager coreManager) {
        this.coreManager = coreManager;
        this.rmiURL = coreManager.getSystemProperties().getRMIServerAddress();
        this.rmiPort = coreManager.getSystemProperties().getRMIPort();
        this.state = ServerState.STOPPED;
    }

    public void start(BundleContext context) throws Exception {
        // TODO add activation code here
    }

    public void stop(BundleContext context) throws Exception {
        // TODO add deactivation code here
    }

    public int getFreePort(int fromPort, int toPort) {
        for (int port = 5001; port <= 5100; port++) {
            try {
                //Socket socket = new Socket(hostName, port);
                ServerSocket socket = new ServerSocket(port);
                socket.setReuseAddress(true);
                socket.close();
                return port;
            } catch (IOException e) {
            }
        }
        return -1;
    }

    public RMIServerImpl getrMIServer() {
        return rMIServerImpl;
    }

    public int getRmiPort() {
        return rmiPort;
    }

    public String getRmiURL() {
        return rmiURL;
    }

    public Registry getServerRegistry() {
        return serverRegistry;
    }

    public CoreManager getSystemManager() {
        return coreManager;
    }

    public final void start() throws VSafeException {
        setState(ServerState.STARTING);
        try {
            serverRegistry = LocateRegistry.createRegistry(rmiPort);
            rMIServerImpl = new RMIServerImpl(this);
            Naming.rebind(rmiURL, rMIServerImpl);
            setState(ServerState.STARTED);
        } catch (RemoteException re) {
            setState(ServerState.STOPPED);
            throw new VSafeException("RMIServerHandling.start()", ExceptionCodes.RMI_REMOTEEXCEPTION, "Verbindungsfehler.", re);
        } catch (MalformedURLException mue) {
            setState(ServerState.STOPPED);
            throw new VSafeException("RMIServerHandling.start()", ExceptionCodes.RMI_WRONGURL, "Fehlerhafte URL.", mue);
        }
    }

    protected void setState(ServerState newState) {
        this.state = newState;
        if (coreManager.getGuiHandling().getRMIServerModel() != null) {
            coreManager.getGuiHandling().getRMIServerModel().setPropertyValue(RMIServerModel.RMI_STATE, newState);
        }
    }

    public ServerState getState() {
        return this.state;
    }

    public void stop() throws VSafeException {
        try {
            setState(ServerState.STOPPING);
            Naming.unbind(rmiURL);
            String[] names = serverRegistry.list();
            for (int i = 0; i < names.length; i++) {
                serverRegistry.unbind(names[i]);
            }
            setState(ServerState.STOPPED);
        } catch (AccessException ae) {
            throw new VSafeException("RMIServerHandling.stop()", ExceptionCodes.RMI_NOACCESS, "Kein Zugriff", ae);
        } catch (MalformedURLException mue) {
            throw new VSafeException("RMIServerHandling.stop()", ExceptionCodes.RMI_WRONGURL, "Fehlerhafte URL.", mue);
        } catch (RemoteException re) {
            setState(ServerState.STARTED);
            throw new VSafeException("RMIServerHandling.stop()", ExceptionCodes.RMI_REMOTEEXCEPTION, "Verbindungsfehler.", re);
        } catch (NotBoundException nbe) {
            throw new VSafeException("RMIServerHandling.stop()", ExceptionCodes.RMI_NOTBOUND, "Nicht gebunden.", nbe);
        }


    }
}
