package examples.showcase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import java.util.concurrent.TimeUnit;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JunitTest {

    private static final Logger logger = LogManager.getLogger(JunitTest.class);

    @BeforeClass
    public static void beforeClass() {
        logger.info("beforeClass");
    }

    @AfterClass
    public static void afterClass() {
        logger.info("afterClass");
    }

    @Before
    public void before() {
        logger.info("before");
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
        logger.info("after");
    }
}
