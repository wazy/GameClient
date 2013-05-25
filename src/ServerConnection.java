import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServerConnection implements Runnable {
	
	private Socket connection;
	private ObjectInputStream ois = null;

	ServerConnection(Socket s, ObjectInputStream input) {
		this.connection = s;
		this.ois = input;
	}
	
	public static String[] authenticate() {
		String host = "localhost";
		int port = 8149;
		
		StringBuffer instr1 = new StringBuffer();
		String instrBuffer[] = null;
		
		try {
			// gets server address and attempts to establish a connection
			InetAddress address = InetAddress.getByName(host);
			Socket connection = new Socket(address, port);
			BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.flush();
		
			// tell server we want to auth
			oos.writeChars("auth" + (char) 13);
			oos.flush();
			
			BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
			ObjectInputStream ois = new ObjectInputStream(bis);
			int c = ois.readInt();
			
			if (c == 1) {
				System.out.println("Authenticated successfully!");
				
				// TODO: this needs to come from authentication part however NYI in client
				System.out.println("Sending userid now...");
				//Random rand = new Random();
				//int Id = rand.nextInt(6-1+1)+1;
				oos.writeInt(1);
				oos.flush();

				while ((c = ois.readChar()) != 13) {
					instr1.append((char) c);
				}
				if (instr1.toString().contentEquals("create")) {
					System.out.println("Please create an account first!");
				}
				else { // we have character info
					System.out.println("Success!");
					instrBuffer = instr1.toString().split(",");
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
			// handle receiving player updates
			Runnable runnable = new ServerConnection(connection, ois);
			Thread thread = new Thread(runnable);
			thread.start();
			
			// handle sending coordinates
			Runnable runnable1 = new UpdateCoordinates();
			Thread thread1 = new Thread(runnable1);
			thread1.start();
			// oos.close();
			// ois.close();
			return instrBuffer;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/*
	private static void receiveOnlinePlayers() {
		//ObjectInputStream inputStream = new ObjectInputStream();
		//outputStream.(Player.onlinePlayers);
	}
	*/
	
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
				System.out.println("You are position: " + Player.listPosition + " in the list.");
				
				// read all players and their positions
				Player.onlinePlayers = (ArrayList<Player>) ois.readObject();
				Thread.sleep(3000);
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