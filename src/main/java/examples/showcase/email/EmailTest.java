package examples.showcase.email;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.io.FileUtils.listFiles;

import java.io.File;
import java.util.List;

import org.junit.Test;

public class EmailTest {

	@Test
	public void send() {
		List<String> tos = newArrayList();
		tos.add("610603860@qq.com");
		List<File> attachments = (List<File>) listFiles(new File("/home/gulin/图片"), new String[] { "jpg" }, false);
		boolean result = EmailUtil.sendMail(tos, "javax.mail", "邮件的内容", attachments);
		if (result) {
			System.out.println("OK");
		} else {
			System.out.println("FALL");
		}
	}
}
