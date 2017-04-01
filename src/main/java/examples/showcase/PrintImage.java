package examples.showcase;

import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;
import java.io.FileInputStream;

public class PrintImage {
    public static void main(String[] args) throws Exception{
        DocFlavor dof = DocFlavor.INPUT_STREAM.JPEG;
        PrintService ps = PrintServiceLookup.lookupDefaultPrintService();
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        pras.add(OrientationRequested.PORTRAIT);

        pras.add(new Copies(1));
        pras.add(PrintQuality.HIGH);
        DocAttributeSet das = new HashDocAttributeSet();

        das.add(new MediaPrintableArea(0, 0, 210, 296, MediaPrintableArea.MM));
        FileInputStream fin = new FileInputStream("E:\\dl\\zuanshi3.jpg");

        Doc doc = new SimpleDoc(fin, dof, das);

        DocPrintJob job = ps.createPrintJob();

        job.print(doc, pras);
        fin.close();
    }
}
