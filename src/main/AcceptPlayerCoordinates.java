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
			ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());

			// tell server we want it to spc (send player coordinates)
			oos.writeObject("spc");
			oos.writeInt(Player.playerID);
			oos.flush();

			int c = ois.readInt();

			if (c == 1) {

				System.out.println("Accepting player coordinates...");

				Player.onlinePlayers = (List<Player>) ois.readObject();
				
				while (true) {
					if (Main.exitRequest) {
						System.out.println("SHUTDOWN: Accepting player coordinates thread is exiting..");
						Main.threadCount.decrementAndGet(); // one less active thread
						ois.close();
						return;
					}

					int pos = Player.listPosition.get();
					
					// write position in list so server can cleanup on d/c 
					oos.writeInt(pos);
					oos.flush();

					// read all players and their positions
					for (int i = 0; i < ois.read(); i++) {
						if (i != pos) {
							Player play = Player.onlinePlayers.get(i);
							System.out.println(play.getID());
							int ID = ois.read();
							if (play.getID() == ID) { // need to add contains check
								play.setX(ois.read());
								play.setY(ois.read());
							}
							else { // add new player to our list
								play = new Player(ID, "test", ois.read(), ois.read());
								Player.onlinePlayers.add(play);
							}
						}
					}

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
			e.printStackTrace();
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