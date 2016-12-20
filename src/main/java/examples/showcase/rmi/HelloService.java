package examples.showcase.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloService extends Remote {

    String hello() throws RemoteException;

    String sayHello(String name) throws RemoteException;
}
