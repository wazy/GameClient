package packets;

import java.io.IOException;
import java.net.Socket;

public abstract class Packet {
	
	Socket connection = null;
	
	public Packet (Socket connection) {
		this.connection = connection;
	}

	public void send(int packet) throws IOException {
		connection.getOutputStream().write(packet);
	}
}
