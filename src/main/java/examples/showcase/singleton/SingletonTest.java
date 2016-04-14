package examples.showcase.singleton;

import org.junit.Test;

public class SingletonTest {

	@Test
	public void test1() {
		for (int i = 0; i < 10; i++) {
			System.out.println(SingletonClass.getInstance());
		}
	}

	@Test
	public void test2() {
		for (int i = 0; i < 10; i++) {
			System.out.println(SingletonInnerClass.getInstance());
		}
	}

	@Test
	public void test3() {
		for (int i = 0; i < 10; i++) {
			System.out.println(SingletonEnum.INSTANCE.getInstance());
		}
	}
}
