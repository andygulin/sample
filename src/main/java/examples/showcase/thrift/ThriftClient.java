package examples.showcase.thrift;

import java.io.IOException;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import examples.showcase.thrift.service.HelloService;

public class ThriftClient {
	public static void main(String[] args) throws IOException, TException {
		TTransport transport = new TSocket("localhost", 9090);
		transport.open();
		TProtocol protocol = new TBinaryProtocol(transport);
		HelloService.Client client = new HelloService.Client(protocol);
		client.voidMethod();
		System.out.println(client.hello());
		System.out.println(client.sayHello("aa"));
		System.out.println(client.ints());
		System.out.println(client.longs());
		System.out.println(client.isMan());
		System.out.println(client.lists());
		System.out.println(client.sets());
		System.out.println(client.maps());
		transport.close();
	}
}
