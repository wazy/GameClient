import java.io.*;
import java.net.*;
import java.util.List;

public class ServerConnection implements Runnable {
	
	private Socket connection;
	private ObjectInputStream ois;

	ServerConnection(Socket s, ObjectInputStream ois2) throws IOException {
		this.connection = s;
		this.ois = ois2;
		//ois = new ObjectInputStream(connection.getInputStream());
	}
	
	public static String[] authenticate() {
		String host = "216.158.67.244";
		int port = 8149;
		
		//StringBuffer instr1 = new StringBuffer();
		String instrBuffer[] = null;
		
		try {
			// gets server address and attempts to establish a connection
			InetAddress address = InetAddress.getByName(host);
			Socket connection = new Socket(address, port);
			BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.flush();
			ObjectInputStream ois1 = new ObjectInputStream(connection.getInputStream());

			// tell server we want to auth
			oos.writeObject("auth");
			oos.flush();

			int c = ois1.readInt();

			if (c == 1) {
				System.out.println("Authenticated successfully!");
				
				// TODO: this needs to come from authentication part however NYI in client
				System.out.println("Sending userid now...");
				oos.writeInt(2);
				oos.flush();

				String option = (String) ois1.readObject();
			
				if (option.contentEquals("create")) {
					System.out.println("Please create an account first!");
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
				ois1.close();
				return null;
			}
			// handle receiving player updates
			Runnable runnable = new ServerConnection(connection, ois1);
			Thread thread = new Thread(runnable);
			thread.start();
			
			// handle sending coordinates
			Runnable runnable1 = new UpdateCoordinates();
			Thread thread1 = new Thread(runnable1);
			thread1.start();
			return instrBuffer;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void run () {
		try {
			int id;
			while (true) {
				if (Main.exitRequest) {
					connection.shutdownInput();
					ois.close();
					return;
				}
			
				// read and set position of player in list here
				id = ois.readInt();
				Player.listPosition = id;
				//System.out.println("You are position: " + Player.listPosition + " in the list.");
				
				// read all players and their positions
				Player.onlinePlayers = (List<Player>) ois.readObject();
				Thread.sleep(1000);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
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