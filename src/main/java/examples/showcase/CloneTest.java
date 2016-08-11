package examples.showcase;

import org.junit.Test;

public class CloneTest {

	@Test
	public void equalsObj() {
		// same object
		User user = new User();
		User user2 = user;
		System.out.println(user == user2);
	}

	@Test
	public void cloneObj() throws CloneNotSupportedException {
		// not same object
		User user = new User();
		User user2 = (User) user.clone();
		System.out.println(user == user2);
	}
}