package packets;

public enum Packet {	
	CONNECT (0x00),
	DISCONNECT (0x01),
	UPDATE (0x02);
		
	private Packet(int value) {};
}
