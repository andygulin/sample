package examples.showcase.redis;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class JedisShardedTest {

	private static final Logger logger = LogManager.getLogger(JedisShardedTest.class);

	private ShardedJedisPool shardedJedisPool;
	private ShardedJedis shardedJedis;

	@Before
	public void init() {
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setTestOnBorrow(true);
		poolConfig.setMaxIdle(10);
		poolConfig.setMinIdle(1);
		poolConfig.setMaxTotal(10);

		List<JedisShardInfo> shards = Lists.newArrayList();
		shards.add(new JedisShardInfo("192.168.16.128", 6379));
		shards.add(new JedisShardInfo("192.168.16.128", 6380));

		shardedJedisPool = new ShardedJedisPool(poolConfig, shards);
		shardedJedis = shardedJedisPool.getResource();
	}

	@Test
	public void set() {
		shardedJedis.set("name", "aaa");
	}

	@Test
	public void get() {
		String name = shardedJedis.get("name");
		logger.info(name);
	}

	@After
	public void close() {
		shardedJedis.close();
	}
}
