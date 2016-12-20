package examples.showcase.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DBTest {

    private static final Logger logger = LogManager.getLogger(DBTest.class);

    @Test
    public void count() {
        int count = DBHelper.count("select count(*) from `user`");
        logger.info(count);
    }

    @Test
    public void count2() {
        int count = DBHelper.count("select count(*) from `user` where id=?", 1);
        logger.info(count);
    }

    @Test
    public void insert() {
        int newId = DBHelper.insert("insert into `user` values(?,?,?,?,?)", true,
                null, "bbb", 12, "shanghai", new Date());
        logger.info(newId);
    }

    @Test
    public void query() {
        Map<String, Object> row = DBHelper.queryRow("select * from `user` where id=?", 1);
        logger.info(row);
    }

    @Test
    public void query2() {
        List<Map<String, Object>> list = DBHelper.queryList("select * from `user`");
        logger.info(list);
    }

    @Test
    public void batch() {
        Object[][] args = {{null, "ccc", 11, "beijing", new Date()}, {null, "ddd", 15, "shanghai", new Date()}};
        int[] result = DBHelper.batch("insert into `user` values(?,?,?,?,?)", args);
        logger.info(Arrays.toString(result));
    }

    @Test
    public void delete() {
        int result = DBHelper.executeSQL("delete from `user` where id=?", 1);
        logger.info(result);
    }

    @Test
    public void update() {
        int result = DBHelper.executeSQL("update `user` set `name`=?,age=?,address=?,createAt=? where id=?",
                "abc", 99, "xinjiang", new Date(), 2);
        logger.info(result);
    }

}
