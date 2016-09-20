package examples.showcase.rmi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RmiTest {

	private static final Logger logger = LogManager.getLogger(RmiTest.class);

	private final String HOST = "localhost";
	private final int PORT = 3000;
	private final String HELLO_ADDRESS = String.format("rmi://%s:%d/%s", HOST, PORT, HelloService.class.getName());
	private final String USER_ADDRESS = String.format("rmi://%s:%d/%s", HOST, PORT, UserService.class.getName());

	@Before
	public void bind() throws NamingException, AlreadyBoundException, IOException {
		LocateRegistry.createRegistry(PORT);
		Naming.bind(HELLO_ADDRESS, new HelloServiceImpl());
		logger.info(StringUtils.repeat("=", 80));
		logger.info(HELLO_ADDRESS + " bind success...");
		logger.info(StringUtils.repeat("=", 80));

		Naming.bind(USER_ADDRESS, new UserServiceImpl());
		logger.info(StringUtils.repeat("=", 80));
		logger.info(USER_ADDRESS + " bind success...");
		logger.info(StringUtils.repeat("=", 80));
	}

	@Test
	public void run() throws MalformedURLException, RemoteException, NotBoundException {
		HelloService hello = (HelloService) Naming.lookup(HELLO_ADDRESS);
		logger.info(hello.hello());
		logger.info(hello.sayHello("小明"));

		UserService user = (UserService) Naming.lookup(USER_ADDRESS);
		logger.info(user.get());
		logger.info(user.get(1));
	}

	@After
	public void unbind() throws RemoteException, MalformedURLException, NotBoundException {
		Naming.unbind(HELLO_ADDRESS);
		Naming.unbind(USER_ADDRESS);
	}
}