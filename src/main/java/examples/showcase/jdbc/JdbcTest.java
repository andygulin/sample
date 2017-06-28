package examples.showcase.jdbc;

import examples.showcase.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
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

public class JdbcTest {

    private static final Logger logger = LogManager.getLogger(JdbcTest.class);

    private Connection con;
    private PreparedStatement pstmt;
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
        logger.info(genKey);
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
        Map<String, Object> row;
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
        User user;
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
        BufferedInputStream in;
        for (File file : files) {
            pstmt.setString(1, file.getName());
            in = new BufferedInputStream(new FileInputStream(file));
            pstmt.setBlob(2, in);
            pstmt.setObject(3, new Date());
            pstmt.executeUpdate();
            logger.info(file);
        }
    }

    @Test
    public void readBlob() throws SQLException, IOException {
        String sql = "select * from `file`";
        pstmt = con.prepareStatement(sql);
        rs = pstmt.executeQuery();
        BufferedInputStream in;
        BufferedOutputStream out;
        while (rs.next()) {
            logger.info(rs.getInt("id"));
            logger.info(rs.getString("filename"));
            in = new BufferedInputStream(rs.getBlob("data").getBinaryStream());
            out = new BufferedOutputStream(new FileOutputStream(new File("/tmp", rs.getString("filename"))));
            IOUtils.copy(in, out);
            logger.info(rs.getTimestamp("createAt"));
            logger.info(StringUtils.repeat("=", 80));
        }
    }

    private File[] getFiles() {
        File file = new File("/home/gulin/文档");
        return file.listFiles(pathname -> pathname.isFile());
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
                logger.info("id : " + row.get("id"));
                logger.info("name : " + row.get("name"));
                logger.info("age : " + row.get("age"));
                logger.info("address : " + row.get("address"));
                logger.info("createAt : " + row.get("createAt"));
                logger.info(StringUtils.repeat("=", 80));
            }
            if (obj instanceof User) {
                User user = (User) obj;
                logger.info("id : " + user.getId());
                logger.info("name : " + user.getName());
                logger.info("age : " + user.getAge());
                logger.info("address : " + user.getAddress());
                logger.info("createAt : " + user.getCreateAt());
                logger.info(StringUtils.repeat("=", 80));
            }
        }
    }

    private void print(ResultSet rs) throws SQLException {
        logger.info("id : " + rs.getInt("id"));
        logger.info("name : " + rs.getString("name"));
        logger.info("age : " + rs.getInt("age"));
        logger.info("address : " + rs.getString("address"));
        logger.info("createAt : " + rs.getTimestamp("createAt"));
        logger.info(StringUtils.repeat("=", 80));
    }

    @After
    public void close() throws SQLException {
        if (pstmt != null) {
            pstmt.close();
        }
        if (rs != null) {
            rs.close();
        }
        if (con != null) {
            con.close();
        }
    }
}
