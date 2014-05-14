package handlers;

import java.net.InetAddress;
import java.net.Socket;

public class SocketHandler {
	public static Socket fetchSocket() {
		String host = "localhost";
		int port = 8149;
		try {
			// gets server address and attempts to establish a connection
			InetAddress address = InetAddress.getByName(host);
			Socket connection = new Socket(address, port);
			return connection;
		}
		catch (Exception e) {
			return null;
		}
	}
}
