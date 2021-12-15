package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dataBaseUtil {

	private static dataBaseUtil jdbcUtil = null;

	public dataBaseUtil() {

	}

	public static dataBaseUtil getJdbcUtil() {
		if (jdbcUtil == null) {
			jdbcUtil = new dataBaseUtil();
		}
		return jdbcUtil;
	}

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(PropertiesUtil.getPropertiesUtil().getValue("url"),
				PropertiesUtil.getPropertiesUtil().getValue("username"),
				PropertiesUtil.getPropertiesUtil().getValue("pwd"));
	}

	public void closeConnection(ResultSet resultSet, Statement statement, Connection connection) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
