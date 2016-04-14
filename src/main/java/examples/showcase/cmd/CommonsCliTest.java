package examples.showcase.cmd;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CommonsCliTest {
	public static void main(String[] args) throws ParseException {
		// 添加命令行参数
		// -name aaa -age 11 -address shanghai
		Options options = new Options();
		options.addOption(new Option("name", true, "显示姓名"));
		options.addOption(new Option("age", true, "显示年龄"));
		options.addOption(new Option("address", true, "显示地址"));

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);

		if (cmd.hasOption("name")) {
			String name = cmd.getOptionValue("name");
			System.out.println(name);
		}
		if (cmd.hasOption("age")) {
			String age = cmd.getOptionValue("age");
			System.out.println(age);
		}
		if (cmd.hasOption("address")) {
			String address = cmd.getOptionValue("address");
			System.out.println(address);
		}
	}
}
