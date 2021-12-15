package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import classes.User;
import clientView.friendList;
import utility.Message;
import utility.configConnection;
import utility.connectionUtil;
import utility.statusType;
import utility.threadManageClient;
import utility.threadRefreshFriendslist;

public class userConn {

	public boolean login(User user) {
		try {
			Socket socket = new Socket(configConnection.HOST, configConnection.PORT);
			Message messageLogClient2Server = new Message();
			messageLogClient2Server.setStatusType(statusType.LOGIN);
			messageLogClient2Server.setUser(user);

			connectionUtil.getConnectionUtil().writeMessage(socket, messageLogClient2Server);

			Message messageLogServer2Client = connectionUtil.getConnectionUtil().readMessage(socket);

			socket.close();
			if (messageLogServer2Client.getStatusType() == statusType.LOGIN_SUCCESS) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean register(User user) {
		try {

			Message request = new Message();
			request.setStatusType(statusType.REGISTER);
			request.setUser(user);

			Socket socket = new Socket(configConnection.HOST, configConnection.PORT);
			connectionUtil.getConnectionUtil().writeMessage(socket, request);

			Message responsemessage = connectionUtil.getConnectionUtil().readMessage(socket);
			socket.close();

			if (responsemessage.getStatusType() == statusType.REGISTER_SUCCESS) {
				return true;
			} else {
				return false;
			}
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public List<User> getFriendList(friendList frds) {

		List<User> users = null;
		try {
			Socket socket = new Socket(configConnection.HOST, configConnection.PORT);
			Message messagFriendClient2Server = new Message();
			messagFriendClient2Server.setUsernameString(frds.username);
			messagFriendClient2Server.setStatusType(statusType.FRIENDS);
			connectionUtil.getConnectionUtil().writeMessage(socket, messagFriendClient2Server);

			Message messageFriendServer2Client = connectionUtil.getConnectionUtil().readMessage(socket);

			if (messageFriendServer2Client.getStatusType() == statusType.FRIENDS_OK) {
				users = messageFriendServer2Client.getFriends();
			}
			threadRefreshFriendslist thread = new threadRefreshFriendslist(socket, frds);
			thread.start();
			threadManageClient.threadMap.put(frds.username, thread);

			// socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return users;

	}

}
