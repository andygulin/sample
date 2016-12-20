package examples.showcase.callback;

import examples.showcase.callback.handler.MessageHandler;

import java.util.concurrent.TimeUnit;

public class JobThread implements Runnable {

    private MessageHandler handler;

    public JobThread(MessageHandler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1L);
                if (i == 5) {
                    throw new InterruptedException();
                }
                if (this.handler != null) {
                    this.handler.success();
                }
            } catch (InterruptedException e) {
                if (this.handler != null) {
                    this.handler.failed();
                }
            } finally {

            }
        }
    }

}
