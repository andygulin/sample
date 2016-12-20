package examples.showcase.json;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.List;

public class FastJsonTest extends JsonBaseTest {

    @Test
    @Override
    public void json() {
        String json = JSON.toJSONString(customs);
        logger.info(json);

        List<Custom> customs = JSON.parseArray(json, Custom.class);
        print(customs);
    }
}