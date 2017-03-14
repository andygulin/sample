package examples.showcase.rmi;

import examples.showcase.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {

    private static final long serialVersionUID = -537034810974372676L;

    private List<User> users;

    public UserServiceImpl() throws RemoteException {
        super();
        users = new ArrayList<>(5);
        users.add(new User(1, "小明", 10, "香港", new Date()));
        users.add(new User(2, "小红", 11, "天津", new Date()));
        users.add(new User(3, "小啊", 12, "北京", new Date()));
        users.add(new User(4, "小噢", 13, "南京", new Date()));
        users.add(new User(5, "小要", 14, "上海", new Date()));
    }

    @Override
    public User get(int id) {
        User obj = null;
        for (User user : users) {
            if (user.getId().intValue() == id) {
                obj = user;
                break;
            }
        }
        return obj;
    }

    @Override
    public List<User> get() {
        return users;
    }

}
