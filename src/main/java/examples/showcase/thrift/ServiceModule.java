package examples.showcase.thrift;

import com.google.inject.AbstractModule;

import examples.showcase.thrift.service.HelloService;
import examples.showcase.thrift.service.HelloServiceHandler;

public class ServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(HelloService.Iface.class).to(HelloServiceHandler.class);
	}
}
