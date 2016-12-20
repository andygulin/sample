package examples.showcase.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GuavaCache {

    private static final Logger logger = LogManager.getLogger(GuavaCache.class);

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

        logger.info(cache.get("name"));
        logger.info(cache.get("age"));
        logger.info(cache.get("age"));
    }
}