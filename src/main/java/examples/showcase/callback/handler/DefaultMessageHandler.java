package examples.showcase.callback.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultMessageHandler implements MessageHandler {

	private static final Logger logger = LogManager.getLogger(DefaultMessageHandler.class);

	@Override
	public void success() {
		logger.info("default success");
	}

	@Override
	public void failed() {
		logger.info("default failed");
	}

}
