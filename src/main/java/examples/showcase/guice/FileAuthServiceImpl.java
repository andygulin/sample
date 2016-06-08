package examples.showcase.guice;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.inject.Singleton;

@Singleton
public class FileAuthServiceImpl implements AuthService {

	@Override
	public boolean login(String username, String password) {
		Resource resource = new ClassPathResource("examples/showcase/guice/auth.txt");
		List<String> lines = null;
		try {
			lines = Files.readAllLines(resource.getFile().toPath(), Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String u = lines.get(0);
		String p = lines.get(1);
		if (username.equals(u) && password.equals(p)) {
			return true;
		}
		return false;
	}
}