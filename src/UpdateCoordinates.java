import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class UpdateCoordinates implements Runnable {
	public void run() {
		Socket connection = null;
		try {
			System.out.println("Updating coordinates");
			int id, x , y, listPosition;
			// gets server address and attempts to establish a connection
			InetAddress address = InetAddress.getByName("localhost");
			connection = new Socket(address, 8149);
			BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
			ObjectOutputStream outputStream = new ObjectOutputStream(bos);
			BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
			ObjectInputStream inputStream = new ObjectInputStream(bis);
			//ObjectOutputStream outputStream = new ObjectOutputStream(bos);
			
			// tell server we want to update player coords
			outputStream.writeChars("update" + (char) 13);
			outputStream.flush();
						
			int c = inputStream.readInt();
			// one is accepted connection from server
			if (c == 1) {
				while (true) {
					Thread.sleep(2000);
					id = Player.getId();
					listPosition = Player.listPosition;
					x = Player.onlinePlayers.get(listPosition).x;
					y = Player.onlinePlayers.get(listPosition).y;
					Player player = new Player(id, String.valueOf(listPosition), x, y);
					outputStream.writeObject(player);
					outputStream.flush();
				}
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				connection.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
