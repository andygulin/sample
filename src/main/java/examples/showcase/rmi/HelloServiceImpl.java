package examples.showcase.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {

	private static final long serialVersionUID = 8750257213930571891L;

	public HelloServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public String sayHello(String name) throws RemoteException {
		return "Hello " + name;
	}

	@Override
	public String hello() throws RemoteException {
		return "Hello World!";
	}

}
