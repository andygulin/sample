package examples.showcase.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

public class UrlParamUtils {

	private static final String AND = "&";
	private static final String EQUALS = "=";

	private UrlParamUtils() {
	}

	public static String build(Map<String, Object> param) {
		List<String> list = new ArrayList<>(param.size());
		for (Entry<String, Object> entry : param.entrySet()) {
			list.add(entry.getKey() + EQUALS + entry.getValue());
		}
		String ret = StringUtils.join(list, AND);
		return ret;
	}
}