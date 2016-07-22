package examples.showcase.json;

import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

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