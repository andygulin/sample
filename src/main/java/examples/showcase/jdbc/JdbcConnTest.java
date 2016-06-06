package examples.showcase.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class JdbcConnTest {

	private static final Logger logger = LogManager.getLogger(JdbcConnTest.class);

	@Test
	public void connMySQL() throws SQLException, ClassNotFoundException {
		Class.forName("org.gjt.mm.mysql.Driver");
		String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8";
		String user = "root";
		String password = "root";
		Connection conn = DriverManager.getConnection(url, user, password);
		logger.info(conn);
		conn.close();
	}

	@Test
	public void connOracle() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@192.168.160.118:1521:lottery";
		String user = "lottery";
		String password = "lottery123";
		Connection conn = DriverManager.getConnection(url, user, password);
		logger.info(conn);
		conn.close();
	}

	@Test
	public void connSQLServer() throws ClassNotFoundException, SQLException {
		Class.forName("net.sourceforge.jtds.jdbc.Driver");
		String url = "jdbc:jtds:sqlserver://localhost:1433;DatabaseName=Volunteer";
		String user = "sa";
		String password = "123456";
		Connection conn = DriverManager.getConnection(url, user, password);
		logger.info(conn);
		conn.close();
	}

	@Test
	public void connH2() throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
		String url = "jdbc:h2:tcp://localhost/~/test";
		String user = "sa";
		String password = "";
		Connection conn = DriverManager.getConnection(url, user, password);
		Statement stmt = conn.createStatement();
		stmt.execute("create table user(id INT PRIMARY KEY,name VARCHAR(255),age INT,address VARCHAR(255))");
		PreparedStatement pstmt = conn.prepareStatement("insert into user(id,name,age,address) values(?,?,?,?)");
		for (int i = 1; i <= 100; i++) {
			pstmt.setInt(1, i);
			pstmt.setString(2, "user" + i);
			pstmt.setInt(3, i + i);
			pstmt.setString(4, "shanghai");
			pstmt.executeUpdate();
		}
		ResultSet rs = stmt.executeQuery("select * from user");
		while (rs.next()) {
			logger.info(rs.getInt("id") + " " + rs.getString("name"));
		}
		rs.close();
		stmt.close();
		pstmt.close();
		conn.close();
	}

	@Test
	public void connSQLLite() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		String db = "/tmp/user.db";
		Connection conn = DriverManager.getConnection("jdbc:sqlite:" + db);
		Statement stmt = conn.createStatement();
		stmt.execute("create table user(id,name,age,address)");
		PreparedStatement pstmt = conn.prepareStatement("insert into user(id,name,age,address) values(?,?,?,?)");
		for (int i = 1; i <= 100; i++) {
			pstmt.setInt(1, i);
			pstmt.setString(2, "user" + i);
			pstmt.setInt(3, i + i);
			pstmt.setString(4, "shanghai");
			pstmt.executeUpdate();
		}
		ResultSet rs = stmt.executeQuery("select * from user");
		while (rs.next()) {
			logger.info(rs.getInt("id") + " " + rs.getString("name"));
		}
		rs.close();
		stmt.close();
		pstmt.close();
		conn.close();
	}

	@Test
	public void connPostgreSQL() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "root";
		Connection conn = DriverManager.getConnection(url, user, password);
		logger.info(conn);
		conn.close();
	}
}
