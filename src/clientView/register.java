package clientView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import classes.User;
import client.userConn;

public class register extends JFrame {

	public register() {
		// TODO Auto-generated constructor stub
	}

	JPanel signupPanel;
	JLabel usernameLabel;
	JTextField usernameField;
	JLabel pwdLabel;
	JPasswordField pwdField;
	JLabel repwdLabel;
	JPasswordField repwdField;

	JPanel btnPanel;
	JButton submitButton;
	JButton resetButton;

	public void setupPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.LIGHT_GRAY);
		setSize(400, 200);
		this.setTitle("Sign up");

		signupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pnlInputName = new JPanel();
		usernameLabel = new JLabel("username:               ");
		usernameField = new JTextField(18);
		pnlInputName.add(usernameLabel, BorderLayout.WEST);
		pnlInputName.add(usernameField, BorderLayout.EAST);
		JPanel pnlPwd = new JPanel();
		pwdLabel = new JLabel("password:               ");
		pwdField = new JPasswordField(18);
		pnlPwd.add(pwdLabel, BorderLayout.WEST);
		pnlPwd.add(pwdField, BorderLayout.EAST);
		JPanel pnlrePwd = new JPanel();
		repwdLabel = new JLabel("confirm password: ");
		repwdField = new JPasswordField(18);
		pnlrePwd.add(repwdLabel, BorderLayout.WEST);
		pnlrePwd.add(repwdField, BorderLayout.EAST);

		signupPanel.add(pnlInputName, BorderLayout.NORTH);
		signupPanel.add(pnlPwd, BorderLayout.CENTER);
		signupPanel.add(pnlrePwd, BorderLayout.SOUTH);

		btnPanel = new JPanel();
		submitButton = new JButton("Submit");

		submitButton.addActionListener(e -> {
			String pwdString = new String(pwdField.getPassword());
			String repwdString = new String(repwdField.getPassword());
			if (pwdString.equals(repwdString)) {
				User user = new User();
				user.setUsername(usernameField.getText());
				user.setPwd(pwdString);
				userConn conn = new userConn();
				if (conn.register(user)) {
					this.dispose();
					JOptionPane.showMessageDialog(register.this, "Registration successful!", "",
							JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(register.this, "username has already been taken!", "",
							JOptionPane.WARNING_MESSAGE);
				}

			} else {
				JOptionPane.showMessageDialog(register.this, "Inconsistent password!", "", JOptionPane.WARNING_MESSAGE);
			}

		});

		btnPanel.add(submitButton);
		this.add(signupPanel, BorderLayout.CENTER);
		this.add(btnPanel, BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public static void main(String[] args) {
		register a = new register();
		a.setupPanel();
	}

}
