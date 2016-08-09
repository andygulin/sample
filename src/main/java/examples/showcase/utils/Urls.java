package examples.showcase.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

public final class Urls {

	private static final String AND = "&";
	private static final String EQUALS = "=";

	private Urls() {
	}

	public static String build(Map<String, Object> params) {
		List<String> list = new ArrayList<>(params.size());
		for (Entry<String, Object> entry : params.entrySet()) {
			list.add(entry.getKey() + EQUALS + entry.getValue());
		}
		return StringUtils.join(list, AND);
	}
}