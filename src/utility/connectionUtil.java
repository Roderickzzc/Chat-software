package utility;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class connectionUtil {
	private static connectionUtil conn = null;

	private connectionUtil() {

	}

	public static connectionUtil getConnectionUtil() {
		if (conn == null) {
			conn = new connectionUtil();
		}
		return conn;
	}

	public void writeMessage(Socket socket, Message message) throws Exception {
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		objectOutputStream.writeObject(message);
	}

	public Message readMessage(Socket socket) throws Exception {
		InputStream inputStream = socket.getInputStream();
		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
		Message message = (Message) objectInputStream.readObject();
		return message;
	}
}
