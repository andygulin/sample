package examples.showcase.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;

import examples.showcase.User;

public class JdbcTemplateTest {

	private static final Logger logger = LogManager.getLogger(JdbcTemplateTest.class);

	private JdbcTemplate jdbcTemplate;
	private BoneCPDataSource dataSource;

	@Before
	public void before() throws Exception {
		Properties props = new Properties();
		Resource resource = new ClassPathResource("jdbc.properties");
		Configuration configuration = new PropertiesConfiguration(resource.getFile());
		Iterator<String> keys = configuration.getKeys();
		while (keys.hasNext()) {
			String key = keys.next();
			String value = configuration.getString(key);
			if (key.startsWith("jdbc.")) {
				props.put(key.substring(5), value);
			}
		}
		BoneCPConfig config = new BoneCPConfig(props);
		dataSource = new BoneCPDataSource(config);

		this.jdbcTemplate = new JdbcTemplate(dataSource, true);
	}

	@Test
	public void insert() {
		final String sql = "INSERT INTO `user` VALUES(NULL,?,?,?,?)";
		int result = this.jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, "bbb");
				ps.setInt(2, 12);
				ps.setString(3, "shanghai");
				ps.setObject(4, new Date());
			}
		});
		logger.info(result);
	}

	@Test
	public void insert2() {
		final String sql = "INSERT INTO `user` VALUES(NULL,?,?,?,?)";
		int result = this.jdbcTemplate.update(sql, "aaa", 14, "beijing", new Date());
		logger.info(result);
	}

	@Test
	public void insert3() {
		final String sql = "INSERT INTO `user` VALUES(NULL,?,?,?,?)";
		KeyHolder holder = new GeneratedKeyHolder();
		int result = this.jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, "bbb");
				ps.setInt(2, 12);
				ps.setString(3, "shanghai");
				ps.setObject(4, new Date());
				return ps;
			}
		}, holder);
		logger.info(result);
		logger.info(holder.getKey().intValue());
	}

	@Test
	public void insert4() {
		final String sql = "INSERT INTO `user` VALUES(NULL,?,?,?,?)";
		Object[] args = { "ccc", 11, "nanjing", new Date() };
		int[] argTypes = { Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.TIMESTAMP };
		int result = this.jdbcTemplate.update(sql, args, argTypes);
		logger.info(result);
	}

	@Test
	public void queryObj() {
		User user = this.jdbcTemplate.query("SELECT * FROM `user` WHERE id=?", new ResultSetExtractor<User>() {
			@Override
			public User extractData(ResultSet rs) throws SQLException, DataAccessException {
				rs.next();
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setName(rs.getString("name"));
				u.setAddress(rs.getString("address"));
				u.setAge(rs.getInt("age"));
				u.setCreateAt(rs.getDate("createAt"));
				return u;
			}
		}, 1);
		logger.info(user);
	}

	@Test
	public void execute() {
		int result = this.jdbcTemplate.execute(new CallableStatementCreator() {
			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement statement = con.prepareCall("UPDATE `user` SET name=? WHERE id=?");
				statement.setString(1, "zzz");
				statement.setInt(2, 1);
				return statement;
			}
		}, new CallableStatementCallback<Integer>() {
			@Override
			public Integer doInCallableStatement(CallableStatement statement) throws SQLException, DataAccessException {
				return statement.executeUpdate();
			}
		});
		logger.info(result);
	}

	@Test
	public void execute2() {
		int result = this.jdbcTemplate.execute(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = con.prepareStatement("UPDATE `user` SET name=? WHERE id=?");
				statement.setString(1, "AAA");
				statement.setInt(2, 1);
				return statement;
			}
		}, new PreparedStatementCallback<Integer>() {
			@Override
			public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				return ps.executeUpdate();
			}
		});
		logger.info(result);
	}

	@Test
	public void queryObject() {
		String name = this.jdbcTemplate.queryForObject("SELECT name FROM `user` WHERE id=?", String.class, 1);
		logger.info(name);
	}

	@Test
	public void queryObject2() {
		Integer age = this.jdbcTemplate.queryForObject("SELECT age FROM `user` WHERE id=?", Integer.class, 1);
		logger.info(age);
	}

	@Test
	public void queryObject3() {
		String name = this.jdbcTemplate.queryForObject("SELECT name FROM `user` WHERE id=?", new Object[] { 1 },
				new int[] { Types.INTEGER }, new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("name");
					}
				});
		logger.info(name);
	}

	@Test
	public void queryListMap() {
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList("SELECT * FROM `user`");
		logger.info(list);
	}

	@Test
	public void queryListMap2() {
		List<Map<String, Object>> list = this.jdbcTemplate.query("SELECT * FROM `user`", new ColumnMapRowMapper());
		logger.info(list);
	}

	@Test
	public void queryBean() {
		List<User> users = this.jdbcTemplate.query("SELECT * FROM `user`", new BeanPropertyRowMapper<User>(User.class));
		logger.info(users);
	}

	@Test
	public void queryColumn() {
		List<String> names = this.jdbcTemplate.query("SELECT `name` FROM `user`",
				new SingleColumnRowMapper<String>(String.class));
		logger.info(names);
	}

	@Test
	public void batch() {
		final String sql = "INSERT INTO `user` VALUES(NULL,?,?,?,?)";
		final List<User> users = new ArrayList<>();
		users.add(new User(null, "aaa", 11, "sh", new Date()));
		users.add(new User(null, "bbb", 12, "bj", new Date()));
		users.add(new User(null, "ccc", 13, "nj", new Date()));
		int[] result = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				User user = users.get(i);
				ps.setString(1, user.getName());
				ps.setInt(2, user.getAge());
				ps.setString(3, user.getAddress());
				ps.setObject(4, user.getCreateAt());
			}

			@Override
			public int getBatchSize() {
				return users.size();
			}
		});
		logger.info(Arrays.toString(result));
	}

	@Test
	public void batch2() {
		final String sql = "INSERT INTO `user` VALUES(NULL,?,?,?,?)";
		List<Object[]> batchArgs = new ArrayList<>();
		batchArgs.add(new Object[] { "aaa", 11, "sh", new Date() });
		batchArgs.add(new Object[] { "bbb", 12, "bj", new Date() });
		batchArgs.add(new Object[] { "ccc", 13, "nj", new Date() });
		int[] argTypes = { Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.TIMESTAMP };
		int[] result = this.jdbcTemplate.batchUpdate(sql, batchArgs, argTypes);
		logger.info(Arrays.toString(result));
	}

	@Test
	public void batch3() {
		final String sql = "INSERT INTO `user` VALUES(NULL,?,?,?,?)";
		List<User> batchArgs = new ArrayList<>();
		batchArgs.add(new User(null, "aaa", 11, "sh", new Date()));
		batchArgs.add(new User(null, "bbb", 12, "bj", new Date()));
		batchArgs.add(new User(null, "ccc", 13, "nj", new Date()));
		int batchSize = batchArgs.size();
		int[][] result = this.jdbcTemplate.batchUpdate(sql, batchArgs, batchSize,
				new ParameterizedPreparedStatementSetter<User>() {
					@Override
					public void setValues(PreparedStatement ps, User user) throws SQLException {
						ps.setString(1, user.getName());
						ps.setInt(2, user.getAge());
						ps.setString(3, user.getAddress());
						ps.setObject(4, user.getCreateAt());
					}
				});
		for (int[] arr : result) {
			logger.info(Arrays.toString(arr));
		}
	}

	@Test
	public void call() {
		/*
		 * INSERT INTO `user` VALUES(NULL,'aaa',11,'sh',NOW());
		 */
		List<SqlParameter> declaredParameters = new ArrayList<>();
		this.jdbcTemplate.call(new CallableStatementCreator() {
			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement statement = con.prepareCall("call insertUser()");
				return statement;
			}
		}, declaredParameters);

	}

	@Test
	public void call2() {
		/*
		 * `insertuser`(p_name VARCHAR(20),p_age
		 * INT,p_addressVARCHAR(20),p_createAt DATETIME)
		 * 
		 * INSERT INTO `user` VALUES(NULL,p_name,p_age,p_address,p_createAt);
		 */
		List<SqlParameter> declaredParameters = new ArrayList<>();
		declaredParameters.add(new SqlParameter(Types.VARCHAR));
		declaredParameters.add(new SqlParameter(Types.INTEGER));
		declaredParameters.add(new SqlParameter(Types.VARCHAR));
		declaredParameters.add(new SqlParameter(Types.TIMESTAMP));
		this.jdbcTemplate.call(new CallableStatementCreator() {
			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement statement = con.prepareCall("call insertUser(?,?,?,?)");
				statement.setString(1, "aa");
				statement.setInt(2, 11);
				statement.setString(3, "sh");
				statement.setObject(4, new Date());
				return statement;
			}
		}, declaredParameters);
	}

	@After
	public void after() {
		if (this.dataSource != null) {
			this.dataSource.close();
		}
	}
}
