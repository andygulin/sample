package examples.showcase;

import java.io.File;

public class SvnFileCleaner {

	public static void cleanSVNDir(String dirName) {
		try {
			File file = new File(dirName);
			if (file.isDirectory()) {
				File[] c_file = file.listFiles();
				for (int i = 0; i < c_file.length; i++) {
					File s_file = c_file[i];
					String fileName = s_file.getName();
					if (s_file.isDirectory() && fileName.equals(".svn")) {
						cleanSVNFile(s_file.getPath());
						s_file.delete();
					} else {
						cleanSVNDir(s_file.getPath());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void cleanSVNFile(String svnPath) {
		try {
			File file = new File(svnPath);
			if (file.isDirectory()) {
				File[] c_file = file.listFiles();
				for (int i = 0; i < c_file.length; i++) {
					File s_file = c_file[i];
					if (s_file.isDirectory()) {
						cleanSVNFile(s_file.getPath());
						s_file.delete();
					} else {
						s_file.delete();
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		cleanSVNDir("D:\\BaiduYunDownload\\YichaSearch");
	}
}
