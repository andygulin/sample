package examples.showcase.impl;

import examples.showcase.User;
import org.junit.Test;

import java.util.ServiceLoader;

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