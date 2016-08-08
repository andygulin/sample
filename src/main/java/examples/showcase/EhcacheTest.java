package examples.showcase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import examples.showcase.callback.CustomApplication;

public class EhcacheTest {

	private static final Logger logger = LogManager.getLogger(CustomApplication.class);

	private CacheManager cacheManager;

	@Before
	public void init() {

	}

	@Test
	public void cache() {
		cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				.withCache("preConfigured", CacheConfigurationBuilder
						.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(100))
						.build())
				.build(true);
		Cache<String, String> preConfigured = cacheManager.getCache("preConfigured", String.class, String.class);
		logger.info(preConfigured.get("name"));

		Cache<String, String> myCache = cacheManager.createCache("myCache", CacheConfigurationBuilder
				.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(100)).build());

		myCache.put("name", "andygulin");
		logger.info(myCache.get("name"));
	}

	@After
	public void close() {
		cacheManager.close();
	}
}