package utility;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import classes.LeaveMessage;
import server.serverMessageDataManage;

public class threadChatServer extends Thread {

	Socket socket;
	serverMessageDataManage messageData = new serverMessageDataManage();
	boolean on = true;

	public threadChatServer(Socket socket) {
		this.socket = socket;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public void off() {
		on = false;
	}

	@Override
	public void run() {
		while (on) {
			try {
				Message messageClient2Server = connectionUtil.getConnectionUtil().readMessage(socket);
				if (messageClient2Server.getStatusType() == statusType.CHAT_MESSAGE) {

					// System.out.println(messageClient2Server.getWarning());
					threadChatServer thread = threadManageServer.threadMap
							.get(messageClient2Server.getTargetnameString() + "-"
									+ messageClient2Server.getUsernameString());

					if (thread != null) {
						Socket targetSocket = thread.getSocket();
						Message messageServer2Client = new Message();
						messageServer2Client.setStatusType(statusType.CHAT_MESSAGE);
						messageServer2Client.setWarning(messageClient2Server.getWarning());
						connectionUtil.getConnectionUtil().writeMessage(targetSocket, messageClient2Server);
					} else {

						LeaveMessage leaveMessage = new LeaveMessage();
						leaveMessage.setUsername(messageClient2Server.getUsernameString());
						leaveMessage.setFriendname(messageClient2Server.getTargetnameString());
						leaveMessage.setContentString(messageClient2Server.getWarning());
						messageData.addMessage(leaveMessage);

						Socket friendRefreshSocket = threadManageServer.socketMap
								.get(messageClient2Server.getTargetnameString());
						if (friendRefreshSocket != null) {
							Message message = new Message();
							message.setStatusType(statusType.YOU_HAVE_MESSAGE);
							message.setTargetnameString(messageClient2Server.getUsernameString());
							connectionUtil.getConnectionUtil().writeMessage(friendRefreshSocket, message);
						} else {
							Message messageServerBack = new Message();
							messageServerBack.setStatusType(statusType.TARGET_OFFLINE);
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							messageServerBack.setWarning("Server to " + messageClient2Server.getUsernameString() + " @ "
									+ simpleDateFormat.format(new Date()) + "\n" + "Target is offline");
							connectionUtil.getConnectionUtil().writeMessage(socket, messageServerBack);

						}

					}

				} else if (messageClient2Server.getStatusType() == statusType.CHAT_CLOSE) {
					this.off();
					threadManageServer.threadMap.remove(messageClient2Server.getUsernameString() + "-"
							+ messageClient2Server.getTargetnameString());
					// threadManageServer.socketMap.remove()
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
