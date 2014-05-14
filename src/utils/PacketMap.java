package utils;

import java.util.HashMap;

public class PacketMap {
	public HashMap<String, Short> packets = new HashMap<String, Short>();

	public synchronized void add(String key, Short value) {
		packets.put(key, value);
	}

	public synchronized Short getOpcodeValue(String key) {
		return packets.get(key);
	}

	public synchronized PacketMap clone() {
		return (PacketMap) packets.clone();
	}

}
