package examples.showcase;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSObject;
import com.dd.plist.PropertyListFormatException;
import com.dd.plist.PropertyListParser;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;

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

    @Test
    public void plist1() throws IOException, PropertyListFormatException, ParseException, ParserConfigurationException,
            SAXException {
        Resource resource = new ClassPathResource("configuration/City.plist");
        NSArray rootArr = (NSArray) PropertyListParser.parse(resource.getFile());
        NSObject[] objs = rootArr.getArray();
        for (NSObject obj : objs) {
            logger.info(obj);
        }
    }

    @Test
    public void plist2() throws IOException, PropertyListFormatException, ParseException, ParserConfigurationException,
            SAXException {
        Resource resource = new ClassPathResource("configuration/Info.plist");
        NSDictionary rootDic = (NSDictionary) PropertyListParser.parse(resource.getFile());
        logger.info(rootDic.objectForKey("CFBundleDevelopmentRegion"));
        logger.info(rootDic.objectForKey("CFBundleDisplayName"));
        logger.info(rootDic.objectForKey("CFBundleExecutable"));
        logger.info(rootDic.objectForKey("CFBundleIdentifier"));
        logger.info(rootDic.objectForKey("CFBundleInfoDictionaryVersion"));
        logger.info(rootDic.objectForKey("CFBundleName"));
        logger.info(rootDic.objectForKey("CFBundlePackageType"));
        logger.info(rootDic.objectForKey("CFBundleShortVersionString"));
        logger.info(rootDic.objectForKey("CFBundleSignature"));

        NSArray arr = (NSArray) rootDic.objectForKey("CFBundleURLTypes");
        NSDictionary typeDir = (NSDictionary) arr.getArray()[0];
        logger.info(typeDir.objectForKey("CFBundleTypeRole"));
        logger.info(typeDir.objectForKey("CFBundleURLName"));
        arr = (NSArray) typeDir.objectForKey("CFBundleURLSchemes");
        logger.info(arr.getArray()[0]);

        logger.info(rootDic.objectForKey("CFBundleVersion"));
        logger.info(rootDic.objectForKey("LSRequiresIPhoneOS"));

        arr = (NSArray) rootDic.objectForKey("UIRequiredDeviceCapabilities");
        logger.info(arr.getArray()[0]);

        arr = (NSArray) rootDic.objectForKey("UISupportedInterfaceOrientations");
        logger.info(arr.getArray()[0]);

        logger.info(rootDic.objectForKey("UIViewEdgeAntialiasing"));
    }
}