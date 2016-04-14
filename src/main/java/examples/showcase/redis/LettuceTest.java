package examples.showcase.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lambdaworks.redis.RedisChannelHandler;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.RedisConnectionPool;
import com.lambdaworks.redis.RedisConnectionStateListener;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.codec.Utf8StringCodec;
import com.lambdaworks.redis.protocol.SetArgs;

public class LettuceTest {

	private RedisClient client;
	private RedisConnectionStateListener connectionListener;
	private RedisConnectionPool<RedisConnection<String, String>> pool;
	private RedisConnection<String, String> conn;

	@Before
	public void init() {
		RedisURI uri = new RedisURI();
		uri.setHost("192.168.1.58");
		uri.setPort(6379);
		uri.setPassword("1234");
		client = new RedisClient(uri);

		connectionListener = new ConnectionListener();
		client.addListener(connectionListener);

		pool = client.pool(new Utf8StringCodec(), 2, 10);
		conn = pool.allocateConnection();
	}

	@Test
	public void set() throws InterruptedException, ExecutionException {
		String result = conn.set("name", "aaa");
		System.out.println(result);

		result = conn.set("name2", "bbb", SetArgs.Builder.ex(5000L));
		System.out.println(result);
	}

	@Test
	public void get() throws InterruptedException, ExecutionException {
		String result = conn.get("name");
		System.out.println(result);
	}

	@Test
	public void hset() {
		boolean result = conn.hset("user", "name", "aaa");
		System.out.println(result);

		Map<String, String> map = new HashMap<>();
		map.put("age", "11");
		map.put("address", "shanghai");
		String res = conn.hmset("user", map);
		System.out.println(res);
	}

	@Test
	public void hget() {
		String result = conn.hget("user", "name");
		System.out.println(result);

		Map<String, String> map = conn.hgetall("user");
		for (Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
	}

	@After
	public void close() {
		client.shutdown();
	}

	private static class ConnectionListener implements RedisConnectionStateListener {

		@Override
		public void onRedisConnected(RedisChannelHandler<?, ?> connection) {
			System.out.println("onRedisConnected");
		}

		@Override
		public void onRedisDisconnected(RedisChannelHandler<?, ?> connection) {
			System.out.println("onRedisDisconnected");
		}

		@Override
		public void onRedisExceptionCaught(RedisChannelHandler<?, ?> connection, Throwable cause) {
			System.out.println("onRedisExceptionCaught");
		}
	}
}
