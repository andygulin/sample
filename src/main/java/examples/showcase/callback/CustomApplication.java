package examples.showcase.callback;

import examples.showcase.callback.handler.MessageHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomApplication {

    private static final Logger logger = LogManager.getLogger(CustomApplication.class);

    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new JobThread(new MessageHandler() {
            @Override
            public void success() {
                logger.info("custom success");
            }

            @Override
            public void failed() {
                logger.info("custom fail");
            }
        }));
        service.shutdown();
    }
}
