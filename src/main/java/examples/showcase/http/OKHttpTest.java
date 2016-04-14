package examples.showcase.http;

import java.io.IOException;

import org.junit.Test;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class OKHttpTest extends AbstractHttpTest {

	@Test
	public void request() throws IOException {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(url).build();
		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			System.out.println(response.body().string());
		}
	}
}
