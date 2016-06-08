package examples.showcase.guice;

import com.google.inject.Singleton;

@Singleton
public class SimpleAuthServiceImpl implements AuthService {

	@Override
	public boolean login(String username, String password) {
		if (username.equals("root") && password.equals("root")) {
			return true;
		}
		return false;
	}
}