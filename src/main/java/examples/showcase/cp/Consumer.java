package examples.showcase.cp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    private BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        List<String> values = new ArrayList<>();
        String value;
        try {
            while (true) {
                value = queue.take();
                values.add(value);
                if (values.size() == 100) {
                    System.out.println(values);
                    System.out.println(values.size());
                    values.clear();
                    System.out.println();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
