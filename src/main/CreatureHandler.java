package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class CreatureHandler implements Runnable {
	@SuppressWarnings("unchecked")
	public void run() {
		Socket connection = null;
		try {
			System.out.println("Updating creatures...");
			// gets server address and attempts to establish a connection
			connection = SocketHandler.fetchSocket();
			if (connection == null) {
				System.exit(-1);
			}
			// init the streams here for r/w
			ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(connection.getOutputStream()));
			outputStream.flush();
			
			ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(connection.getInputStream()));
			
			// tell server we want to update creature coordinates
			outputStream.writeObject("monster");
			outputStream.flush();
						
			int c = inputStream.readInt();
			// one means accepted connection from server
			if (c == 1) {
				while (true) {
					Thread.sleep(1000); // adjust this
					if (Main.exitRequest) { // check if a reason exists to continue
						connection.close();
						System.out.println("SHUTDOWN: Creature thread is exiting..");
						Main.threadCount.decrementAndGet(); // one less active thread
						return;
					}
					Creature.creatureList = (List<Creature>) inputStream.readObject();
					// System.out.println(Creature.CreatureList.size());
				}
			}
			
		}
		// socket exception / EOF exception thrown when connection terminates (silence error)
		// this will terminate entire client in a domino fashion
		catch (Exception e) {
			System.out.println("\nFATAL: Creature thread is exiting..");
			Main.exitRequest = true;
			Main.threadCount.decrementAndGet(); // one less active thread
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
