package utility;

import java.net.Socket;

import javax.swing.JTextArea;

public class threadChatClient extends Thread {

	Socket socket;
	JTextArea jTextArea;
	boolean on = true;

	public threadChatClient(Socket socket, JTextArea jTextArea) {
		this.socket = socket;
		this.jTextArea = jTextArea;
	}

	public void off() {
		on = false;
	}

	@Override
	public void run() {
		while (on) {
			Message messageIn;
			try {
				messageIn = connectionUtil.getConnectionUtil().readMessage(socket);
				if (messageIn.getStatusType() == statusType.CHAT_MESSAGE) {
					// jTextArea.setText(messageIn.getWarning());
					if (!messageIn.getUsernameString().equals(messageIn.getTargetnameString())) {
						jTextArea.append(messageIn.getWarning() + "\n");
					}
				} else if (messageIn.getStatusType() == statusType.TARGET_OFFLINE) {
					jTextArea.append(messageIn.getWarning() + "\n");
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
