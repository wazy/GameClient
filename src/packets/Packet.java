package packets;

import java.io.IOException;
import java.net.Socket;

public abstract class Packet {

	public String opcodeKey;
	public short opcodeVal;
	public short size;

	Socket connection = null;

	public void put (Packet p) {
		this.opcodeKey = p.opcodeKey;
		this.opcodeVal = p.opcodeVal;
		this.size = p.size;
	}
	
	public void send(int packet) throws IOException {
		connection.getOutputStream().write(packet);
	}
}
