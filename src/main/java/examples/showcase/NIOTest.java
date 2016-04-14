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

import org.junit.Test;

public class NIOTest {

	@Test
	public void copyFile() throws IOException {
		Path source = Paths.get("/home/gulin/文档/svn");
		Path target = Paths.get("/tmp/svn");
		target = Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		System.out.println(target);
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
			System.out.println(dir + " 已存在...");
		}
	}

	@Test
	public void delete() throws IOException {
		Path dir = Paths.get("/tmp/dir");
		Files.deleteIfExists(dir);
	}

	@Test
	public void readLine() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("/tmp/svn"), Charset.forName("UTF-8"));
		for (String line : lines) {
			System.out.println(line);
		}
	}

	@Test
	public void time() throws IOException {
		Path file = Paths.get("/tmp/svn");
		System.out.println(Files.getLastModifiedTime(file));

		FileTime time = FileTime.fromMillis(new Date().getTime() - 3600);
		Files.setLastModifiedTime(file, time);
		System.out.println(Files.getLastModifiedTime(file));
	}

	@Test
	public void writeFile() throws IOException {
		Path file = Paths.get("/tmp/file");
		List<String> lines = new ArrayList<>();
		lines.add("aaaa");
		lines.add("bbbb");
		Files.write(file, lines, Charset.forName("UTF-8"));
	}

	@Test
	public void attr() throws IOException {
		Path file = Paths.get("/tmp/file");
		BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
		System.out.println(attr.fileKey());
		System.out.println(attr.isDirectory());
		System.out.println(attr.isOther());
		System.out.println(attr.isRegularFile());
		System.out.println(attr.isSymbolicLink());
		System.out.println(attr.size());
		System.out.println(attr.creationTime());
		System.out.println(attr.lastAccessTime());
		System.out.println(attr.lastModifiedTime());
	}

	@Test
	public void store() throws IOException {
		FileStore store = Files.getFileStore(Paths.get("/tmp/file"));
		System.out.println(store.getTotalSpace());
		System.out.println(store.getUnallocatedSpace());
		System.out.println(store.getUsableSpace());
		System.out.println(store.isReadOnly());
		System.out.println(store.name());
		System.out.println(store.type());
		System.out.println(store.getFileStoreAttributeView(FileStoreAttributeView.class));
	}

	private static final int BUFFER_SIZE = 512;

	@Test
	public void buffer() {
		ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
		CharBuffer charBuffer = CharBuffer.allocate(BUFFER_SIZE);
		ShortBuffer shortBuffer = ShortBuffer.allocate(BUFFER_SIZE);
		IntBuffer intBuffer = IntBuffer.allocate(BUFFER_SIZE);
		FloatBuffer floatBuffer = FloatBuffer.allocate(BUFFER_SIZE);
		DoubleBuffer doubleBuffer = DoubleBuffer.allocate(BUFFER_SIZE);
		LongBuffer longBuffer = LongBuffer.allocate(BUFFER_SIZE);
	}
}
