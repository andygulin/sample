package examples.showcase.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import examples.showcase.User;

public interface UserService extends Remote {

	User get(int id) throws RemoteException;

	List<User> get() throws RemoteException;
}
