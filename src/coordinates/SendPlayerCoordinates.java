package coordinates;
import handlers.SocketHandler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.GameClient;



import entities.Player;


public class SendPlayerCoordinates implements Runnable {

	final static Logger logger = LoggerFactory.getLogger(SendPlayerCoordinates.class);

	public void run() {
		Socket connection = null;
		int x , y, pos = -1;
		
		try {
			logger.info("Sending player coordinates...");

			if ((connection = SocketHandler.fetchSocket()) == null) {
				logger.error("Unable to open a socket.");
				System.exit(-1);
			}

			// init the streams here for r/w
			ObjectOutputStream oos = new ObjectOutputStream(
										new BufferedOutputStream(
											connection.getOutputStream()));

			ObjectInputStream ois = new ObjectInputStream(
												new BufferedInputStream(
													connection.getInputStream()));
			
			// tell server we want to update player coordinates
			oos.writeObject("update");
			oos.writeInt(Player.getPlayerID());
			oos.flush();
						
			int c = ois.readInt();

			if (c == 1) { // accepted connection from server

				while (Player.getOnlinePlayers().size() < 1) {;}

				while (true) {
					if (GameClient.isExitRequested()) { // check if a reason exists to continue
						connection.close();
						logger.info("SHUTDOWN: Sending player coordinates thread is exiting..");
						GameClient.getThreadCount().decrementAndGet(); // one less active thread
						return;
					}

					while (pos < 0 || pos >= Player.getOnlinePlayers().size()) {
						pos = Player.getListPosition().get();
					}

					x = Player.getOnlinePlayers().get(pos).getX();
					y = Player.getOnlinePlayers().get(pos).getY();

					//System.out.println(pos + ", " + x + ", " + y);

					oos.write(pos);
					oos.writeShort(x);
					oos.writeShort(y);
					oos.flush();

					Thread.sleep(200);

					// System.out.println("Coordinates sent = " + x + "  " + y);
				}
			}
			
		}
		// socket exception thrown when connection terminates
		// this will terminate entire client in a domino fashion
		catch (Exception e) {
			logger.error("FATAL: Sending player coordinates thread is exiting..");
			GameClient.setExitRequest(true);
			GameClient.getThreadCount().decrementAndGet(); // one less active thread
			e.printStackTrace();
			return;
		} 
		finally {
			try {
				connection.close();
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
	}

}
