package examples.showcase.cp;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private BlockingQueue<String> queue;

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String value = RandomStringUtils.random(5, "1234567890");
                queue.put(value);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}