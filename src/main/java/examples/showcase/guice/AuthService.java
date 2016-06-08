package examples.showcase.guice;

import com.google.inject.ImplementedBy;

@ImplementedBy(FileAuthServiceImpl.class)
public interface AuthService {

	boolean login(String username, String password);
}
