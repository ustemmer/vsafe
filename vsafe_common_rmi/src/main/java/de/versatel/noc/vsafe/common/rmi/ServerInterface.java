package de.versatel.noc.vsafe.common.rmi;

import java.rmi.RemoteException;

/**
 * 
 * @author ustemmer
 */
public interface ServerInterface{

        public ServerResponse login(String userName, String passWord, ClientInterface clientInterface)throws RemoteException;

        public ServerResponse logout(String sessionID)throws RemoteException;

        public ServerResponse get(ServerRequest serverRequest) throws RemoteException;

        public ServerResponse set(ServerRequest serverRequest)throws RemoteException;

        public void notify(ClientNotification notObj)throws RemoteException;

}
