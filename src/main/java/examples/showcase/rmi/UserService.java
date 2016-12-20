package examples.showcase.rmi;

import examples.showcase.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface UserService extends Remote {

    User get(int id) throws RemoteException;

    List<User> get() throws RemoteException;
}
