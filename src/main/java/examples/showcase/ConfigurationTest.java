package examples.showcase;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ConfigurationTest {

	@Test
	public void prop() throws ConfigurationException, IOException {
		Resource resource = new ClassPathResource("configuration/config.properties");
		PropertiesConfiguration configuration = new PropertiesConfiguration(resource.getFile());
		String name = configuration.getString("name");
		System.out.println(name);
	}

	@Test
	public void ini() throws ConfigurationException, IOException {
		Resource resource = new ClassPathResource("configuration/config.ini");
		HierarchicalINIConfiguration configuration = new HierarchicalINIConfiguration(resource.getFile());
		String name = configuration.getString("name");
		System.out.println(name);
	}
}
