package examples.showcase.serializable;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Test;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import examples.showcase.User;

public class HessianTest extends BaseTest {

	@Test
	public void hessian() throws IOException {
		// 序列化
		ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
		HessianOutput output = new HessianOutput(os);
		output.writeObject(user);
		byte[] data = os.toByteArray();
		output.flush();
		output.close();

		// 反序列化
		ByteArrayInputStream is = new ByteArrayInputStream(data);
		HessianInput input = new HessianInput(is);
		User user2 = (User) input.readObject();
		input.close();
		print(user2);
	}
}
