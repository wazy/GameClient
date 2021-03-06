package handlers;

import java.io.*;
import java.net.*;

import utils.BCrypt;

import entities.Player;

public class LoginHandler {
	public static boolean authenticate(String username, String password) {
		try {
			Socket connection = SocketHandler.fetchSocket();

			// null if fails to open connection
			if (connection == null) {
				System.exit(-1);
			}

			ObjectOutputStream oos = new ObjectOutputStream(
										new BufferedOutputStream(
											connection.getOutputStream()));

			ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());

			// client wants to authenticate, auth packet as follows
			// CMSG "auth", SMSG 1, CMSG username,
			// SMSG 1 or 0 (exists or doesn't), CMSG 1 (pong if user exists),
			// SMSG passwordHash, CMSG 1 or 0 (valid or not)
			// --> then server d/c's the client		

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

					// tell server we are validated and get playerID
					if (BCrypt.checkpw(password, passwordHash)) {
						oos.writeInt(1);
						oos.flush();
						
						Player.setPlayerID(ois.readInt());

						return true;
					}
					else {
						oos.writeInt(0); // not right password
						oos.flush();
						return false;
					}
				}
			}
			return false; // failure to authenticate
		}
		catch (Exception e) {
			System.out.println(password);
			e.printStackTrace();
			return false;
		}
	}
	public static boolean register(String username, String hashpw) {
		try {
			// concat hash and username together so that we only write once
			String regDetails = username + " " + hashpw;
			
			Socket connection = SocketHandler.fetchSocket();

			// null if fails to open connection
			if (connection == null) {
				System.exit(-1);
			}

			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(connection.getOutputStream()));
			oos.flush();
			ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());

			// client wants to register, reg packet as follows
			// CMSG "register", SMSG 1, CMSG regDetails,
			// SMSG 1 or 0 (valid or not) --> then server d/c's the client		

			// tell server we want to register
			oos.writeObject("register");
			oos.flush();

			int c = ois.readInt();

			if (c == 1) {
				oos.writeObject(regDetails); // send account details
				oos.flush();

				c = ois.readInt(); // 1 means success
				if (c == 1) {
					return true;
				}
			}
			return false; // failed to register
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}