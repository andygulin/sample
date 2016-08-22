package examples.showcase.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreeMarker {

	@Test
	public void make() throws IOException, TemplateException {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		Resource resource = new ClassPathResource("examples/showcase/template/freemarker_template.ftlh");
		String filePath = resource.getFile().getPath();
		String fileDir = filePath.substring(0, filePath.lastIndexOf("\\"));
		cfg.setDirectoryForTemplateLoading(new File(fileDir));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);

		Map<String, Object> root = new HashMap<>();
		root.put("users", UserBuilder.users);
		Template temp = cfg.getTemplate(resource.getFilename());
		File outFile = new File(FileUtils.getTempDirectoryPath(), "freemarker_template.html");
		Writer out = new FileWriter(outFile);
		temp.process(root, out);
		out.flush();
		out.close();
		System.out.println(outFile);
	}
}
