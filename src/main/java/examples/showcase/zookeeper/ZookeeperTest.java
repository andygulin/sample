package examples.showcase.zookeeper;

import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public abstract class ZookeeperTest {

	protected static String SERVERS = "";

	static {
		Resource resource = new ClassPathResource("zookeeper.properties");
		try {
			Configuration configuration = new PropertiesConfiguration(resource.getFile());
			SERVERS = configuration.getString("servers");
		} catch (ConfigurationException | IOException e) {
			e.printStackTrace();
		}
	}
}