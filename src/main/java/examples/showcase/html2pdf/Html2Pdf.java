package examples.showcase.html2pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class Html2Pdf {

    private static final Charset DEFAULT_CHATSET = Charset.forName("UTF-8");

    private InputStream html;
    private InputStream css;
    private File pdfFile;

    public Html2Pdf(InputStream html, InputStream css, File pdfFile) {
        this.html = html;
        this.css = css;
        this.pdfFile = pdfFile;
    }

    public boolean create() {
        boolean success = true;
        Document doc = new Document(PageSize.A4.rotate(), 50, 50, 50, 50);
        try {
            PdfWriter writer = PdfWriter.getInstance(doc, FileUtils.openOutputStream(pdfFile));
            doc.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, doc, html, css, DEFAULT_CHATSET, new STFontProvider());
        } catch (DocumentException | IOException e) {
            success = false;
            e.printStackTrace();
        } finally {
            doc.close();
        }
        return success;
    }

    private static class STFontProvider extends XMLWorkerFontProvider {

        @Override
        public Font getFont(String fontname, String encoding, boolean embedded, float size, int style,
                            BaseColor color) {
            BaseFont bf = null;
            try {
                bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
            Font font = new Font(bf, size, style, color);
            font.setColor(color);
            return font;
        }
    }
}
