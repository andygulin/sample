package examples.showcase.jdbc;

import java.beans.PropertyVetoException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.enhydra.jdbc.standard.StandardXADataSource;
import org.junit.Before;
import org.junit.Test;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.vibur.dbcp.ViburDBCPDataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.jolbox.bonecp.BoneCPDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zaxxer.hikari.HikariDataSource;

public class JdbcPoolTest {

	private static final Logger logger = LogManager.getLogger(JdbcPoolTest.class);

	private static String USERNAME;
	private static String PASSWORD;
	private static String URL;
	private static String CLASSNAME;

	@Before
	public void before() throws IOException {
		Resource resource = new ClassPathResource("jdbc.properties");
		Properties prop = new Properties();
		prop.load(resource.getInputStream());
		USERNAME = prop.getProperty("jdbc.username");
		PASSWORD = prop.getProperty("jdbc.password");
		URL = prop.getProperty("jdbc.jdbcUrl");
		CLASSNAME = prop.getProperty("jdbc.driverClass");
	}

	@Test
	public void dbcp2() throws SQLException {
		BasicDataSource ds = new BasicDataSource();
		ds.setUsername(USERNAME);
		ds.setPassword(PASSWORD);
		ds.setUrl(URL);
		ds.setDriverClassName(CLASSNAME);
		process(ds);
		ds.close();
	}

	@Test
	public void c3p0() throws PropertyVetoException, SQLException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		ds.setUser(USERNAME);
		ds.setPassword(PASSWORD);
		ds.setJdbcUrl(URL);
		ds.setDriverClass(CLASSNAME);
		process(ds);
		ds.close();
	}

	@Test
	public void bonecp() throws SQLException {
		BoneCPDataSource ds = new BoneCPDataSource();
		ds.setUsername(USERNAME);
		ds.setPassword(PASSWORD);
		ds.setJdbcUrl(URL);
		ds.setDriverClass(CLASSNAME);
		process(ds);
		ds.close();
	}

	@Test
	public void xapool() throws SQLException {
		StandardXADataSource ds = new StandardXADataSource();
		ds.setUser(USERNAME);
		ds.setPassword(PASSWORD);
		ds.setUrl(URL);
		ds.setDriverName(CLASSNAME);
		process(ds);
		ds.shutdown(true);
	}

	@Test
	public void hikariCP() throws SQLException {
		HikariDataSource ds = new HikariDataSource();
		ds.setUsername(USERNAME);
		ds.setPassword(PASSWORD);
		ds.setJdbcUrl(URL);
		ds.setDriverClassName(CLASSNAME);
		process(ds);
		ds.close();
	}

	@Test
	public void druid() throws SQLException {
		DruidDataSource ds = new DruidDataSource();
		ds.setUsername(USERNAME);
		ds.setPassword(PASSWORD);
		ds.setUrl(URL);
		ds.setDriverClassName(CLASSNAME);
		ds.setTestWhileIdle(true);
		ds.setValidationQuery("SELECT 1");
		process(ds);
		ds.close();
	}

	@Test
	public void proxool() throws Exception {
		Resource resource = new ClassPathResource("proxool.xml");
		Reader reader = new FileReader(resource.getFile());
		JAXPConfigurator.configure(reader, false);
		Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
		Connection con = DriverManager.getConnection("proxool.mysql");
		Connection con2 = DriverManager.getConnection("proxool.mysql2");
		logger.info(con);
		logger.info(con2);
		if (con != null) {
			con.close();
		}
		if (con2 != null) {
			con2.close();
		}
		ProxoolFacade.shutdown(1);
	}

	@Test
	public void vibur() throws SQLException {
		ViburDBCPDataSource ds = new ViburDBCPDataSource();
		ds.setUsername(USERNAME);
		ds.setPassword(PASSWORD);
		ds.setJdbcUrl(URL);
		ds.setDriverClassName(CLASSNAME);
		ds.start();
		logger.info(ds.getState().name());
		process(ds);
		ds.terminate();
		logger.info(ds.getState().name());
	}

	private void process(DataSource ds) throws SQLException {
		Connection con = ds.getConnection();
		logger.info(ds);
		logger.info(con);
	}
}
