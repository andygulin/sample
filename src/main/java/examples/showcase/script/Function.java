package examples.showcase.script;

import examples.showcase.User;

import java.util.ArrayList;
import java.util.HashMap;

public interface Function {

    int add(int a, int b);

    ArrayList<String> list();

    HashMap<Integer, String> maps();

    ArrayList<User> users();

    void printUser(User user);
}
