package examples.showcase.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.collect.Maps;

public class BeanFactory {

	private static final Logger logger = Logger.getLogger(BeanFactory.class);

	private static final Map<String, UserDao> pool = Maps.newConcurrentMap();

	private BeanFactory() {
	}

	public static UserDao getBean() {
		String cacheKey = UserDao.class.getName();
		UserDao obj = null;
		if (!pool.containsKey(cacheKey)) {
			ImplementBy impl = UserDao.class.getAnnotation(ImplementBy.class);
			Class<?> clazz = impl.clazz();
			try {
				obj = (UserDao) clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			pool.put(cacheKey, obj);
			final String logTpl = "make cache key : %s , value = %s";
			logger.info(String.format(logTpl, cacheKey, obj));
		}
		final String logTpl = "load cache key : %s , value = %s";
		logger.info(String.format(logTpl, cacheKey, obj));
		return pool.get(cacheKey);
	}
}