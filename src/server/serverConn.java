package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;

import classes.User;
import utility.Message;
import utility.configConnection;
import utility.connectionUtil;
import utility.statusType;
import utility.threadChatServer;
import utility.threadManageServer;

public class serverConn extends JFrame {
	serverDataManage userData = new serverDataManage();
	serverMessageDataManage messageData = new serverMessageDataManage();

	public static void main(String[] args) {
		serverConn serverClient = new serverConn();
		serverClient.setupPanel();
	}

	public void setupPanel() {
		JLabel jLabel = new JLabel("Server is listening on port: " + configConnection.PORT, JLabel.CENTER);
		add(jLabel, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.LIGHT_GRAY);
		setSize(300, 150);
		setTitle("Server");
		setVisible(true);
		try {
			this.server();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void server() throws Exception {
		ServerSocket serverSocket = new ServerSocket(configConnection.PORT);
		while (true) {
			Socket socket = serverSocket.accept();
			Message messageClient2Server = connectionUtil.getConnectionUtil().readMessage(socket);

			switch (messageClient2Server.getStatusType()) {
			case statusType.LOGIN: {
				Message messageLogServer2Client = new Message();

				if (userData.login(messageClient2Server.getUser().getUsername(),
						messageClient2Server.getUser().getPwd()) != null) {
					messageLogServer2Client.setStatusType(statusType.LOGIN_SUCCESS);
					// System.out.println("s");
					Message refreshFriendListMessage = new Message();
					refreshFriendListMessage.setStatusType(statusType.REFRESH_FRIEND_LIST);
					refreshFriendListMessage.setUser(messageClient2Server.getUser());
					Set<String> keys = threadManageServer.socketMap.keySet();
					for (String key : keys) {
						Socket friendListSocket = threadManageServer.socketMap.get(key);
						connectionUtil.getConnectionUtil().writeMessage(friendListSocket, refreshFriendListMessage);
					}
				} else {
					messageLogServer2Client.setStatusType(statusType.LOGIN_FAIL);
					// System.out.println("f");
				}

				connectionUtil.getConnectionUtil().writeMessage(socket, messageLogServer2Client);
				break;
			}
			case statusType.REGISTER: {
				Message messageRegServer2Client = new Message();
				User registerUser = messageClient2Server.getUser();
				if (userData.getUser(registerUser.getUsername()) == null) {
					messageRegServer2Client.setStatusType(statusType.REGISTER_SUCCESS);
					messageRegServer2Client.setWarning("success!");
					userData.addUser(registerUser);
					//
					Message refreshFriendListMessage = new Message();
					refreshFriendListMessage.setStatusType(statusType.REG_FRIEND_LIST);
					refreshFriendListMessage.setUser(messageClient2Server.getUser());

					Set<String> keys = threadManageServer.socketMap.keySet();
					for (String key : keys) {
						Socket friendListSocket = threadManageServer.socketMap.get(key);
						refreshFriendListMessage.setTargetnameString(key);
						connectionUtil.getConnectionUtil().writeMessage(friendListSocket, refreshFriendListMessage);
					}
				} else {
					messageRegServer2Client.setStatusType(statusType.REGISTER_FAIL);
					messageRegServer2Client.setWarning("username has already been taken!");
				}
				connectionUtil.getConnectionUtil().writeMessage(socket, messageRegServer2Client);
				break;
			}
			case statusType.FRIENDS: {
				List<User> users = userData.getFriendList();
				Message messageFriendServer2Client = new Message();
				messageFriendServer2Client.setFriends(users);
				messageFriendServer2Client.setStatusType(statusType.FRIENDS_OK);

				connectionUtil.getConnectionUtil().writeMessage(socket, messageFriendServer2Client);
				threadManageServer.socketMap.put(messageClient2Server.getUsernameString(), socket);
				break;
			}
			case statusType.CHAT_CONN: {
				// System.out.println(messageClient2Server.getWarning());
				threadChatServer thread_CHAT = new threadChatServer(socket);
				thread_CHAT.start();
				threadManageServer.threadMap.put(
						messageClient2Server.getUsernameString() + "-" + messageClient2Server.getTargetnameString(),
						thread_CHAT);
				break;
			}
			case statusType.LOGOUT: {
				threadManageServer.socketMap.remove(messageClient2Server.getUsernameString());
				userData.logout(messageClient2Server.getUsernameString());
				Message refreshFriendListMessage = new Message();
				refreshFriendListMessage.setStatusType(statusType.LOGOUT_FRIEND_LIST);
				refreshFriendListMessage.setTargetnameString(messageClient2Server.getUsernameString());
				Set<String> keys = threadManageServer.socketMap.keySet();
				for (String key : keys) {
					Socket friendListSocket = threadManageServer.socketMap.get(key);
					connectionUtil.getConnectionUtil().writeMessage(friendListSocket, refreshFriendListMessage);
				}
				break;
			}
			case statusType.GET_MESSAGE: {
				Message messageReplyServer2Client = new Message();
				messageReplyServer2Client.setLeaveMessages(messageData.getInfo(messageClient2Server.getUsernameString(),
						messageClient2Server.getTargetnameString()));
				connectionUtil.getConnectionUtil().writeMessage(socket, messageReplyServer2Client);

				break;
			}
			}
			// System.out.println(user.toString());
			// System.out.println(userData.login(user.getUsername(), user.getPwd()));

		}

	}
}
