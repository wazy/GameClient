package coordinates;
import handlers.SocketHandler;

import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import main.GameClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entities.Player;

import static handlers.ResourceHandler.getTexture;

/* accepts / updates player list from server */
public class AcceptPlayerCoordinates implements Runnable {

	final static Logger logger = LoggerFactory.getLogger(AcceptPlayerCoordinates.class);

	@SuppressWarnings("unchecked")
	public void run () {
		// need to do path prediction on player coordinates
		Socket connection = null;
		try {
			if ((connection = SocketHandler.fetchSocket()) == null) {
				logger.error("Unable to open a socket!");
				System.exit(-1);
			}

			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(connection.getOutputStream()));
			ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());
			
			// tell server we want it to spc (send player coordinates)
			oos.writeObject("spc");
			oos.flush();

			if (ois.readInt() == 1) {

				logger.info("Accepting player coordinates...");

				oos.writeInt(Player.getPlayerID());
				oos.flush();
				
				Player.setOnlinePlayers((List<Player>) ois.readObject());

				while (true) {
					if (GameClient.isExitRequested()) {
						logger.info("SHUTDOWN: Accepting player coordinates thread is exiting..");
						GameClient.getThreadCount().decrementAndGet(); // one less active thread
						ois.close();
						return;
					}

					// get "actual" size of player list
					int n = ois.read();
					int m = Player.getOnlinePlayers().size();

					// w: spc | r: 1 | w: ID | r: List | while true { r: int | r: player or position }

					int disconnect = ois.read();

					if (disconnect == 1) { // remove a player
						disconnect = ois.read();
						if (disconnect < 0)
							continue;

						synchronized (Player.getOnlinePlayers()) {
							Player.getOnlinePlayers().remove(disconnect);
						}
					}
					else {
						if (n >= m) { // read all players and their positions
							for (int i = 0; i < n; i++) {
								int type = ois.read();
	
								if (type == 0) { // receiving player updates
									Player player = (Player) ois.readObject();
									player.setTexture(getTexture(player.getTextureName()));
									if (i >= m)
										Player.getOnlinePlayers().add(player);
									else {
										Player.getOnlinePlayers().set(i, player);
									}
								}
								else { // set our position in list
									int pos = ois.read();
									if (i < m && pos >= 0 && pos != Player.getListPosition().get()) {
										Player player = Player.getOnlinePlayers().get(pos);
										player.setTexture(getTexture(player.getTextureName()));
										Player.setListPosition(pos);
									}
								}
							}
						}
					}
					Thread.sleep(200);
				}
			}
			else {
				return;
			}
		}
		// will cause client to shutdown on fatal error
		catch (Exception e) {
			logger.error("\nFATAL: Accepting player coordinates thread is exiting..");
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