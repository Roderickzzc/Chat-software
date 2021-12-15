package clientView;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import classes.User;
import client.userConn;

public class loginInterface extends JFrame {

	JLabel lblUsername, lblPassword;
	JTextField txtUsername;
	JPasswordField txtPassword;
	JButton btnLogin, btnRegister;

	public static void main(String[] args) {
		loginInterface lgLogin = new loginInterface();
		lgLogin.setupPanels();
	}

	public void setupPanels() {
		// overview
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.LIGHT_GRAY);
		setSize(300, 150);
		this.setTitle("my Login");
		// this.setResizable(false);

		// center login and
		lblUsername = new JLabel("Username :");
		lblPassword = new JLabel("Password :");
		txtUsername = new JTextField(18);
		txtPassword = new JPasswordField(18);
		JPanel pnlInputName = new JPanel();
		pnlInputName.add(lblUsername, BorderLayout.WEST);
		pnlInputName.add(txtUsername, BorderLayout.EAST);
		JPanel pnlInputPwd = new JPanel();
		pnlInputPwd.add(lblPassword, BorderLayout.WEST);
		pnlInputPwd.add(txtPassword, BorderLayout.EAST);
		this.add(pnlInputName, BorderLayout.NORTH);
		this.add(pnlInputPwd, BorderLayout.CENTER);
		// button
		btnLogin = new JButton("Login");

		btnLogin.addActionListener(e -> {
			User user = new User();
			user.setUsername(txtUsername.getText());
			user.setPwd(new String(txtPassword.getPassword()));
			userConn conn = new userConn();
			if (conn.login(user)) {
				loginInterface.this.dispose();
				friendList list = new friendList(user.getUsername());
				list.setupPanel();
			} else {
				JOptionPane.showMessageDialog(loginInterface.this, "Wrong username or password!", "",
						JOptionPane.WARNING_MESSAGE);
			}
		});

		btnRegister = new JButton("Create an account");
		btnRegister.addActionListener(e -> {
			register reg = new register();
			reg.setupPanel();
		});

		JPanel btnPanel = new JPanel();
		btnPanel.add(btnLogin, BorderLayout.WEST);
		btnPanel.add(btnRegister, BorderLayout.EAST);
		this.add(btnPanel, BorderLayout.SOUTH);

		setVisible(true);
	}

}
