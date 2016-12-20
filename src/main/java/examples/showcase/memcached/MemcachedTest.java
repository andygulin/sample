package examples.showcase.memcached;

import examples.showcase.User;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.GetFuture;
import net.spy.memcached.internal.OperationFuture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

@ContextConfiguration(classes = MemcachedConfiguration.class)
public class MemcachedTest extends AbstractJUnit4SpringContextTests {

    private static final Logger logger = LogManager.getLogger(MemcachedTest.class);

    @Autowired
    private MemcachedClient client;

    @Test
    public void set() throws InterruptedException, ExecutionException {
        OperationFuture<Boolean> future = client.set("name", 0, "aaa");
        logger.info(future.get());
    }

    @Test
    public void get() throws InterruptedException, ExecutionException {
        GetFuture<Object> future = client.asyncGet("name");
        logger.info(future.get());
    }

    @Test
    public void incr() throws InterruptedException, ExecutionException {
        OperationFuture<Long> future = client.asyncIncr("age", 1, 1);
        logger.info(future.get());
    }

    @Test
    public void decr() throws InterruptedException, ExecutionException {
        OperationFuture<Long> future = client.asyncDecr("age", 1, 1);
        logger.info(future.get());
    }

    @Test
    public void delete() throws InterruptedException, ExecutionException {
        OperationFuture<Boolean> future = client.delete("age");
        logger.info(future.get());
    }

    @Test
    public void obj() throws InterruptedException, ExecutionException {
        OperationFuture<Boolean> future = client.set("user", 0, new User(1, "aaa", 11, "shanghai", new Date()));
        logger.info(future.get());

        GetFuture<Object> future2 = client.asyncGet("user");
        logger.info(future2.get());
    }

    @Test
    public void stats() {
        Map<SocketAddress, Map<String, String>> stats = client.getStats();
        for (Entry<SocketAddress, Map<String, String>> stat : stats.entrySet()) {
            InetSocketAddress address = (InetSocketAddress) stat.getKey();
            logger.info(address.getHostName() + ":" + address.getPort());
            logger.info("");
            Map<String, String> params = stat.getValue();
            for (Entry<String, String> param : params.entrySet()) {
                String key = param.getKey();
                String value = param.getValue();
                logger.info(key + " -> " + value);
            }
        }
    }
}
