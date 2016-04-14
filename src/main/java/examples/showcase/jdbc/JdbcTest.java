package examples.showcase.jdbc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import examples.showcase.User;

public class JdbcTest {

	private Connection con;
	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet rs;

	@Before
	public void conn() throws ClassNotFoundException, SQLException {
		Class.forName("org.gjt.mm.mysql.Driver");
		String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8";
		String user = "root";
		String password = "root";
		con = DriverManager.getConnection(url, user, password);
	}

	@Test
	public void insert() throws SQLException {
		String sql = "INSERT INTO `user` VALUES(NULL,?,?,?,?)";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, "aaa");
		pstmt.setInt(2, 11);
		pstmt.setString(3, "shanghai");
		pstmt.setObject(4, new Date());
		int result = pstmt.executeUpdate();
		Assert.assertEquals(result, 1);
	}

	@Test
	public void insertGen() throws SQLException {
		String sql = "INSERT INTO `user` VALUES(NULL,?,?,?,?)";
		pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, "aaa");
		pstmt.setInt(2, 11);
		pstmt.setString(3, "shanghai");
		pstmt.setObject(4, new Date());
		int result = pstmt.executeUpdate();
		Assert.assertEquals(result, 1);

		rs = pstmt.getGeneratedKeys();
		rs.next();
		int genKey = rs.getInt(1);
		System.out.println(genKey);
	}

	@Test
	public void query() throws SQLException {
		String sql = "select * from `user` order by id desc";
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			print(rs);
		}
	}

	@Test
	public void queryMap() throws SQLException {
		String sql = "select * from `user` order by id desc";
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount();
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> row = null;
		while (rs.next()) {
			row = new HashMap<>(cols);
			for (int i = 1; i <= cols; i++) {
				String colName = rsmd.getColumnName(i);
				row.put(colName, rs.getObject(colName));
			}
			list.add(row);
		}
		print(list);
	}

	@Test
	public void queryBean() throws SQLException, IllegalAccessException, InvocationTargetException {
		String sql = "select * from `user` order by id desc";
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount();
		List<User> users = new ArrayList<>();
		User user = null;
		while (rs.next()) {
			user = new User();
			for (int i = 1; i <= cols; i++) {
				String colName = rsmd.getColumnName(i);
				BeanUtils.setProperty(user, colName, rs.getObject(colName));
			}
			users.add(user);
		}
		print(users);
	}

	@Test
	public void insertBlob() throws SQLException, FileNotFoundException {
		String sql = "INSERT INTO `file` VALUES(NULL,?,?,?)";
		pstmt = con.prepareStatement(sql);
		File[] files = getFiles();
		BufferedInputStream in = null;
		for (File file : files) {
			pstmt.setString(1, file.getName());
			in = new BufferedInputStream(new FileInputStream(file));
			pstmt.setBlob(2, in);
			pstmt.setObject(3, new Date());
			pstmt.executeUpdate();
			System.out.println(file);
		}
	}

	@Test
	public void readBlob() throws SQLException, IOException {
		String sql = "select * from `file`";
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		while (rs.next()) {
			System.out.println(rs.getInt("id"));
			System.out.println(rs.getString("filename"));
			in = new BufferedInputStream(rs.getBlob("data").getBinaryStream());
			out = new BufferedOutputStream(new FileOutputStream(new File("/tmp", rs.getString("filename"))));
			IOUtils.copy(in, out);
			System.out.println(rs.getTimestamp("createAt"));
			System.out.println(StringUtils.repeat("=", 80));
		}
	}

	private File[] getFiles() {
		File file = new File("/home/gulin/文档");
		return file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile();
			}
		});
	}

	@Test
	public void rollback() {
		try {
			con.setAutoCommit(false);
			String sql = "INSERT INTO `user` VALUES(NULL,?,?,?,?)";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, "bbb");
			pstmt.setInt(2, 12);
			pstmt.setString(3, "shanghai");
			pstmt.setObject(4, new Date());
			pstmt.executeUpdate();

			pstmt.setString(1, "ccc");
			pstmt.setString(2, "ccc");// 故意写错
			pstmt.setString(3, "shanghai");
			pstmt.setObject(4, new Date());
			pstmt.executeUpdate();

			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void print(List<?> objs) {
		for (Object obj : objs) {
			if (obj instanceof Map) {
				Map<String, Object> row = (Map<String, Object>) obj;
				System.out.println("id : " + row.get("id"));
				System.out.println("name : " + row.get("name"));
				System.out.println("age : " + row.get("age"));
				System.out.println("address : " + row.get("address"));
				System.out.println("createAt : " + row.get("createAt"));
				System.out.println(StringUtils.repeat("=", 80));
			}
			if (obj instanceof User) {
				User user = (User) obj;
				System.out.println("id : " + user.getId());
				System.out.println("name : " + user.getName());
				System.out.println("age : " + user.getAge());
				System.out.println("address : " + user.getAddress());
				System.out.println("createAt : " + user.getCreateAt());
				System.out.println(StringUtils.repeat("=", 80));
			}
		}
	}

	private void print(ResultSet rs) throws SQLException {
		System.out.println("id : " + rs.getInt("id"));
		System.out.println("name : " + rs.getString("name"));
		System.out.println("age : " + rs.getInt("age"));
		System.out.println("address : " + rs.getString("address"));
		System.out.println("createAt : " + rs.getTimestamp("createAt"));
		System.out.println(StringUtils.repeat("=", 80));
	}

	@After
	public void close() throws SQLException {
		if (pstmt != null) {
			pstmt.close();
		}
		if (stmt != null) {
			stmt.close();
		}
		if (rs != null) {
			rs.close();
		}
		if (con != null) {
			con.close();
		}
	}
}
