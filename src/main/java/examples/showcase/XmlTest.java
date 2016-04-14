package examples.showcase;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class XmlTest {

	private List<User> users;
	private static final int LIST_SIZE = 10000;
	private static final String DOM4J_FILE = SystemUtils.IS_OS_WINDOWS ? "C:/a.xml" : "/tmp/a.xml";
	private static final String JDOM_FILE = SystemUtils.IS_OS_WINDOWS ? "C:/b.xml" : "/tmp/b.xml";

	@Before
	public void before() {
		users = Lists.newArrayListWithCapacity(LIST_SIZE);
		for (int i = 1; i <= LIST_SIZE; i++) {
			users.add(new User(i, "姓名" + random(), random(), "shanghai", new Date()));
		}
	}

	private int random() {
		return RandomUtils.nextInt(1, 100);
	}

	@Test
	public void dom4j() throws IOException {
		Document doc = DocumentFactory.getInstance().createDocument("UTF-8");

		Element root = doc.addElement("users");

		for (User u : users) {
			Element user = root.addElement("user");
			user.addAttribute("id", String.valueOf(u.getId()));
			user.addElement("name").addText(u.getName());
			user.addElement("age").addText(String.valueOf(u.getAge()));
			user.addElement("address").addText(u.getAddress());
			user.addElement("createDate").addText(DateFormatUtils.format(u.getCreateAt(), "yyyy-MM-dd HH:mm:ss"));
		}

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		XMLWriter writer = new XMLWriter(new FileWriter(DOM4J_FILE), format);
		writer.write(doc);
		writer.flush();
		writer.close();
	}

	@Test
	public void jdom() throws IOException {
		org.jdom.Document doc = new org.jdom.Document();

		org.jdom.Element root = new org.jdom.Element("users");

		for (User u : users) {
			org.jdom.Element user = new org.jdom.Element("user");
			user.setAttribute("id", String.valueOf(u.getId()));
			user.addContent(new org.jdom.Element("name").setText(u.getName()));
			user.addContent(new org.jdom.Element("age").setText(String.valueOf(u.getAge())));
			user.addContent(new org.jdom.Element("address").setText(u.getAddress()));
			user.addContent(new org.jdom.Element("createDate")
					.setText(DateFormatUtils.format(u.getCreateAt(), "yyyy-MM-dd HH:mm:ss")));
			root.addContent(user);
		}
		doc.setRootElement(root);

		Format format = Format.getPrettyFormat();
		format.setEncoding("UTF-8");

		XMLOutputter output = new XMLOutputter(format);
		OutputStream os = new BufferedOutputStream(new FileOutputStream(JDOM_FILE));
		output.output(doc, os);

		IOUtils.closeQuietly(os);
	}
}
