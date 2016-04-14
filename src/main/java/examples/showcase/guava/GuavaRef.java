package examples.showcase.guava;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.reflect.Reflection;
import com.google.common.reflect.TypeToken;

public class GuavaRef {

	@Test
	public void test() {
		TypeToken<String> stringTok = TypeToken.of(String.class);
		System.out.println(stringTok);

		TypeToken<Integer> intTok = TypeToken.of(Integer.class);
		System.out.println(intTok);

		TypeToken<List<String>> stringListTok = new TypeToken<List<String>>() {
			private static final long serialVersionUID = 1L;
		};
		System.out.println(stringListTok);

		TypeToken<Map<?, ?>> wildMapTok = new TypeToken<Map<?, ?>>() {
			private static final long serialVersionUID = 1L;
		};
		System.out.println(wildMapTok);

		Reflection.initialize(InitializeClass.class);
	}
}

class InitializeClass {
	static {
		System.out.println("hehe");
	}
}
