package examples.showcase.impl;

import examples.showcase.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.List;

public class HibernateUserDaoImpl implements UserDao {

    private static final Logger logger = LogManager.getLogger(HibernateUserDaoImpl.class);

    public void save(User user) {
        logger.info(getClass().getName() + " save");
    }

    public List<User> selectAll() {
        logger.info(getClass().getName() + " selectAll");
        return null;
    }

    public User select(Serializable id) {
        logger.info(getClass().getName() + " select");
        return null;
    }

    public void update(User user) {
        logger.info(getClass().getName() + " update");
    }

    public void delete(Serializable id) {
        logger.info(getClass().getName() + " delete");
    }
}