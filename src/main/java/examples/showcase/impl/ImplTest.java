package examples.showcase.impl;

import java.util.ServiceLoader;

import org.junit.Test;

import examples.showcase.User;

public class ImplTest {

	@Test
	public void test() {
		UserDao user = BeanFactory.getBean();
		user.save(new User());
		user.delete(1);
		user.select(1);
		user.selectAll();
		user.update(new User());
	}

	@Test
	public void spiTest() {
		ServiceLoader<UserDao> loader = ServiceLoader.load(UserDao.class);
		for (UserDao user : loader) {
			user.save(new User());
			user.delete(1);
			user.select(1);
			user.selectAll();
			user.update(new User());
		}
	}
}
