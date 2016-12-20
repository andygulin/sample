package examples.showcase.json;

import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import java.util.LinkedList;
import java.util.List;

public abstract class JsonBaseTest {

    protected static final Logger logger = LogManager.getLogger(JsonBaseTest.class);

    protected List<Custom> customs;

    @Before
    public void before() {
        int customCount = RandomUtils.nextInt(1, 10);
        customs = new LinkedList<>();

        int orderCount;
        List<Order> orders;
        for (int i = 1; i <= customCount; i++) {
            orderCount = RandomUtils.nextInt(1, 100);
            orders = new LinkedList<>();
            for (int j = 1; j <= orderCount; j++) {
                orders.add(new Order(j, "订单" + j));
            }
            customs.add(new Custom(i, "客户" + i, orders));
        }
    }

    public abstract void json();

    protected void print(List<Custom> customs) {
        for (Custom custom : customs) {
            logger.info(custom.getId());
            logger.info(custom.getName());
            for (Order order : custom.getOrders()) {
                logger.info("---- " + order.getId() + " " + order.getName());
            }
            logger.info("");
        }
    }
}