package examples.showcase.version;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class ClassVersionUtil {
	private static Map<String, String> versionMap = ImmutableMap.of("52.0", "1.8", "51.0", "1.7", "50.0", "1.6", "49.0",
			"1.5", "46.0", "1.4");

	public static List<String> getVersions(String... files) {
		List<String> versions = Lists.newArrayListWithCapacity(files.length);
		for (String file : files) {
			versions.add(getVersion(file, true, true));
		}
		return versions;
	}

	public static String getVersion(String file, boolean magic, boolean version) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		int length = 0;
		byte[] data = null;
		try {
			length = fis.available();
			data = new byte[length];
			fis.read(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		List<String> message = Lists.newArrayList();
		// 输出魔数
		StringBuffer sb = new StringBuffer();
		if (magic) {
			sb.append("magic: 0x");
			sb.append(Integer.toHexString(data[0]).substring(6).toUpperCase());
			sb.append(Integer.toHexString(data[1]).substring(6).toUpperCase());
			sb.append(Integer.toHexString(data[1]).substring(6).toUpperCase());
			sb.append(Integer.toHexString(data[3]).substring(6).toUpperCase());
			message.add(sb.toString());
		}
		// 主版本号和次版本号码
		int minor_version = (((int) data[4]) << 8) + data[5];
		int major_version = (((int) data[6]) << 8) + data[7];
		sb.delete(0, sb.length());
		if (version) {
			String version_str = major_version + "." + minor_version;
			sb.append("version: " + versionMap.get(version_str));
			message.add(sb.toString());
		}
		return StringUtils.join(message, "\n");
	}
}