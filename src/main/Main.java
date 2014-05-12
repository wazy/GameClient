package main;
import java.util.concurrent.atomic.AtomicInteger;

import org.lwjgl.LWJGLException;

public class Main {
	public static volatile boolean exitRequest = false;
	// public static int threadCount = -1;
	public static AtomicInteger threadCount = new AtomicInteger(-1);

	// public synchronized void decreaseThread() {
	//	threadCount--;
	// }
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, LWJGLException {
		try {
			LoginFrontEnd.init();
			
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

	public static AtomicInteger getThreadCount() {
		return threadCount;
	}

}
