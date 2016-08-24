package examples.showcase;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.Charset;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileStoreAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class NIOTest {

	private static final Logger logger = LogManager.getLogger(NIOTest.class);

	@Test
	public void copyFile() throws IOException {
		Path source = Paths.get("/home/gulin/文档/svn");
		Path target = Paths.get("/tmp/svn");
		target = Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		logger.info(target);
	}

	@Test
	public void dir() throws IOException {
		Set<PosixFilePermission> permissions = new HashSet<>();
		permissions.add(PosixFilePermission.OWNER_READ);
		permissions.add(PosixFilePermission.OWNER_WRITE);
		permissions.add(PosixFilePermission.GROUP_READ);
		permissions.add(PosixFilePermission.GROUP_WRITE);
		permissions.add(PosixFilePermission.OTHERS_READ);
		permissions.add(PosixFilePermission.OTHERS_WRITE);
		Path dir = Paths.get("/tmp/dir");
		if (!Files.exists(dir)) {
			Files.createDirectory(Paths.get("/tmp/dir"), PosixFilePermissions.asFileAttribute(permissions));
		} else {
			logger.info(dir + " 已存在...");
		}
	}

	@Test
	public void delete() throws IOException {
		Path dir = Paths.get("/tmp/dir");
		Files.deleteIfExists(dir);
	}

	@Test
	public void readLine() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("/tmp/svn"), DEFAULT_CHARSET);
		for (String line : lines) {
			logger.info(line);
		}
	}

	@Test
	public void time() throws IOException {
		Path file = Paths.get("/tmp/svn");
		logger.info(Files.getLastModifiedTime(file));

		FileTime time = FileTime.fromMillis(new Date().getTime() - 3600);
		Files.setLastModifiedTime(file, time);
		logger.info(Files.getLastModifiedTime(file));
	}

	@Test
	public void writeFile() throws IOException {
		Path file = Paths.get("/tmp/file");
		List<String> lines = new ArrayList<>();
		lines.add("aaaa");
		lines.add("bbbb");
		Files.write(file, lines, DEFAULT_CHARSET);
	}

	@Test
	public void attr() throws IOException {
		Path file = Paths.get("/tmp/file");
		BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
		logger.info(attr.fileKey());
		logger.info(attr.isDirectory());
		logger.info(attr.isOther());
		logger.info(attr.isRegularFile());
		logger.info(attr.isSymbolicLink());
		logger.info(attr.size());
		logger.info(attr.creationTime());
		logger.info(attr.lastAccessTime());
		logger.info(attr.lastModifiedTime());
	}

	@Test
	public void store() throws IOException {
		FileStore store = Files.getFileStore(Paths.get("/tmp/file"));
		logger.info(store.getTotalSpace());
		logger.info(store.getUnallocatedSpace());
		logger.info(store.getUsableSpace());
		logger.info(store.isReadOnly());
		logger.info(store.name());
		logger.info(store.type());
		logger.info(store.getFileStoreAttributeView(FileStoreAttributeView.class));
	}

	private static final String DEFAULT_CHARSET_STR = "UTF-8";
	private static final Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_CHARSET_STR);
	private static final int BUFFER_SIZE = 512;

	@Test
	public void buffer() {
		ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
		logger.info(byteBuffer);

		CharBuffer charBuffer = CharBuffer.allocate(BUFFER_SIZE);
		logger.info(charBuffer);

		ShortBuffer shortBuffer = ShortBuffer.allocate(BUFFER_SIZE);
		logger.info(shortBuffer);

		IntBuffer intBuffer = IntBuffer.allocate(BUFFER_SIZE);
		logger.info(intBuffer);

		FloatBuffer floatBuffer = FloatBuffer.allocate(BUFFER_SIZE);
		logger.info(floatBuffer);

		DoubleBuffer doubleBuffer = DoubleBuffer.allocate(BUFFER_SIZE);
		logger.info(doubleBuffer);

		LongBuffer longBuffer = LongBuffer.allocate(BUFFER_SIZE);
		logger.info(longBuffer);
	}
}
