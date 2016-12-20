package examples.showcase.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import java.util.List;

public class GsonTest extends JsonBaseTest {

    @Test
    @Override
    public void json() {
        Gson gson = new Gson();
        String json = gson.toJson(customs);
        logger.info(json);

        List<Custom> customs = gson.fromJson(json, new TypeToken<List<Custom>>() {
        }.getType());
        print(customs);
    }
}