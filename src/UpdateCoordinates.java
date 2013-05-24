import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class UpdateCoordinates implements Runnable {
	public void run() {
		try {
			System.out.println("Updating coordinates");
			int id, x , y, listPosition;
			// gets server address and attempts to establish a connection
			InetAddress address = InetAddress.getByName("localhost");
			Socket connection = new Socket(address, 8149);
			BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
			OutputStreamWriter osw = new OutputStreamWriter(bos);
			BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
			InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");
			//ObjectOutputStream outputStream = new ObjectOutputStream(bos);
			
			// tell server we want to update player coords
			osw.write("update" + (char) 13);
			osw.flush();
			osw.close();
			
			BufferedOutputStream bos1 = new BufferedOutputStream(connection.getOutputStream());
			ObjectOutputStream outputStream = new ObjectOutputStream(bos1);
			
			int c = isr.read();
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
	}

}
