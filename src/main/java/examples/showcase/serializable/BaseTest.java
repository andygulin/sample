package examples.showcase.serializable;

import java.util.Date;

import org.junit.Before;

import examples.showcase.User;

public abstract class BaseTest {

	protected User user = null;

	@Before
	public void init() {
		user = new User(1, "小明", 11, "上海", new Date());
	}

	protected void print(Object obj) {
		System.out.println(obj);
	}
}
