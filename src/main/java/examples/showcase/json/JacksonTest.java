package examples.showcase.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

public class JacksonTest extends JsonBaseTest {

    @Test
    @Override
    public void json() {
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}