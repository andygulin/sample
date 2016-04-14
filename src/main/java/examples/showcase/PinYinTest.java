package examples.showcase;

import static net.sourceforge.pinyin4j.PinyinHelper.toHanyuPinyinStringArray;
import static net.sourceforge.pinyin4j.format.HanyuPinyinCaseType.LOWERCASE;
import static net.sourceforge.pinyin4j.format.HanyuPinyinToneType.WITHOUT_TONE;
import static net.sourceforge.pinyin4j.format.HanyuPinyinVCharType.WITH_V;

import java.util.Arrays;

import org.junit.Test;

import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinTest {

	@Test
	public void test() throws BadHanyuPinyinOutputFormatCombination {
		HanyuPinyinOutputFormat output = new HanyuPinyinOutputFormat();
		output.setCaseType(LOWERCASE);
		output.setToneType(WITHOUT_TONE);
		output.setVCharType(WITH_V);
		char ch = '你';
		String[] py = toHanyuPinyinStringArray(ch, output);
		System.out.println(Arrays.toString(py));
	}
}
