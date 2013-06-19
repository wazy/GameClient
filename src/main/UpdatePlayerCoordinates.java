package main;
import java.io.*;
import java.net.*;
import java.util.List;

public class UpdatePlayerCoordinates implements Runnable {
	@SuppressWarnings("unchecked")
	public void run () {
		// probably not best place for this but
		// receive player coordinates here
		Socket connection = null;
		try {
			connection = SocketHandler.fetchSocket();

			// null if fails to open connection
			if (connection == null) {
				System.exit(-1);
			}

			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(connection.getOutputStream()));
			oos.flush();
			ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());

			// tell server we want to auth
			oos.writeObject("spc");
			oos.flush();

			int c = ois.readInt();

			if (c == 1) {
				System.out.println("Receiving player coordinates...");
				while (true) {
					if (Main.exitRequest) {
						System.out.println("SHUTDOWN: Receiving player coordinates thread is exiting..");
						Main.threadCount = Main.threadCount - 1; // one less active thread
						ois.close();
						return;
					}

					// read and set position of player in list here
					int id = ois.readInt();
					Player.listPosition = id;
					//System.out.println("You are position: " + Player.listPosition + " in the list.");

					// read all players and their positions
					Player.onlinePlayers = (List<Player>) ois.readObject();
					Thread.sleep(500);
				}
			}
			else {
				return;
			}
		}
		// will cause client to shutdown on fatal error
		catch (Exception e) {
			System.out.println("\nFATAL: Receiving player coordinates thread is exiting..");
			Main.exitRequest = true;
			//e.printStackTrace();
		}
		finally {
			try {
				connection.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}