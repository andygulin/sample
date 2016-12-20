package examples.showcase.callback;

import examples.showcase.callback.handler.DefaultMessageHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultApplication {
    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new JobThread(new DefaultMessageHandler()));
        service.shutdown();
    }
}
