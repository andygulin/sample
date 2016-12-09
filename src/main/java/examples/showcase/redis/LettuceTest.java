package examples.showcase.redis;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.SetArgs;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

public class LettuceTest {

	private static final Logger logger = LogManager.getLogger(LettuceTest.class);

	private RedisClient client;
	private StatefulRedisConnection<String, String> conn = null;

	@Before
	public void init() {
		RedisURI redisURI = new RedisURI();
		redisURI.setHost("192.168.209.128");
		redisURI.setPort(3306);
		client = RedisClient.create(redisURI);
		conn = client.connect();
	}

	@Test
	public void set() throws InterruptedException, ExecutionException {
		RedisCommands<String,String> command = conn.sync();

		String result = command.set("name", "aaa");
		logger.info(result);

		result = command.set("name2", "bbb", SetArgs.Builder.ex(5000L));
		logger.info(result);
	}

	@Test
	public void get() throws InterruptedException, ExecutionException {
		RedisCommands<String,String> command = conn.sync();

		String result = command.get("name");
		logger.info(result);
	}

	@Test
	public void hset() {
		RedisCommands<String,String> command = conn.sync();

		boolean result = command.hset("user", "name", "aaa");
		logger.info(result);

		Map<String, String> map = new HashMap<>();
		map.put("age", "11");
		map.put("address", "shanghai");
		String res = command.hmset("user", map);
		logger.info(res);
	}

	@Test
	public void hget() {
		RedisCommands<String,String> command = conn.sync();

		String result = command.hget("user", "name");
		logger.info(result);

		Map<String, String> map = command.hgetall("user");
		for (Entry<String, String> entry : map.entrySet()) {
			logger.info(entry.getKey() + " " + entry.getValue());
		}
	}

	@After
	public void close() {
		conn.close();
		client.shutdown();
	}
}
