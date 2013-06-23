package main;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendPlayerCoordinates implements Runnable {
	public void run() {
		Socket connection = null;
		int id, x , y, listPosition;
		try {
			System.out.println("Sending player coordinates...");
			// gets server address and attempts to establish a connection
			connection = SocketHandler.fetchSocket();
			if (connection == null) {
				System.exit(-1);
			}
			// init the streams here for r/w
			ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(connection.getOutputStream()));
			outputStream.flush();
			
			ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(connection.getInputStream()));
			
			// tell server we want to update player coordinates
			outputStream.writeObject("update");
			outputStream.flush();
						
			int c = inputStream.readInt();
			// one means accepted connection from server
			if (c == 1) {
				while (true) {
					Thread.sleep(100); // adjust this
					
					if (Main.exitRequest) { // check if a reason exists to continue
						connection.close();
						System.out.println("SHUTDOWN: Sending player coordinates thread is exiting..");
						Main.threadCount.decrementAndGet(); // one less active thread
						return;
					}
					
					// System.out.println("Sending coordinates to server..");

					while (Player.onlinePlayers.size() < 1) {;}

					listPosition = Player.listPosition.get();
					
					id = Player.onlinePlayers.get(listPosition).getId();
					x = Player.onlinePlayers.get(listPosition).getX();
					y = Player.onlinePlayers.get(listPosition).getY();
					
					// where client's player is in the array is sent as "name" -- hack
					Player player = new Player(id, String.valueOf(listPosition), x, y);
					outputStream.writeObject(player);
					outputStream.flush();
					// System.out.println("Coordinates sent = " + x + "  " + y);
				}
			}
			
		}
		// socket exception thrown when connection terminates
		// this will terminate entire client in a domino fashion
		catch (Exception e) {
			//e.printStackTrace();
			System.out.println("FATAL: Sending player coordinates thread is exiting..");
			Main.exitRequest = true;
			Main.threadCount.decrementAndGet(); // one less active thread
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
