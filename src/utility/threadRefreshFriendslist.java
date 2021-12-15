package utility;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import client.userConn;
import clientView.chat;
import clientView.friendList;

public class threadRefreshFriendslist extends Thread {

	Socket socket;
	friendList frds;

	public threadRefreshFriendslist(Socket socket, friendList frds) {
		this.socket = socket;
		this.frds = frds;
	}

	boolean on = true;

	public void off() {
		on = false;
	}

	@Override
	public void run() {
		while (on) {
			try {
				Message message = connectionUtil.getConnectionUtil().readMessage(socket);
				if (message.getStatusType() == statusType.REFRESH_FRIEND_LIST) {
					JLabel[] jLabels = frds.jLabels;
					for (int i = 0; i < jLabels.length; i++) {
						if (message.getUser().getUsername().equals(jLabels[i].getText())) {
							jLabels[i].setForeground(Color.black);
							jLabels[i].setEnabled(true);
							frds.validate();
						}
					}
				} else if (message.getStatusType() == statusType.LOGOUT_FRIEND_LIST) {
					JLabel[] jLabels = frds.jLabels;
					for (int i = 0; i < jLabels.length; i++) {
						if (message.getTargetnameString().equals(jLabels[i].getText())) {
							jLabels[i].setForeground(Color.gray);
							jLabels[i].setEnabled(false);

							frds.validate();
						}
					}
				} else if (message.getStatusType() == statusType.REG_FRIEND_LIST) {
					JLabel[] jLabels = frds.jLabels;
					JLabel newarr[] = new JLabel[jLabels.length + 1];
					for (int i = 0; i < jLabels.length; i++) {
						newarr[i] = jLabels[i];
					}
					ImageIcon imageIcon = new ImageIcon(
							friendList.class.getClassLoader().getResource("image/icon.png"));
					Image image = imageIcon.getImage();
					Image newimg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
					imageIcon = new ImageIcon(newimg);
					JLabel newUser = new JLabel(message.getUser().getUsername(), imageIcon, JLabel.LEFT);
					if (message.getUser().getOnlineStatus() == 0) {
						newUser.setForeground(Color.GRAY);
						newUser.setEnabled(false);
					}
					newUser.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if (e.getClickCount() == 2) {
								chat aChat = new chat(message.getTargetnameString(), ((JLabel) e.getSource()).getText(),
										null);
								aChat.setupPanel();
							}
						}
					});
					newarr[jLabels.length] = newUser;
					frds.jLabels = newarr;
					frds.jPanel.add(newUser);
					userConn conn = new userConn();
					frds.usersList = conn.getFriendList(frds);
					// System.out.println(frds.usersList);
					frds.jPanel.setLayout(new GridLayout(frds.usersList.size(), 1, 20, 20));

					frds.validate();
				} else if (message.getStatusType() == statusType.YOU_HAVE_MESSAGE) {
					JLabel[] jLabels = frds.jLabels;
					for (int i = 0; i < jLabels.length; i++) {
						if (message.getTargetnameString().equals(jLabels[i].getText())) {
							jLabels[i].setForeground(Color.blue);
							frds.validate();
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
