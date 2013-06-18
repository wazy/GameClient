package main;
import java.io.*;
import java.net.*;
import java.util.List;

public class ServerConnection implements Runnable {
	
	private Socket connection;
	private ObjectInputStream ois;

	ServerConnection(Socket s, ObjectInputStream ois1) throws IOException {
		this.connection = s;
		this.ois = ois1;
	}
	
	public static String[] authenticate() {
		try {
			String instrBuffer[] = null;
			Socket connection = SocketHandler.fetchSocket();
			
			// null if fails to open connection
			if (connection == null) {
				System.exit(-1);
			}
			
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(connection.getOutputStream()));
			oos.flush();
			ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());

			// tell server we want to auth
			oos.writeObject("auth");
			oos.flush();

			int c = ois.readInt();

			if (c == 1) {
				System.out.println("Authenticated successfully!");
				
				// TODO: this needs to come from authentication part however NYI in client
				System.out.println("Sending userid now...");
				oos.writeInt(5);
				oos.flush();

				String option = (String) ois.readObject();
			
				// account id doesn't exist according to server or failure
				if (option.contentEquals("create")) {
					System.out.println("Please create an account first!");
					connection.close();
					oos.close();
					ois.close();
					return null;
				}
				else { // we have character info
					System.out.println("Success!");
					instrBuffer = option.split(",");
					System.out.println("Your character info is as follows:");
					System.out.println("ID = " + instrBuffer[0]);
					System.out.println("Name = " + instrBuffer[1]);
					System.out.println("X-coord = " + instrBuffer[2]);
					System.out.println("Y-coord = " + instrBuffer[3]);
					Player.setId(instrBuffer[0]);
				}
			}
			else {
				System.out.println("not authenticated");
				connection.close();
				oos.close();
				ois.close();
				return null;
			}
			// handle receiving player coordinate updates
			Runnable getPlayerCoordinates = new ServerConnection(connection, ois);
			Thread getPlayerCoordinatesThread = new Thread(getPlayerCoordinates);
			getPlayerCoordinatesThread.start();
			
			// handle sending coordinates
			Runnable sendPlayerCoordinates = new SendPlayerCoordinates();
			Thread sendPlayerCoordinatesThread = new Thread(sendPlayerCoordinates);
			sendPlayerCoordinatesThread.start();
			
			// handle monster coordinates
			Runnable handleMonsterCoordinates = new CreatureHandler();
			Thread handleMonsterCoordinatesThread = new Thread(handleMonsterCoordinates);
			handleMonsterCoordinatesThread.start();
			
			/*
			 * Add more threads in this location for future functionality.
			 * TODO: cleanup.. need better design here.
			 */
			
			return instrBuffer;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void run () {
		// probably not best place for this but
		// receive player coordinates here
		try {
			System.out.println("Receiving player coordinates...");
			while (true) {
				if (Main.exitRequest) {
					connection.shutdownInput();
					System.out.println("SHUTDOWN: Receiving player coordinates thread is exiting..");
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