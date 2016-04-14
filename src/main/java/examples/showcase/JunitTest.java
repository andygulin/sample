package examples.showcase;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JunitTest {

	@Before
	public void before() {
		System.out.println("before");
	}

	@BeforeClass
	public static void beforeClass() {
		System.out.println("beforeClass");
	}

	@Test
	public void test() {
		Assert.assertNull(null);
		Assert.assertNotNull("a");
		Assert.assertEquals("abc", "abc");
	}

	@Test(expected = NullPointerException.class)
	public void exception() {
		throw new NullPointerException();
	}

	@Test(timeout = 2000L)
	public void timeout() {
		try {
			TimeUnit.SECONDS.sleep(1L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@After
	public void after() {
		System.out.println("after");
	}

	@AfterClass
	public static void afterClass() {
		System.out.println("afterClass");
	}
}
