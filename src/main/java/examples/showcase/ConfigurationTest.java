package examples.showcase;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ConfigurationTest {

	private static final Logger logger = LogManager.getLogger(ConfigurationTest.class);

	@Test
	public void prop() throws ConfigurationException, IOException {
		Resource resource = new ClassPathResource("configuration/config.properties");
		PropertiesConfiguration configuration = new PropertiesConfiguration(resource.getFile());
		String name = configuration.getString("name");
		logger.info(name);
	}

	@Test
	public void ini() throws ConfigurationException, IOException {
		Resource resource = new ClassPathResource("configuration/config.ini");
		HierarchicalINIConfiguration configuration = new HierarchicalINIConfiguration(resource.getFile());
		String name = configuration.getString("name");
		logger.info(name);
	}
}
