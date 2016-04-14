package examples.showcase.db;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class DBTest {

	@Test
	public void count() {
		int count = DBHelper.count("select count(*) from `user`");
		System.out.println(count);
	}

	@Test
	public void count2() {
		int count = DBHelper.count("select count(*) from `user` where id=?",
				new Object[] { 1 });
		System.out.println(count);
	}

	@Test
	public void insert() {
		int newId = DBHelper.insert("insert into `user` values(?,?,?,?,?)",
				new Object[] { null, "bbb", 12, "shanghai", new Date() }, true);
		System.out.println(newId);
	}

	@Test
	public void query() {
		Map<String, Object> row = DBHelper.queryRow(
				"select * from `user` where id=?", new Object[] { 1 });
		System.out.println(row);
	}

	@Test
	public void query2() {
		List<Map<String, Object>> list = DBHelper
				.queryList("select * from `user`");
		System.out.println(list);
	}

	@Test
	public void batch() {
		Object[][] args = { { null, "ccc", 11, "beijing", new Date() },
				{ null, "ddd", 15, "shanghai", new Date() } };
		int[] result = DBHelper.batch("insert into `user` values(?,?,?,?,?)",
				args);
		System.out.println(Arrays.toString(result));
	}

	@Test
	public void delete() {
		int result = DBHelper.executeSQL("delete from `user` where id=?",
				new Object[] { 1 });
		System.out.println(result);
	}

	@Test
	public void update() {
		int result = DBHelper
				.executeSQL(
						"update `user` set `name`=?,age=?,address=?,createAt=? where id=?",
						new Object[] { "abc", 99, "xinjiang", new Date(), 2 });
		System.out.println(result);
	}

}
