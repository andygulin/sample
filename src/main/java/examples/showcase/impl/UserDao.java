package examples.showcase.impl;

import java.io.Serializable;
import java.util.List;

import examples.showcase.User;

@ImplementBy(clazz = JdbcTemplateUserDaoImpl.class)
public interface UserDao {
	void save(User user);

	List<User> selectAll();

	User select(Serializable id);

	void update(User user);

	void delete(Serializable id);
}
