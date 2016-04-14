package examples.showcase.cp;

import java.util.concurrent.BlockingQueue;

import org.apache.commons.lang3.RandomStringUtils;

public class Producer implements Runnable {

	private BlockingQueue<String> queue;

	public Producer(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			for (;;) {
				String value = RandomStringUtils.random(5, "1234567890");
				queue.put(value);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
