package classes;

import java.io.Serializable;

public class User implements Serializable {

	private String username;
	private String pwd;
	private Integer id;
	private int onlineStatus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(int onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	@Override
	public String toString() {
		return "TestSerializableObject [name=" + username + ", id=" + id + ", pwd=" + pwd + ", status=" + onlineStatus
				+ "]";
	}

}
