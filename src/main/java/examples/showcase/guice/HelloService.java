package examples.showcase.guice;

import com.google.inject.ImplementedBy;

@ImplementedBy(HelloServiceImpl.class)
public interface HelloService {

	String sayHello(String name);
}
