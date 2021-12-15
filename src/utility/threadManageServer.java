package utility;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class threadManageServer {

	public static Map<String, threadChatServer> threadMap = new HashMap<String, threadChatServer>();

	public static Map<String, Socket> socketMap = new HashMap<String, Socket>();

	public threadManageServer() {
		// TODO Auto-generated constructor stub
	}

}
