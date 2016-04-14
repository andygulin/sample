package examples.showcase.db;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mchange.v2.c3p0.ComboPooledDataSource;

class ConnectionFactory {
	private ConnectionFactory() {

	}

	private static ComboPooledDataSource ds = null;
	private static ResourceBundle bundle = ResourceBundle.getBundle("jdbc");

	static {
		String driverClass = bundle.getString("jdbc.driverClass");
		String jdbcUrl = bundle.getString("jdbc.jdbcUrl");
		String user = bundle.getString("jdbc.username");
		String password = bundle.getString("jdbc.password");
		ds = new ComboPooledDataSource();
		try {
			ds.setDriverClass(driverClass);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		ds.setJdbcUrl(jdbcUrl);
		ds.setUser(user);
		ds.setPassword(password);
	}

	public static synchronized Connection getConnection() {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}
