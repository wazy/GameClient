package utils;

public class Opcodes {
	public static final String CMSG_PING			= "CMSG_PING";
	public static final String CMSG_PONG			= "CMSG_PONG";
	public static final String CMSG_OKAY			= "CMSG_OKAY";
	public static final String CMSG_UPDATE 			= "CMSG_UPDATE";
	public static final String CMSG_DISCONNECT 		= "CMSG_DISCONNECT";
	
	public static final String SMSG_PING			= "SMSG_PING";
	public static final String SMSG_PONG			= "SMSG_PONG";
	public static final String SMSG_OKAY			= "SMSG_OKAY";
	public static final String SMSG_UPDATE 			= "SMSG_UPDATE";
	public static final String SMSG_AUTH_FAIL 		= "SMSG_AUTH_FAIL";
	public static final String SMSG_DISCONNECT 		= "SMSG_DISCONNECT";

	private static PacketMap packets;
	
	public static PacketMap loadPackets() {

		packets = new PacketMap();
		
		add(CMSG_PING, 			0x0001);
		add(CMSG_PONG, 			0x0002);
		add(CMSG_OKAY, 			0x0003);
		add(CMSG_UPDATE, 		0x0004);
		add(CMSG_DISCONNECT, 	0x0005);

		add(SMSG_PING, 			0x0001);
		add(SMSG_PONG, 			0x0002);
		add(SMSG_OKAY, 			0x0003);
		add(SMSG_UPDATE, 		0x0004);
		add(SMSG_AUTH_FAIL, 	0x0005);
		add(SMSG_DISCONNECT, 	0x0006);		

		return packets.clone();
	}
	
	private static void add(String name, Integer opcode) {
		packets.add(name, opcode.shortValue());
	}
}
