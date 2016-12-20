package examples.showcase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XmlTest {

    private static final Logger logger = LogManager.getLogger(XmlTest.class);
    private static final int LIST_SIZE = 10000;
    private static final File DOM4J_FILE = new File(FileUtils.getTempDirectoryPath(), "a.xml");
    private static final File JDOM_FILE = new File(FileUtils.getTempDirectoryPath(), "b.xml");
    private List<User> users;

    @Before
    public void before() {
        users = new ArrayList<>(LIST_SIZE);
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
            user.addElement("createDate").addText(DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.format(u.getCreateAt()));
        }

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        XMLWriter writer = new XMLWriter(new FileWriter(DOM4J_FILE), format);
        writer.write(doc);
        writer.flush();
        writer.close();
        logger.info(DOM4J_FILE);
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
                    .setText(DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.format(u.getCreateAt())));
            root.addContent(user);
        }
        doc.setRootElement(root);

        Format format = Format.getPrettyFormat();
        format.setEncoding("UTF-8");

        XMLOutputter output = new XMLOutputter(format);
        OutputStream os = new BufferedOutputStream(new FileOutputStream(JDOM_FILE));
        output.output(doc, os);

        IOUtils.closeQuietly(os);

        logger.info(JDOM_FILE);
    }
}
