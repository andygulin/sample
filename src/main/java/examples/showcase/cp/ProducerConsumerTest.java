package examples.showcase.cp;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerConsumerTest {
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();

		BlockingQueue<String> queue = new ArrayBlockingQueue<String>(100);
		service.execute(new Producer(queue));
		service.execute(new Consumer(queue));

		service.shutdown();
	}
}
