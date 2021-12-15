package utility;

import java.io.Serializable;
import java.util.List;

import classes.LeaveMessage;
import classes.User;

public class Message implements Serializable {

	private int statusType;
	private User user;
	private String warning;
	private List<User> friends;
	private List<LeaveMessage> leaveMessages;

	private String usernameString;
	private String targetnameString;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<LeaveMessage> getLeaveMessages() {
		return leaveMessages;
	}

	public void setLeaveMessages(List<LeaveMessage> leaveMessages) {
		this.leaveMessages = leaveMessages;
	}

	public String getWarning() {
		return warning;
	}

	public void setWarning(String warning) {
		this.warning = warning;
	}

	public int getStatusType() {
		return statusType;
	}

	public void setStatusType(int statusType) {
		this.statusType = statusType;
	}

	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	public String getUsernameString() {
		return usernameString;
	}

	public void setUsernameString(String usernameString) {
		this.usernameString = usernameString;
	}

	public String getTargetnameString() {
		return targetnameString;
	}

	public void setTargetnameString(String targetnameString) {
		this.targetnameString = targetnameString;
	}

}
