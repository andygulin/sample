package examples.showcase.thrift;

import org.apache.log4j.Logger;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

import com.google.inject.Guice;
import com.google.inject.Injector;

import examples.showcase.thrift.service.HelloService;

public class Bootstrap {

	private static final Logger logger = Logger.getLogger(Bootstrap.class);

	private static final Injector injector;

	static {
		injector = Guice.createInjector(new ServiceModule());
	}

	private TServer server;
	private int port;

	public Bootstrap(int port) {
		this.port = port;
	}

	public void start() throws TTransportException {
		this.stop();
		HelloService.Iface handler = injector.getInstance(HelloService.Iface.class);

		TServerTransport transport = new TServerSocket(this.port);
		HelloService.Processor<HelloService.Iface> processor = new HelloService.Processor<HelloService.Iface>(handler);
		TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(transport).processor(processor)
				.protocolFactory(new TBinaryProtocol.Factory());
		this.server = new TThreadPoolServer(tArgs);
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				logger.info("server start success...");
				Bootstrap.this.server.serve();
			}
		});
		th.start();
	}

	public void stop() {
		if (this.server != null && this.server.isServing()) {
			this.server.stop();
		}
	}

	public static void main(String[] args) {
		try {
			new Bootstrap(9090).start();
		} catch (TTransportException e) {
			e.printStackTrace();
		}
	}
}
