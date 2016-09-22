package examples.showcase.generate;

import java.io.File;
import java.util.List;

import examples.showcase.generate.bean.Table;
import examples.showcase.generate.parse.GenerateJavaBean;
import examples.showcase.generate.parse.MySQLConnection;
import examples.showcase.generate.parse.Parse;

public class GenerateCode {
	public static void main(String[] args) {
		final String host = "localhost";
		final int port = 3306;
		final String user = "root";
		final String passwd = "root";
		MySQLConnection mySQLConnection = new MySQLConnection(host, port, user, passwd);
		List<Table> tables = new Parse(mySQLConnection).getParseTables();
		final File dir = new File("D:/");
		final String pkg = "com.test.bean";
		new GenerateJavaBean(tables).generate(dir, pkg);
	}
}