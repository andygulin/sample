package examples.showcase.guice;

import com.google.inject.Singleton;

@Singleton
public class DataBaseAuthServiceImpl implements AuthService {

	@Override
	public boolean login(String username, String password) {
		return true;
	}
}