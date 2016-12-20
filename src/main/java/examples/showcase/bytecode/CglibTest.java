package examples.showcase.bytecode;

import net.sf.cglib.beans.BeanGenerator;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.Date;

public class CglibTest {

    private static final Logger logger = LogManager.getLogger(CglibTest.class);

    @Test
    public void beanGenerator() throws ReflectiveOperationException {
        BeanGenerator generator = new BeanGenerator();
        generator.addProperty("id", Integer.class);
        generator.addProperty("name", String.class);
        generator.addProperty("age", Integer.class);
        generator.addProperty("address", String.class);
        generator.addProperty("createdAt", Date.class);

        Object object = generator.create();
        logger.info(object);

        PropertyUtils.setProperty(object, "id", 1);
        PropertyUtils.setProperty(object, "name", "aaa");
        PropertyUtils.setProperty(object, "age", 11);
        PropertyUtils.setProperty(object, "address", "shanghai");
        PropertyUtils.setProperty(object, "createdAt", new Date());

        logger.info(PropertyUtils.getProperty(object, "id"));
        logger.info(PropertyUtils.getProperty(object, "name"));
        logger.info(PropertyUtils.getProperty(object, "age"));
        logger.info(PropertyUtils.getProperty(object, "address"));
        logger.info(PropertyUtils.getProperty(object, "createdAt"));
    }
}