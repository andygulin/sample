package examples.showcase.html2pdf;

import static org.apache.commons.io.FileUtils.getFile;
import static org.apache.commons.io.FileUtils.getTempDirectory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import com.itextpdf.text.DocumentException;

public class Html2PdfTest {
	public static void main(String[] args) throws DocumentException, IOException {
		Resource htmlResouce = new UrlResource(
				"http://localhost:8080/aifei-console/medicalrecord/view?type=Pathology&id=1864");
		Resource cssResource = new UrlResource("http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css");
		InputStream inHtml = htmlResouce.getInputStream();
		InputStream inCss = cssResource.getInputStream();
		File pdfFile = getFile(getTempDirectory(), "abc.pdf");
		Html2Pdf html2Pdf = new Html2Pdf(inHtml, inCss, pdfFile);
		boolean success = html2Pdf.create();
		if (success) {
			System.out.println(pdfFile);
		}
	}
}