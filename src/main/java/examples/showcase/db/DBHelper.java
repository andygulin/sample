package examples.showcase.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DBHelper {
	private DBHelper() {
	}

	public static int count(String sql, Object[] args) {
		int count = 0;
		int len = args.length;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con = ConnectionFactory.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			if (len > 0) {
				for (int i = 0; i < len; i++) {
					pstmt.setObject(i + 1, args[i]);
				}
			}
			rs = pstmt.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {

		} finally {
			close(con, pstmt, rs);
		}
		return count;
	}

	public static int count(String sql) {
		return count(sql, new Object[] {});
	}

	public static int executeSQL(String sql) {
		return executeSQL(sql, new Object[] {});
	}

	public static int executeSQL(String sql, Object[] args) {
		int rows = 0;
		int len = args.length;
		Connection con = null;
		PreparedStatement pstmt = null;
		con = ConnectionFactory.getConnection();
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql);
			if (len > 0) {
				for (int i = 0; i < len; i++) {
					pstmt.setObject(i + 1, args[i]);
				}
			}
			rows = pstmt.executeUpdate();
			commit(con);
		} catch (SQLException e) {
			rollback(con);
		} finally {
			close(con, pstmt);
		}
		return rows;
	}

	public static List<Map<String, Object>> queryList(String sql, Object[] args) {
		List<Map<String, Object>> list = Lists.newArrayList();
		int len = args.length;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		con = ConnectionFactory.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			if (len > 0) {
				for (int i = 0; i < len; i++) {
					pstmt.setObject(i + 1, args[i]);
				}
			}
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			List<String> cols = Lists.newArrayListWithCapacity(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				cols.add(rsmd.getColumnLabel(i));
			}
			Map<String, Object> row = null;
			while (rs.next()) {
				row = Maps.newHashMapWithExpectedSize(columnCount);
				for (String col : cols) {
					row.put(col, rs.getObject(col));
				}
				list.add(row);
			}
		} catch (SQLException e) {

		} finally {
			close(con, pstmt, rs);
		}
		return list;
	}

	public static List<Map<String, Object>> queryList(String sql) {
		return queryList(sql, new Object[] {});
	}

	public static Map<String, Object> queryRow(String sql, Object[] args) {
		int len = args.length;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		con = ConnectionFactory.getConnection();
		Map<String, Object> row = null;
		try {
			pstmt = con.prepareStatement(sql);
			if (len > 0) {
				for (int i = 0; i < len; i++) {
					pstmt.setObject(i + 1, args[i]);
				}
			}
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			List<String> cols = Lists.newArrayListWithCapacity(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				cols.add(rsmd.getColumnLabel(i));
			}
			rs.next();
			row = Maps.newHashMapWithExpectedSize(columnCount);
			for (String col : cols) {
				row.put(col, rs.getObject(col));
			}
		} catch (SQLException e) {

		} finally {
			close(con, pstmt, rs);
		}
		return row;
	}

	public static Map<String, Object> queryRow(String sql) {
		return queryRow(sql, new Object[] {});
	}

	public static int insert(String sql, Object[] args, boolean returnId) {
		int rows = 0;
		int insertId = 0;
		int len = args.length;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con = ConnectionFactory.getConnection();
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if (len > 0) {
				for (int i = 0; i < len; i++) {
					pstmt.setObject(i + 1, args[i]);
				}
			}
			rows = pstmt.executeUpdate();
			commit(con);
			rs = pstmt.getGeneratedKeys();
			rs.next();
			insertId = rs.getInt(1);
		} catch (SQLException e) {
			rollback(con);
		} finally {
			close(con, pstmt, rs);
		}
		return returnId ? insertId : rows;
	}

	public static int[] batch(String sql, Object[][] args) {
		int[] rows = null;
		int len = args.length;
		Connection con = null;
		PreparedStatement pstmt = null;
		con = ConnectionFactory.getConnection();
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql);
			if (len > 0) {
				for (int i = 0; i < len; i++) {
					for (int j = 0; j < args[i].length; j++) {
						pstmt.setObject(j + 1, args[i][j]);
					}
					pstmt.addBatch();
				}
			}
			rows = pstmt.executeBatch();
			commit(con);
		} catch (SQLException e) {
			rollback(con);
		} finally {
			close(con, pstmt);
		}
		return rows;
	}

	private static void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
	}

	private static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
			}
		}
	}

	private static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
	}

	private static void close(Connection con, Statement stmt) {
		close(stmt);
		close(con);
	}

	private static void close(Connection con, Statement stmt, ResultSet rs) {
		close(rs);
		close(stmt);
		close(con);
	}

	private static void rollback(Connection con) {
		try {
			con.rollback();
		} catch (SQLException e) {
		}
	}

	private static void commit(Connection con) {
		try {
			con.commit();
		} catch (SQLException e) {
		}
	}
}
