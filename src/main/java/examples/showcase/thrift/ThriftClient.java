package examples.showcase.thrift;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import examples.showcase.thrift.service.HelloService;

public class ThriftClient {

	private static final Logger logger = LogManager.getLogger(ThriftClient.class);

	public static void main(String[] args) throws IOException, TException {
		TTransport transport = new TSocket("localhost", 9090);
		transport.open();
		TProtocol protocol = new TBinaryProtocol(transport);
		HelloService.Client client = new HelloService.Client(protocol);
		client.voidMethod();
		logger.info(client.hello());
		logger.info(client.sayHello("aa"));
		logger.info(client.ints());
		logger.info(client.longs());
		logger.info(client.isMan());
		logger.info(client.lists());
		logger.info(client.sets());
		logger.info(client.maps());
		transport.close();
	}
}
