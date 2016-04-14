package examples.showcase.memcached;

import java.util.ResourceBundle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.spy.memcached.ConnectionFactoryBuilder.Locator;
import net.spy.memcached.ConnectionFactoryBuilder.Protocol;
import net.spy.memcached.DefaultHashAlgorithm;
import net.spy.memcached.FailureMode;
import net.spy.memcached.spring.MemcachedClientFactoryBean;
import net.spy.memcached.transcoders.SerializingTranscoder;

@Configuration
public class MemcachedConfiguration {

	@Bean(name = "client")
	public MemcachedClientFactoryBean factoryBean() {
		ResourceBundle bundle = ResourceBundle.getBundle("memcached");

		MemcachedClientFactoryBean bean = new MemcachedClientFactoryBean();
		bean.setServers(bundle.getString("memcached.servers"));
		bean.setProtocol(Protocol.BINARY);
		SerializingTranscoder transcoder = new SerializingTranscoder();
		transcoder.setCompressionThreshold(1024);
		bean.setTranscoder(transcoder);
		bean.setOpTimeout(1000);
		bean.setTimeoutExceptionThreshold(1998);
		bean.setHashAlg(DefaultHashAlgorithm.KETAMA_HASH);
		bean.setLocatorType(Locator.CONSISTENT);
		bean.setFailureMode(FailureMode.Redistribute);
		bean.setUseNagleAlgorithm(false);
		return bean;
	}
}
