package examples.showcase.guice;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceTest {

	@Test
	public void test() {
		Injector injector = Guice.createInjector();
		HelloService helloService = injector.getInstance(HelloService.class);
		System.out.println(helloService.sayHello("sb"));
	}
}
