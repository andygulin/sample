package examples.showcase.guava;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.google.common.reflect.Reflection;
import com.google.common.reflect.TypeToken;

public class GuavaRef {

	private static final Logger logger = LogManager.getLogger(GuavaRef.class);

	@Test
	public void test() {
		TypeToken<String> stringTok = TypeToken.of(String.class);
		logger.info(stringTok);

		TypeToken<Integer> intTok = TypeToken.of(Integer.class);
		logger.info(intTok);

		TypeToken<List<String>> stringListTok = new TypeToken<List<String>>() {
			private static final long serialVersionUID = 1L;
		};
		logger.info(stringListTok);

		TypeToken<Map<?, ?>> wildMapTok = new TypeToken<Map<?, ?>>() {
			private static final long serialVersionUID = 1L;
		};
		logger.info(wildMapTok);

		Reflection.initialize(InitializeClass.class);
	}
}

class InitializeClass {

	private static final Logger logger = LogManager.getLogger(InitializeClass.class);

	static {
		logger.info("hehe");
	}
}
