package examples.showcase.html2pdf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import com.itextpdf.text.DocumentException;

public class Html2PdfTest {

	private static final Logger logger = LogManager.getLogger(Html2PdfTest.class);

	public static void main(String[] args) throws DocumentException, IOException {
		Resource htmlResouce = new UrlResource(
				"http://localhost:8080/aifei-console/medicalrecord/view?type=Pathology&id=1864");
		Resource cssResource = new UrlResource("http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css");
		InputStream inHtml = htmlResouce.getInputStream();
		InputStream inCss = cssResource.getInputStream();
		File pdfFile = FileUtils.getFile(FileUtils.getTempDirectory(), "abc.pdf");
		Html2Pdf html2Pdf = new Html2Pdf(inHtml, inCss, pdfFile);
		boolean success = html2Pdf.create();
		if (success) {
			logger.info(pdfFile);
		} else {
			logger.info("error");
		}
	}
}