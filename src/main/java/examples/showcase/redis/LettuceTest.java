package examples.showcase.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lambdaworks.redis.RedisChannelHandler;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnectionPool;
import com.lambdaworks.redis.RedisConnectionStateListener;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.SetArgs;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.lambdaworks.redis.codec.Utf8StringCodec;

public class LettuceTest {

	private static final Logger logger = LogManager.getLogger(LettuceTest.class);

	private RedisClient client;
	private RedisConnectionStateListener connectionListener;
	private RedisConnectionPool<RedisCommands<String, String>> pool;
	private RedisCommands<String, String> conn;

	@Before
	public void init() {
		RedisURI uri = new RedisURI();
		uri.setHost("192.168.1.58");
		uri.setPort(6379);
		uri.setPassword("1234");
		client = RedisClient.create(uri);

		connectionListener = new ConnectionListener();
		client.addListener(connectionListener);

		pool = client.pool(new Utf8StringCodec(), 2, 10);
		conn = pool.allocateConnection();
	}

	@Test
	public void set() throws InterruptedException, ExecutionException {
		String result = conn.set("name", "aaa");
		logger.info(result);

		result = conn.set("name2", "bbb", SetArgs.Builder.ex(5000L));
		logger.info(result);
	}

	@Test
	public void get() throws InterruptedException, ExecutionException {
		String result = conn.get("name");
		logger.info(result);
	}

	@Test
	public void hset() {
		boolean result = conn.hset("user", "name", "aaa");
		logger.info(result);

		Map<String, String> map = new HashMap<>();
		map.put("age", "11");
		map.put("address", "shanghai");
		String res = conn.hmset("user", map);
		logger.info(res);
	}

	@Test
	public void hget() {
		String result = conn.hget("user", "name");
		logger.info(result);

		Map<String, String> map = conn.hgetall("user");
		for (Entry<String, String> entry : map.entrySet()) {
			logger.info(entry.getKey() + " " + entry.getValue());
		}
	}

	@After
	public void close() {
		client.shutdown();
	}

	private static class ConnectionListener implements RedisConnectionStateListener {

		@Override
		public void onRedisConnected(RedisChannelHandler<?, ?> connection) {
			logger.info("onRedisConnected");
		}

		@Override
		public void onRedisDisconnected(RedisChannelHandler<?, ?> connection) {
			logger.info("onRedisDisconnected");
		}

		@Override
		public void onRedisExceptionCaught(RedisChannelHandler<?, ?> connection, Throwable cause) {
			logger.info("onRedisExceptionCaught");
		}
	}
}
