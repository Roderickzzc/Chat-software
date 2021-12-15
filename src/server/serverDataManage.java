package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import classes.User;
import utility.PropertiesUtil;
import utility.dataBaseUtil;

public class serverDataManage {

	public serverDataManage() {
	}

	public User login(String username, String pwd) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		try {
			connection = dataBaseUtil.getJdbcUtil().getConnection();
			connection.setAutoCommit(false);
			StringBuffer stringBuffer = new StringBuffer(
					" select id,username,pwd from user where username = ? and AES_DECRYPT(pwd,?)=? ");
			preparedStatement = connection.prepareStatement(stringBuffer.toString());
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, PropertiesUtil.getPropertiesUtil().getValue("mykey"));
			preparedStatement.setString(3, pwd);

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user = new User();
				user.setId(resultSet.getInt("id"));
				user.setUsername(resultSet.getString("username"));
				user.setPwd(resultSet.getString("pwd"));
			}

			if (user != null) {
				StringBuffer stringBuffer2 = new StringBuffer(" update user set onlineStatus=1 where username=?");
				preparedStatement = connection.prepareStatement(stringBuffer2.toString());
				preparedStatement.setString(1, user.getUsername());
				preparedStatement.executeUpdate();
			}

			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			dataBaseUtil.getJdbcUtil().closeConnection(resultSet, preparedStatement, connection);
		}
		return user;
	}

	// prevent duplicates usernames
	public User getUser(String username) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		try {
			connection = dataBaseUtil.getJdbcUtil().getConnection();
			StringBuffer stringBuffer = new StringBuffer(" select id,username,pwd from user where username = ?");
			preparedStatement = connection.prepareStatement(stringBuffer.toString());
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user = new User();
				user.setId(resultSet.getInt("id"));
				user.setUsername(resultSet.getString("username"));
				user.setPwd(resultSet.getString("pwd"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dataBaseUtil.getJdbcUtil().closeConnection(resultSet, preparedStatement, connection);
		}
		return user;

	}

	public void addUser(User user) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataBaseUtil.getJdbcUtil().getConnection();
			StringBuffer stringBuffer = new StringBuffer(
					"insert into user(username,pwd,onlineStatus) values(?,AES_ENCRYPT(?,?),0)");
			preparedStatement = connection.prepareStatement(stringBuffer.toString());
			System.out.println(user.getUsername());
			System.out.println(user.getPwd());
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPwd());
			preparedStatement.setString(3, PropertiesUtil.getPropertiesUtil().getValue("mykey"));

			preparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dataBaseUtil.getJdbcUtil().closeConnection(null, preparedStatement, connection);
		}

	}

	public List<User> getFriendList() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<User> usersList = new ArrayList<User>();
		try {
			connection = dataBaseUtil.getJdbcUtil().getConnection();
			StringBuffer stringBuffer = new StringBuffer(" select id,username,pwd,onlineStatus from user");
			preparedStatement = connection.prepareStatement(stringBuffer.toString());
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.wasNull()) {
				usersList = new ArrayList<User>();
			}
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("id"));
				user.setUsername(resultSet.getString("username"));
				// System.out.println(user.getId());
				// System.out.println(user.getUsername());
				// user.setPwd(resultSet.getString("pwd"));
				user.setOnlineStatus(resultSet.getInt("onlineStatus"));
				usersList.add(user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dataBaseUtil.getJdbcUtil().closeConnection(resultSet, preparedStatement, connection);
		}
		return usersList;
	}

	public void logout(String username) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataBaseUtil.getJdbcUtil().getConnection();
			StringBuffer stringBuffer3 = new StringBuffer(" update user set onlineStatus=0 where username=?");
			preparedStatement = connection.prepareStatement(stringBuffer3.toString());
			preparedStatement.setString(1, username);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dataBaseUtil.getJdbcUtil().closeConnection(null, preparedStatement, connection);
		}
	}

}
