package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import classes.LeaveMessage;
import utility.dataBaseUtil;

public class serverMessageDataManage {

	public void addMessage(LeaveMessage message) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataBaseUtil.getJdbcUtil().getConnection();
			StringBuffer stringBuffer = new StringBuffer(
					"insert into leavemessage(username,friendname,content) values(?,?,?)");
			preparedStatement = connection.prepareStatement(stringBuffer.toString());

			preparedStatement.setString(1, message.getUsername());
			preparedStatement.setString(2, message.getFriendname());
			preparedStatement.setString(3, message.getContentString());

			preparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dataBaseUtil.getJdbcUtil().closeConnection(null, preparedStatement, connection);
		}

	}

	public List<LeaveMessage> getInfo(String username, String friendname) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<LeaveMessage> leaveMessages = null;
		try {
			connection = dataBaseUtil.getJdbcUtil().getConnection();
			connection.setAutoCommit(false);
			StringBuffer stringBuffer = new StringBuffer(
					" select id,username,friendname,content from leavemessage where username=? and friendname=?");
			preparedStatement = connection.prepareStatement(stringBuffer.toString());
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, friendname);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.wasNull()) {
				leaveMessages = new ArrayList<LeaveMessage>();
			}
			while (resultSet.next()) {
				LeaveMessage leaveMessage = new LeaveMessage();
				leaveMessage.setId(resultSet.getInt("id"));
				leaveMessage.setUsername(resultSet.getString("username"));
				leaveMessage.setFriendname(resultSet.getString("friendname"));
				leaveMessage.setContentString(resultSet.getString("content"));
				leaveMessages.add(leaveMessage);
			}
			StringBuffer stringBuffer2 = new StringBuffer(
					" delete from leavemessage where username=? and friendname=?");
			preparedStatement = connection.prepareStatement(stringBuffer2.toString());
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, friendname);
			preparedStatement.executeUpdate();

			connection.commit();

		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			dataBaseUtil.getJdbcUtil().closeConnection(resultSet, preparedStatement, connection);
		}
		return leaveMessages;
	}

}
