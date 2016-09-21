package examples.showcase.generate;

import java.io.File;
import java.util.List;

import examples.showcase.generate.bean.Table;
import examples.showcase.generate.parse.GenerateJavaBean;
import examples.showcase.generate.parse.MySQLConnection;
import examples.showcase.generate.parse.Parse;

public class GenerateCode {
	public static void main(String[] args) {
		MySQLConnection mySQLConnection = new MySQLConnection("localhost", 3306, "root", "root");
		List<Table> tables = new Parse(mySQLConnection).getParseTables();
		new GenerateJavaBean(tables).generate(new File("D:/"), "com.test.bean");
	}
}
