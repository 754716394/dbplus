package com.pamirs.dbplus.configure.constant;

import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * SQL Connection
 */
public class SQLConnetionUtil {
	static String ORACLE_THIN_FIX = "jdbc:oracle:thin:@";
	static String MYSQL_FIX = "jdbc:mysql://";

	public static Connection createConn(String ip, String port, String dbName,
                                        String userName, String password, String dbType)
			throws SQLException {
		Connection conn = null;
		String url = null;
		if (StringUtils.equals(JadeConstants.JADE_DBTYPE_MYSQL, dbType)) { // mysql
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			DriverManager.setLoginTimeout(1);
			url = SQLConnetionUtil.MYSQL_FIX + ip + ":" + port + "/" + dbName
					+ "?connectTimeout=1000";
			conn = DriverManager.getConnection(url, userName, password);
		} else if (StringUtils.equals(JadeConstants.JADE_DBTYPE_ORACLE, dbType)) { // orcale

			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			DriverManager.setLoginTimeout(1);
			url = SQLConnetionUtil.ORACLE_THIN_FIX + ip + ":" + port + ":" + dbName;
			conn = DriverManager.getConnection(url, userName, password);
		}
		return conn;
	}
}
