package examples.showcase.guava;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class GuavaCache {

	@Test
	public void test() throws ExecutionException {
		LoadingCache<String, String> cache = CacheBuilder.newBuilder().maximumSize(100L)
				.expireAfterWrite(30L, TimeUnit.MINUTES).build(new CacheLoader<String, String>() {
					@Override
					public String load(String key) throws Exception {
						return "_" + key + "_";
					}
				});
		cache.put("name", "aaa");

		System.out.println(cache.get("name"));
		System.out.println(cache.get("age"));
		System.out.println(cache.get("age"));
	}
}