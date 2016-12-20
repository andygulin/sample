package examples.showcase.json;

import com.google.common.collect.Maps;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class JsonLibTest extends JsonBaseTest {

    @Test
    @Override
    public void json() {
        JSONArray json = JSONArray.fromObject(customs);
        logger.info(json);

        Map<String, Class<?>> classMap = Maps.newHashMap();
        classMap.put("orders", Order.class);

        JsonConfig config = new JsonConfig();
        config.setClassMap(classMap);
        config.setRootClass(Custom.class);
        @SuppressWarnings("unchecked")
        List<Custom> customs = (List<Custom>) JSONArray.toCollection(json, config);
        print(customs);
    }

}