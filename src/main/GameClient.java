package main;
import java.util.concurrent.atomic.AtomicInteger;

import org.lwjgl.LWJGLException;

import display.LoginFrontEnd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameClient {
	public static volatile boolean exitRequest = false;
	// public static int threadCount = -1;
	public static AtomicInteger threadCount = new AtomicInteger(-1);
	
	final static Logger logger = LoggerFactory.getLogger(GameClient.class);
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, LWJGLException {
		try {
			LoginFrontEnd.init();
			
			logger.info("Front end initialized");
			
			// loop until ready to completely exit client
			// this won't spinlock due to volatile exitRequest
			while (!exitRequest || threadCount.get() != 0) {;}
			
			logger.info("SHUTDOWN: Main thread is exiting..\n");
			logger.info("SHUTDOWN COMPLETE: Client has stopped.");
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
