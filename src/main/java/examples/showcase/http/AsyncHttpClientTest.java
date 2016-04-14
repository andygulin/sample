package examples.showcase.http;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;

public class AsyncHttpClientTest extends AbstractHttpTest {

	private AsyncHttpClient client;

	@Before
	public void init() {
		client = new AsyncHttpClient();
	}

	@After
	public void close() {
		client.close();
	}

	@Test
	public void request() throws InterruptedException, ExecutionException, IOException {
		ListenableFuture<String> future = client.prepareGet(url).execute(new AsyncCompletionHandler<String>() {
			@Override
			public String onCompleted(Response response) throws Exception {
				return response.getResponseBody();
			}
		});
		String content = future.get();
		System.out.println(content);
	}

	@Test
	public void request2() throws InterruptedException, ExecutionException, IOException {
		Request request = new RequestBuilder().setUrl(url).build();
		ListenableFuture<Response> future = client.executeRequest(request);
		Response response = future.get();
		String content = response.getResponseBody();
		System.out.println(content);
	}
}
