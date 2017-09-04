package com.pamirs.dbplus.configure.constant;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * MetaDataUtil
 */
public class MetaDataUtil {

	static String ORACLE_THIN_FIX = "jdbc:oracle:thin:@";
	static String MYSQL_FIX = "jdbc:mysql://";

	@SuppressWarnings("unchecked")
	public static List<String> queryColumnNames(String ip, String port, String dbName, String userName,
                                                String password, String dbType, String tableName) {
		Connection conn = null;
		try {
			conn = SQLConnetionUtil.createConn(ip, port, dbName, userName, password, dbType);
			return getColumnNames(conn, tableName);
		} catch (SQLException e) {

		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return Collections.<String> emptyList();
	}

	/**
	 * 查询表名称
	 * 
	 * @param ip
	 * @param port
	 * @param dbName
	 * @param userName
	 * @param password
	 * @param dbType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> queryTableNames(String ip, String port, String dbName, String userName, String password,
                                               String dbType) {
		Connection conn = null;
		try {
			conn = SQLConnetionUtil.createConn(ip, port, dbName, userName, password, dbType);
			return getAllTableName(conn);
		} catch (SQLException e) {

		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return Collections.<String> emptyList();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getAllTableName(Connection cnn) {
		List tables = new ArrayList();
		String[] types = { "TABLE" };
		ResultSet tabs = null;
		try {
			DatabaseMetaData dbMetaData = cnn.getMetaData();
			tabs = dbMetaData.getTables(null, null, null, types);
			while (tabs.next()) {
				tables.add(tabs.getObject("TABLE_NAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tables;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getColumnNames(Connection conn, String tableName) {
		List columns = new ArrayList();
		ResultSet res = null;
		try {
			DatabaseMetaData dbMetaData = conn.getMetaData();
			res = dbMetaData.getColumns(null, null, tableName, null);
			while (res.next()) {
				columns.add(res.getObject("COLUMN_NAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return columns;
	}

	@SuppressWarnings("rawtypes")
	public static void main(String args[]) throws SQLException {
		List list = queryTableNames("114.55.42.181", "3306", "poyangdb", "admin", "athene.admin", "mysql");
		System.out.println(list);
		list = queryColumnNames("114.55.42.181", "3306", "dbplus", "admin", "athene.admin", "mysql",
				"logic_table_list");
		System.out.println(list);
	}

}