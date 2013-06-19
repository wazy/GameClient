package main;
import java.io.*;
import java.net.*;

public class LoginHandler {
	public static String authenticate(String username) {
		try {
			Socket connection = SocketHandler.fetchSocket();

			// null if fails to open connection
			if (connection == null) {
				System.exit(-1);
			}

			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(connection.getOutputStream()));
			oos.flush();
			ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());

			// client wants to authenticate, auth packet as follows
			// CMSG "auth", SMSG 1, CMSG username,
			// SMSG 1 or 0 (exists or doesn't), CMSG 1 (pong if user exists),
			// SMSG passwordHash --> then server d/c's the client		

			// tell server we want to auth
			oos.writeObject("auth");
			oos.flush();

			int c = ois.readInt();

			if (c == 1) {
				oos.writeObject(username);
				oos.flush();

				c = ois.readInt(); // 1 means user exists and hash is coming across network after our pong
				if (c == 1) {
					oos.writeInt(1); // pong
					oos.flush();

					String passwordHash = (String) ois.readObject(); // hash of password for comparison
					return passwordHash;
				}
			}
			return null; // failure to authenticate
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
	//if ((playerInfo = ServerConnection.authenticate()) != null) { //successful login
	// add client player -- {id, name, x, y}
	//		Player.onlinePlayers.add(new Player(Integer.parseInt(playerInfo[0].trim()), playerInfo[1].trim(),
	//			Integer.parseInt(playerInfo[2].trim()), Integer.parseInt(playerInfo[3].trim())));
	//	States.setState(1);
