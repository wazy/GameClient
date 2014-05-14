package coordinates;
import handlers.SocketHandler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import main.GameClient;



import entities.Player;


public class SendPlayerCoordinates implements Runnable {
	public void run() {
		Socket connection = null;
		int x , y, pos = -1;
		
		try {
			System.out.println("Sending player coordinates...");
			// gets server address and attempts to establish a connection
			connection = SocketHandler.fetchSocket();
			if (connection == null) {
				System.exit(-1);
			}
			// init the streams here for r/w
			ObjectOutputStream oos = new ObjectOutputStream(
										new BufferedOutputStream(
											connection.getOutputStream()));

			ObjectInputStream ois = new ObjectInputStream(
												new BufferedInputStream(
													connection.getInputStream()));
			
			// tell server we wadnt to update player coordinates
			oos.writeObject("update");
			oos.writeInt(Player.playerID);
			oos.flush();
						
			int c = ois.readInt();

			if (c == 1) { // accepted connection from server

				while (Player.onlinePlayers.size() < 1) {;}

				while (true) {
					Thread.sleep(100); // adjust this

					if (GameClient.exitRequest) { // check if a reason exists to continue
						connection.close();
						System.out.println("SHUTDOWN: Sending player coordinates thread is exiting..");
						GameClient.threadCount.decrementAndGet(); // one less active thread
						return;
					}

					pos = Player.getListPosition().get();

					while (pos < 0) {
						pos = Player.getListPosition().get();
					}

					x = Player.onlinePlayers.get(pos).getX();
					y = Player.onlinePlayers.get(pos).getY();

					oos.write(pos);
					oos.write(x);
					oos.write(y);
					oos.flush();
					// System.out.println("Coordinates sent = " + x + "  " + y);
				}
			}
			
		}
		// socket exception thrown when connection terminates
		// this will terminate entire client in a domino fashion
		catch (Exception e) {
			//e.printStackTrace();
			System.out.println("FATAL: Sending player coordinates thread is exiting..");
			GameClient.exitRequest = true;
			GameClient.threadCount.decrementAndGet(); // one less active thread
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
