package examples.showcase.json;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

public class JsonTest {

	private static final Logger logger = LogManager.getLogger(JsonTest.class);

	private List<Custom> customs;

	@Before
	public void before() {
		int customCount = RandomUtils.nextInt(1, 10);
		customs = Lists.newLinkedList();

		int orderCount = 0;
		List<Order> orders = null;
		for (int i = 1; i <= customCount; i++) {
			orderCount = RandomUtils.nextInt(1, 100);
			orders = Lists.newLinkedList();
			for (int j = 1; j <= orderCount; j++) {
				orders.add(new Order(j, "订单" + j));
			}
			customs.add(new Custom(i, "客户" + i, orders));
		}
	}

	private void print(List<Custom> customs) {
		for (Custom custom : customs) {
			logger.info(custom.getId());
			logger.info(custom.getName());
			for (Order order : custom.getOrders()) {
				logger.info("---- " + order.getId() + " " + order.getName());
			}
			logger.info("");
		}
	}

	@Test
	public void jsonLib() {
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

	@Test
	public void fastJson() {
		String json = JSON.toJSONString(customs);
		logger.info(json);

		List<Custom> customs = JSON.parseArray(json, Custom.class);
		print(customs);
	}

	@Test
	public void gson() {
		Gson gson = new Gson();
		String json = gson.toJson(customs);
		logger.info(json);

		List<Custom> customs = gson.fromJson(json, new TypeToken<List<Custom>>() {
		}.getType());
		print(customs);
	}

	@Test
	public void jackson() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(writer);
		jsonGenerator.writeObject(customs);
		jsonGenerator.flush();
		jsonGenerator.close();
		String json = writer.toString();
		logger.info(json);

		Custom[] arrays = objectMapper.readValue(json, Custom[].class);
		List<Custom> customs = Arrays.asList(arrays);
		print(customs);
	}

	@Test
	public void genson() {
		Genson genson = new Genson();
		String json = genson.serialize(customs);
		logger.info(json);

		List<Custom> customs = genson.deserialize(json, new GenericType<List<Custom>>() {
		});
		print(customs);
	}
}
