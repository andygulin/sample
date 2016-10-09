package examples.showcase;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRcodeTest {

	private static final Logger logger = LogManager.getLogger(QRcodeTest.class);
	private static final String CODE_DIR = FileUtils.getTempDirectoryPath();
	private static final String CODE_FILE = "website.png";
	private static final String FILE_FORMAT = FilenameUtils.getExtension(CODE_FILE);
	private static final Path file = Paths.get(CODE_DIR, CODE_FILE);
	private static final String ENCODING = "UTF-8";
	private static final int HEIGHT = 400;
	private static final int WEIGHT = 400;

	@Test
	public void encode() throws WriterException, IOException {
		String str = "http://www.baidu.com";
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, ENCODING);
		hints.put(EncodeHintType.MARGIN, 0);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, WEIGHT, HEIGHT, hints);
		MatrixToImageWriter.writeToPath(bitMatrix, FILE_FORMAT, file);
		logger.info(file);
	}

	@Test
	public void decode() throws IOException, NotFoundException {
		logger.info(file);
		BufferedImage image = ImageIO.read(file.toFile());
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Map<DecodeHintType, Object> hints = new HashMap<>();
		hints.put(DecodeHintType.CHARACTER_SET, ENCODING);
		Result result = new MultiFormatReader().decode(bitmap, hints);
		String resultText = result.getText();
		logger.info(resultText);
	}
}
