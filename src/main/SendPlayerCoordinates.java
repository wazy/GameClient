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
						System.out.println("SHUTDOWN: Update player coordinates thread is exiting..");
						Main.threadCount = Main.threadCount - 1; // one less active thread
						return;
					}
					
					// System.out.println("Sending coordinates to server..");
					id = Player.getId();
					listPosition = Player.listPosition;
					x = Player.onlinePlayers.get(listPosition).x;
					y = Player.onlinePlayers.get(listPosition).y;
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
			System.out.println("FATAL: Update player coordinates thread is exiting..");
			Main.exitRequest = true;
			Main.threadCount = Main.threadCount - 1; // one less active thread
			return;
			//e.printStackTrace();
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
