package examples.showcase.generate.parse;

import java.io.File;
import java.io.IOException;
import java.util.List;

import examples.showcase.generate.bean.Table;
import examples.showcase.generate.template.MakeTemplate;

public class GenerateJavaBean {

	private List<Table> tables;

	public GenerateJavaBean(List<Table> tables) {
		this.tables = tables;
	}

	public void generate(File dir, String pkg) {
		MakeTemplate makeTemplate = new MakeTemplate();
		for (Table table : tables) {
			try {
				File outFile = makeTemplate.make(table, dir, pkg);
				System.out.println(outFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}