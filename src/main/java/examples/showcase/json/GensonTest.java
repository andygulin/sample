package examples.showcase.json;

import java.util.List;

import org.junit.Test;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

public class GensonTest extends JsonBaseTest {

	@Test
	@Override
	public void json() {
		Genson genson = new Genson();
		String json = genson.serialize(customs);
		logger.info(json);

		List<Custom> customs = genson.deserialize(json, new GenericType<List<Custom>>() {
		});
		print(customs);
	}
}