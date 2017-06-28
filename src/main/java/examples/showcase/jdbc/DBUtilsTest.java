package examples.showcase.jdbc;

import examples.showcase.User;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.BeanMapHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DBUtilsTest {

    private static final Logger logger = LogManager.getLogger(DBUtilsTest.class);

    private Connection con;
    private PreparedStatement pstmt;

    @Before
    public void before() throws ConfigurationException, IOException {
        Resource resource = new ClassPathResource("jdbc.properties");
        InputStream is = resource.getInputStream();
        Properties properties = new Properties();
        properties.load(is);
        boolean success = DbUtils.loadDriver(properties.getProperty("jdbc.driverClass"));
        if (success) {
            try {
                con = DriverManager.getConnection(properties.getProperty("jdbc.jdbcUrl"),
                        properties.getProperty("jdbc.username"), properties.getProperty("jdbc.password"));
            } catch (SQLException e) {
                logger.info("conn error");
            }
        } else {
            System.err.println("driver error");
        }
    }

    @Test
    public void batch() {
        QueryRunner query = new QueryRunner();
        String sql = "INSERT INTO `user`(`name`,age,address,createAt) VALUES(?,?,?,?)";
        Object[][] params = {{"111", 11, "sh", new Date()}, {"222", 12, "bj", new Date()}};
        try {
            query.batch(con, sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void update() {
        QueryRunner query = new QueryRunner();
        String sql = "UPDATE `user` SET `name`=? WHERE id=?";
        try {
            query.update(con, sql, "2342", 2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fill() {
        QueryRunner query = new QueryRunner();
        try {
            User user = new User();
            user.setName("bbb");
            user.setAge(12);
            user.setAddress("beijin");
            user.setCreateAt(new Date());
            String[] propNames = {"name", "age", "address", "createAt"};
            String sql = "INSERT INTO `user`(`name`,age,address,createAt) VALUES(?,?,?,?)";
            pstmt = con.prepareStatement(sql);
            query.fillStatementWithBean(pstmt, user, propNames);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fill2() {
        QueryRunner query = new QueryRunner();
        try {
            Object[] params = {"ccc", 14, "shandong", new Date()};
            String sql = "INSERT INTO `user`(`name`,age,address,createAt) VALUES(?,?,?,?)";
            pstmt = con.prepareStatement(sql);
            query.fillStatement(pstmt, params);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fill3() {
        QueryRunner query = new QueryRunner();
        try {
            User user = new User();
            user.setName("bbb");
            user.setAge(12);
            user.setAddress("beijin");
            user.setCreateAt(new Date());
            String sql = "INSERT INTO `user`(`name`,age,address,createAt) VALUES(?,?,?,?)";
            pstmt = con.prepareStatement(sql);
            PropertyDescriptor[] descriptors = {new PropertyDescriptor("name", User.class),
                    new PropertyDescriptor("age", User.class), new PropertyDescriptor("address", User.class),
                    new PropertyDescriptor("createAt", User.class)};
            query.fillStatementWithBean(pstmt, user, descriptors);
            pstmt.executeUpdate();
        } catch (SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void scalar() {
        QueryRunner query = new QueryRunner();
        String sql = "SELECT `name` FROM `user` WHERE id=?";
        try {
            String name = query.query(con, sql, new ScalarHandler<String>("name"), 2);
            logger.info(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void map() {
        QueryRunner query = new QueryRunner();
        String sql = "SELECT * FROM `user` WHERE id=?";
        try {
            Map<String, Object> row = query.query(con, sql, new MapHandler(), 2);
            logger.info(row);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void array() {
        QueryRunner query = new QueryRunner();
        String sql = "SELECT * FROM `user` WHERE id=?";
        try {
            Object[] objs = query.query(con, sql, new ArrayHandler(), 2);
            logger.info(Arrays.toString(objs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void bean() {
        QueryRunner query = new QueryRunner();
        String sql = "SELECT * FROM `user` WHERE id=?";
        try {
            User user = query.query(con, sql, new BeanHandler<>(User.class), 2);
            logger.info(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void beanList() {
        QueryRunner query = new QueryRunner();
        String sql = "SELECT * FROM `user`";
        try {
            List<User> users = query.query(con, sql, new BeanListHandler<>(User.class));
            for (User user : users)
                logger.info(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listObj() {
        QueryRunner query = new QueryRunner();
        String sql = "SELECT * FROM `user`";
        try {
            List<Object[]> objs = query.query(con, sql, new ArrayListHandler());
            for (Object[] obj : objs) {
                logger.info(Arrays.toString(obj));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void beanMap() {
        QueryRunner query = new QueryRunner();
        String sql = "SELECT * FROM `user`";
        try {
            Map<String, User> users = query.query(con, sql, new BeanMapHandler<String, User>(User.class, "name"));
            User user = users.get("111");
            logger.info(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void columnList() {
        QueryRunner query = new QueryRunner();
        String sql = "SELECT `name` FROM `user`";
        try {
            List<String> names = query.query(con, sql, new ColumnListHandler<String>());
            logger.info(names);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void mapList() {
        QueryRunner query = new QueryRunner();
        String sql = "SELECT `name` FROM `user`";
        try {
            List<Map<String, Object>> users = query.query(con, sql, new MapListHandler());
            logger.info(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void keyed() {
        QueryRunner query = new QueryRunner();
        String sql = "SELECT * FROM `user`";
        try {
            Map<String, Map<String, Object>> users = query.query(con, sql, new KeyedHandler<String>("name"));
            Map<String, Object> user = users.get("111");
            logger.info(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void after() {
        DbUtils.closeQuietly(pstmt);
        DbUtils.closeQuietly(con);
    }
}