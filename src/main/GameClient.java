package main;
import java.util.concurrent.atomic.AtomicInteger;

import org.lwjgl.LWJGLException;

import display.LoginFrontEnd;

public class GameClient {
	public static volatile boolean exitRequest = false;
	// public static int threadCount = -1;
	public static AtomicInteger threadCount = new AtomicInteger(-1);

	// public synchronized void decreaseThread() {
	//	threadCount--;
	// }
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, LWJGLException {
		try {
			LoginFrontEnd.init();
			
			//packet.send(Packet.PacketEnum.valueOf("CONNECT"));
			
			
			// loop until ready to completely exit client
			// this won't spinlock due to volatile exitRequest
			while (!exitRequest || threadCount.get() != 0) {;}
			
			System.out.println("SHUTDOWN: Main thread is exiting..");
			System.out.println("SHUTDOWN COMPLETE: Client has stopped.");
			System.exit(0);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static boolean isExitRequested() {
		return exitRequest;
	}

	public static synchronized AtomicInteger getThreadCount() {
		return threadCount;
	}

	public static void setExitRequest(boolean exit) {
		exitRequest = exit;
	}

}
