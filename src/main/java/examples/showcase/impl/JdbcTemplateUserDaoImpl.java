package examples.showcase.impl;

import java.io.Serializable;
import java.util.List;

import examples.showcase.User;

public class JdbcTemplateUserDaoImpl implements UserDao {

	public void save(User user) {
		System.out.println(getClass().getName() + " save");
	}

	public List<User> selectAll() {
		System.out.println(getClass().getName() + " selectAll");
		return null;
	}

	public User select(Serializable id) {
		System.out.println(getClass().getName() + " select");
		return null;
	}

	public void update(User user) {
		System.out.println(getClass().getName() + " update");
	}

	public void delete(Serializable id) {
		System.out.println(getClass().getName() + " delete");
	}

}
