package examples.showcase.redis;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.After;
import org.junit.Before;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterTest {

	private JedisCluster jedis;

	@Before
	public void init() {
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setTestOnBorrow(true);
		poolConfig.setMaxIdle(10);
		poolConfig.setMinIdle(2);
		poolConfig.setMaxTotal(10);

		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("", 6379));
		nodes.add(new HostAndPort("", 6379));
		jedis = new JedisCluster(nodes, poolConfig);
	}

	@After
	public void close() throws IOException {
		jedis.close();
	}
}
