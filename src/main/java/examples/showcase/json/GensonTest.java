package examples.showcase.json;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import org.junit.Test;

import java.util.List;

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