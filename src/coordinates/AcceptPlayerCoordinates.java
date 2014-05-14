package coordinates;
import handlers.SocketHandler;

import java.io.*;
import java.net.*;
import java.util.List;

import main.GameClient;



import entities.Player;


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
			oos.flush();

			int c = ois.readInt();

			if (c == 1) {

				System.out.println("Accepting player coordinates...");

				oos.writeInt(Player.getPlayerID());
				oos.flush();
				
				Player.setOnlinePlayers((List<Player>) ois.readObject());

				while (true) {
					if (GameClient.isExitRequested()) {
						System.out.println("SHUTDOWN: Accepting player coordinates thread is exiting..");
						GameClient.getThreadCount().decrementAndGet(); // one less active thread
						ois.close();
						return;
					}

					// get "actual" size of player list
					int n = ois.read();

					// w: spc | r: 1 | w: ID | r: List | while true { r: n | r: 9 || r: 1 }

					// read all players and their positions
					for (int i = 0; i < n; i++) {
						int playerUpdatePacket = ois.readInt();
						System.out.println("PUP: " + playerUpdatePacket);

						String packet = String.valueOf(playerUpdatePacket); 
						int size = packet.length();

						// decode packet (other players)
						if (size >= 7) {
							while (size <= 9) { 
								size = 0 + size;
							}
							System.out.println(size);
							String[] arr = packet.split(("(?<=\\G...)"));
							
							int ID = Integer.parseInt(arr[0]);
							int X = Integer.parseInt(arr[1]);
							int Y = Integer.parseInt(arr[2]);

							if (i >= Player.getOnlinePlayers().size()) {
								System.out.println("Player being added: " + playerUpdatePacket);
								Player player = new Player(ID, "test", X, Y);
								Player.getOnlinePlayers().add(player);	
					
							}
							else {
								Player player = Player.getOnlinePlayers().get(i);
								System.out.println("Player being moved: " + playerUpdatePacket);
								player.setX(X);
								player.setY(Y);
							}
						}
						else { // set our position in list
							if (i < Player.getOnlinePlayers().size())
								Player.setListPosition(playerUpdatePacket);
							
						}
					}

					Thread.sleep(2000);
				}
			}
			else {
				return;
			}
		}
		// will cause client to shutdown on fatal error
		catch (Exception e) {
			System.out.println("\nFATAL: Accepting player coordinates thread is exiting..");
			GameClient.setExitRequest(true);
			GameClient.getThreadCount().decrementAndGet(); // one less active thread
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