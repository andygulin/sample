package examples.showcase.email;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.common.collect.Lists;

public class EmailUtil {

	private static String HOST = "";
	private static String USERNAME = "";
	private static String PASSWORD = "";
	private static String CHARSET = "";

	static {
		Resource resource = new ClassPathResource("email/email.properties");
		try {
			Configuration configuration = new PropertiesConfiguration(resource.getFile());
			HOST = configuration.getString("host");
			USERNAME = configuration.getString("username");
			PASSWORD = configuration.getString("password");
			CHARSET = configuration.getString("charset");
		} catch (ConfigurationException | IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean sendMail(List<String> tos, String subject, String content, List<File> attachments) {
		boolean result = false;
		MultiPartEmail email = new MultiPartEmail();
		email.setCharset(CHARSET);
		email.setHostName(HOST);
		email.setAuthentication(USERNAME, PASSWORD);
		try {
			for (String to : tos) {
				email.addTo(to);
			}
			email.setFrom(USERNAME);
			email.setSubject(subject);
			email.setMsg(content);

			if (attachments != null && attachments.size() > 0) {
				for (File file : attachments) {
					EmailAttachment emailAttachment = new EmailAttachment();
					emailAttachment.setPath(file.getPath());
					emailAttachment.setName(file.getName());
					emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
					email.attach(emailAttachment);
				}
			}

			email.send();
			result = true;
		} catch (EmailException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean sendMail(List<String> tos, String subject, String content) {
		return sendMail(tos, subject, content, null);
	}

	public static boolean sendMail(String to, String subject, String content) {
		List<String> tos = Lists.newArrayListWithCapacity(1);
		tos.add(to);
		return sendMail(tos, subject, content);
	}

	public static boolean sendMail(String to, String subject, String content, File attachment) {
		List<String> tos = Lists.newArrayListWithCapacity(1);
		tos.add(to);
		List<File> attachments = Lists.newArrayListWithCapacity(1);
		attachments.add(attachment);
		return sendMail(tos, subject, content, attachments);
	}

	public static boolean sendMail(String to, String subject, String content, List<File> attachments) {
		List<String> tos = Lists.newArrayListWithCapacity(1);
		tos.add(to);
		return sendMail(tos, subject, content, attachments);
	}
}
