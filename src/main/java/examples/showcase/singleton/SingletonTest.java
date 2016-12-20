package examples.showcase.singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class SingletonTest {

    private static final Logger logger = LogManager.getLogger(SingletonTest.class);

    @Test
    public void test1() {
        for (int i = 0; i < 10; i++) {
            logger.info(SingletonClass.getInstance());
        }
    }

    @Test
    public void test2() {
        for (int i = 0; i < 10; i++) {
            logger.info(SingletonInnerClass.getInstance());
        }
    }

    @Test
    public void test3() {
        for (int i = 0; i < 10; i++) {
            logger.info(SingletonEnum.INSTANCE.getInstance());
        }
    }
}
