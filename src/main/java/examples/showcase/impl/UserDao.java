package examples.showcase.impl;

import examples.showcase.User;

import java.io.Serializable;
import java.util.List;

@ImplementBy(clazz = JdbcTemplateUserDaoImpl.class)
public interface UserDao {
    void save(User user);

    List<User> selectAll();

    User select(Serializable id);

    void update(User user);

    void delete(Serializable id);
}
