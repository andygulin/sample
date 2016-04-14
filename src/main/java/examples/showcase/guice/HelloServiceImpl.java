package examples.showcase.guice;

import com.google.inject.Singleton;

@Singleton
public class HelloServiceImpl implements HelloService {

	@Override
	public String sayHello(String name) {
		return "hello: " + name;
	}

}
