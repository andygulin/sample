package examples.showcase.thrift.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Singleton;

@Singleton
public class HelloServiceHandler implements HelloService.Iface {

	private static final Logger logger = LogManager.getLogger(HelloServiceHandler.class);

	@Override
	public void voidMethod() throws TException {
		logger.info("voidMethod");
	}

	@Override
	public String hello() throws TException {
		return "hello";
	}

	@Override
	public String sayHello(String name) throws TException {
		return "hello : " + name;
	}

	@Override
	public int ints() throws TException {
		return 123;
	}

	@Override
	public long longs() throws TException {
		return 999L;
	}

	@Override
	public boolean isMan() throws TException {
		return true;
	}

	@Override
	public List<String> lists() throws TException {
		return Lists.newArrayList("a", "b", "c");
	}

	@Override
	public Set<String> sets() throws TException {
		return Sets.newHashSet("a", "b", "c");
	}

	@Override
	public Map<Integer, String> maps() throws TException {
		Map<Integer, String> maps = Maps.newHashMapWithExpectedSize(3);
		maps.put(1, "aaa");
		maps.put(2, "bbb");
		maps.put(3, "ccc");
		return maps;
	}

}
