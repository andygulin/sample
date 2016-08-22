package examples.showcase.template;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import examples.showcase.User;

public class UserBuilder {

	private UserBuilder() {
	}

	public static final List<User> users = new ArrayList<>();

	static {
		users.add(new User(1, "小明", 11, "上海", new Date()));
		users.add(new User(2, "小红", 12, "北京", new Date()));
		users.add(new User(3, "小男", 13, "南京", new Date()));
		users.add(new User(4, "小女", 14, "香港", new Date()));
		users.add(new User(5, "小花", 15, "台湾", new Date()));
	}
}
