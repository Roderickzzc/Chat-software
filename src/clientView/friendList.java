package clientView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import classes.LeaveMessage;
import classes.User;
import client.userConn;
import utility.Message;
import utility.configConnection;
import utility.connectionUtil;
import utility.statusType;
import utility.threadManageClient;

public class friendList extends JFrame {

	public String username;
	JScrollPane jScrollPane = null;
	public JPanel jPanel = null;

	public List<User> usersList;
	userConn conn = new userConn();

	public friendList(String username) {
		this.username = username;
	}

	public JLabel[] jLabels;

	public void setupPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.LIGHT_GRAY);
		setSize(300, 500);
		this.setTitle(username + " 's Friend List");
		usersList = conn.getFriendList(this);
		jPanel = new JPanel(new GridLayout(usersList.size(), 1, 20, 20));
		jLabels = new JLabel[usersList.size()];
		if (usersList != null) {
			for (int i = 0; i < usersList.size(); i++) {
				User currUser = usersList.get(i);
				ImageIcon imageIcon = new ImageIcon(friendList.class.getClassLoader().getResource("image/icon.png"));
				Image image = imageIcon.getImage();
				Image newimg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
				imageIcon = new ImageIcon(newimg);
				JLabel jLabel = new JLabel(currUser.getUsername(), imageIcon, JLabel.LEFT);
				if (currUser.getOnlineStatus() == 0) {
					jLabel.setForeground(Color.GRAY);
					jLabel.setEnabled(false);
				}

				jLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 2) {

							JLabel currJLabel = (JLabel) e.getSource();
							if (currJLabel.getForeground() == Color.blue) {
								currJLabel.setForeground(Color.black);
							}

							List<LeaveMessage> leaveMessages = null;
							Message getMessage = new Message();
							getMessage.setStatusType(statusType.GET_MESSAGE);
							getMessage.setUsernameString(currJLabel.getText());
							getMessage.setTargetnameString(username);
							try {
								Socket socket = new Socket(configConnection.HOST, configConnection.PORT);
								connectionUtil.getConnectionUtil().writeMessage(socket, getMessage);

								Message messageGot = connectionUtil.getConnectionUtil().readMessage(socket);
								leaveMessages = messageGot.getLeaveMessages();

							} catch (Exception e1) {
								e1.printStackTrace();
							}

							chat aChat = new chat(username, currJLabel.getText(), leaveMessages);
							aChat.setupPanel();
						}
					}
				});
				jPanel.add(jLabel);
				jLabels[i] = jLabel;
			}
		}

		jScrollPane = new JScrollPane(jPanel);
		this.add(jScrollPane, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e1) {
				Message messageCloseClient2Server = new Message();
				messageCloseClient2Server.setStatusType(statusType.LOGOUT);
				messageCloseClient2Server.setUsernameString(username);
				try {
					Socket socket = new Socket(configConnection.HOST, configConnection.PORT);
					connectionUtil.getConnectionUtil().writeMessage(socket, messageCloseClient2Server);
					socket.close();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				threadManageClient.threadMap.get(username).off();

			}

			@Override
			public void windowClosed(WindowEvent e2) {

			}

		});
		this.setVisible(true);
	}

}
