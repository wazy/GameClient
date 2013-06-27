package main;
import java.io.*;
import java.net.*;
import java.util.List;

/* accepts / updates player list from server */
public class AcceptPlayerCoordinates implements Runnable {
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

			// tell server we want it to spc (send player coordinates)
			oos.writeObject("spc");
			oos.flush();

			int c = ois.readInt();

			if (c == 1) {
				System.out.println("Accepting player coordinates...");
				while (true) {
					if (Main.exitRequest) {
						System.out.println("SHUTDOWN: Accepting player coordinates thread is exiting..");
						Main.threadCount.decrementAndGet(); // one less active thread
						ois.close();
						return;
					}

					// read and set position of player in list here
					int id = ois.readInt();
					Player.listPosition.set(id);
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
			System.out.println("\nFATAL: Accepting player coordinates thread is exiting..");
			Main.exitRequest = true;
			Main.threadCount.decrementAndGet(); // one less active thread
			// e.printStackTrace();
			return;
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