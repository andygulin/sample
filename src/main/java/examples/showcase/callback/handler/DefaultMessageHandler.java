package examples.showcase.callback.handler;

import org.apache.log4j.Logger;

public class DefaultMessageHandler implements MessageHandler {

	private static final Logger logger = Logger.getLogger(DefaultMessageHandler.class);

	@Override
	public void success() {
		logger.info("default success");
	}

	@Override
	public void failed() {
		logger.info("default failed");
	}

}
