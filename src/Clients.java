import java.io.*;
import java.net.*;
public class Clients {
	public static void main(String[] args) {
		String host = "localhost";
		int port = 8149;
		
		StringBuffer instr = new StringBuffer();
		StringBuffer instr1 = new StringBuffer();

		try {
			// gets server address and attempts to establish a connection
			InetAddress address = InetAddress.getByName(host);
			Socket connection = new Socket(address, port);
			BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
			OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");
		
			// tell server we want to auth
			osw.write("auth" + (char) 13);
			osw.flush();
			
			BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
			InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");
			int c;
			while((c = isr.read()) != 13)
				instr.append((char) c);
			
			if (Integer.parseInt(instr.toString()) == 1) {
				System.out.println("Authenticated successfully!");
				
				// TODO: this needs to come from authentication part however NYI in client
				System.out.println("Sending userid now...");
				//Random rand = new Random();
				//int Id = rand.nextInt(6-1+1)+1;
				osw.write("2" + (char) 13);
				osw.flush();
				
				while ((c = isr.read()) != 13) {
					instr1.append((char) c);
				}
				if (instr1.toString().contentEquals("create")) {
					System.out.println("Please create an account first!");
				}
				else { // we have character info
					String instrBuffer[] = instr1.toString().split(",");
					System.out.println("Your character info is as follows:");
					System.out.println("ID = " + instrBuffer[0]);
					System.out.println("Name = " + instrBuffer[1]);
					System.out.println("X-coord = " + instrBuffer[2]);
					System.out.println("Y-coord = " + instrBuffer[3]);
				}
			}
			else {
				System.out.println("not authenticated");
			}
			connection.close();
		}
		catch (IOException f){
			System.out.println("IOException: " + f);
		}
		catch (Exception g){
			System.out.println("Exception: " + g);
		}
	}
}