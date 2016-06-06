package examples.showcase.guice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceTest {

	private static final Logger logger = LogManager.getLogger(GuiceTest.class);

	@Test
	public void test() {
		Injector injector = Guice.createInjector();
		HelloService helloService = injector.getInstance(HelloService.class);
		logger.info(helloService.sayHello("sb"));
	}
}