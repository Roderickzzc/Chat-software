package clientView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import classes.LeaveMessage;
import utility.Message;
import utility.configConnection;
import utility.connectionUtil;
import utility.statusType;
import utility.threadChatClient;

public class chat extends JFrame {

	String me;
	String friendName;
	List<LeaveMessage> leaveMessages;
	JTextArea jTextArea;
	JScrollPane jScrollPane;
	JPanel southJPanel;
	JTextField jTextField;
	JButton sendButton;

	Socket socket;
	threadChatClient thread_CHAT_Client;

	public chat(String me, String frd, List<LeaveMessage> leaveMessages) {
		this.me = me;
		this.friendName = frd;
		this.leaveMessages = leaveMessages;
	}

	public void setupPanel() {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.LIGHT_GRAY);
		setSize(500, 400);
		this.setTitle(this.me + " is chatting with " + this.friendName);

		jTextArea = new JTextArea();
		jTextArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
		jTextArea.setEditable(false);
		jScrollPane = new JScrollPane(jTextArea);
		add(jScrollPane, BorderLayout.CENTER);

		if (leaveMessages.size() != 0) {
			for (int i = 0; i < leaveMessages.size(); i++) {
				jTextArea.append(leaveMessages.get(i).getContentString() + "\n");
			}
			;
		}

		southJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jTextField = new JTextField(20);
		jTextField.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
		sendButton = new JButton("send");
		sendButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					// Socket socket = new Socket(configConnection.HOST, configConnection.PORT);
					Message messagChatClient2Server = new Message();
					messagChatClient2Server.setStatusType(statusType.CHAT_MESSAGE);
					messagChatClient2Server.setUsernameString(me);
					messagChatClient2Server.setTargetnameString(friendName);

					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					messagChatClient2Server.setWarning(me + " to " + friendName + " @ "
							+ simpleDateFormat.format(new Date()) + "\n" + jTextField.getText());
					connectionUtil.getConnectionUtil().writeMessage(socket, messagChatClient2Server);

					jTextArea.append(me + " to " + friendName + " @ " + simpleDateFormat.format(new Date()) + "\n"
							+ jTextField.getText() + "\n");
					jTextField.setText("");
					// messagChatClient2Server.setWarning(me + " to " + friendName + ": " +
					// jTextField.getText());

				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		southJPanel.add(jTextField);
		southJPanel.add(sendButton);
		add(southJPanel, BorderLayout.SOUTH);
		this.setVisible(true);

		try {
			socket = new Socket(configConnection.HOST, configConnection.PORT);
			Message messagChatClient2Server = new Message();
			messagChatClient2Server.setStatusType(statusType.CHAT_CONN);
			messagChatClient2Server.setUsernameString(me);
			messagChatClient2Server.setTargetnameString(friendName);
			connectionUtil.getConnectionUtil().writeMessage(socket, messagChatClient2Server);

			// Message messageIn = connectionUtil.getConnectionUtil().readMessage(socket);
			// jTextArea.setText(messageIn.getWarning());
			thread_CHAT_Client = new threadChatClient(socket, jTextArea);
			thread_CHAT_Client.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				Message messagCloseClient2Server = new Message();
				messagCloseClient2Server.setStatusType(statusType.CHAT_CLOSE);
				messagCloseClient2Server.setUsernameString(me);
				messagCloseClient2Server.setTargetnameString(friendName);
				try {
					connectionUtil.getConnectionUtil().writeMessage(socket, messagCloseClient2Server);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				thread_CHAT_Client.off();

			}
		});

	}

}
