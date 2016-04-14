package examples.showcase.redis;

import static com.google.common.collect.Maps.newHashMap;
import static org.apache.commons.lang3.RandomStringUtils.random;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

public class JedisTest {

	private JedisPool jedisPool;
	private Jedis jedis;

	@Before
	public void init() {
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setTestOnBorrow(true);
		poolConfig.setMaxIdle(10);
		poolConfig.setMinIdle(1);
		poolConfig.setMaxTotal(10);

		jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6379);
		jedis = jedisPool.getResource();
	}

	@Test
	public void string() {
		String key = "string";
		String result = jedis.set(key, "aaa");
		System.out.println(result);

		System.out.println(jedis.get(key));
	}

	@Test
	public void list() {
		String key = "list";

		long result = jedis.lpush(key, randomInt(5));
		System.out.println(result);

		List<String> list = jedis.lrange(key, 0L, -1L);
		for (String value : list) {
			System.out.println(value);
		}
	}

	@Test
	public void hash() {
		String key = "hash";

		String field = "name";
		String value = "aaa";
		long result = jedis.hset(key, field, value);
		System.out.println(result);

		field = "age";
		value = "11";
		result = jedis.hset(key, field, value);
		System.out.println(result);

		Map<String, String> hash = jedis.hgetAll(key);
		for (Entry<String, String> entry : hash.entrySet()) {
			System.out.println(entry.getKey() + " -> " + entry.getValue());
		}
	}

	@Test
	public void zset() {
		String key = "zset";

		Map<String, Double> members = newHashMap();
		String str = "";
		for (double i = 1; i <= 100; i++) {
			str = randomInt(5);
			members.put(str, i);
		}
		long result = jedis.zadd(key, members);
		System.out.println(result);

		Set<Tuple> tuples = jedis.zrangeByScoreWithScores(key, 10d, 20d);
		for (Tuple tuple : tuples) {
			System.out.println(new String(tuple.getBinaryElement()) + " -> " + tuple.getScore());
		}
	}

	@Test
	public void set() {
		String key = "set";
		long result = jedis.sadd(key, randomInt(5));
		System.out.println(result);

		Set<String> sets = jedis.smembers(key);
		for (String value : sets) {
			System.out.println(value);
		}
	}

	@After
	public void close() {
		jedis.close();
	}

	private String randomInt(int count) {
		return random(5, "abcdefghijklmnopqrstuvwxyz");
	}
}
