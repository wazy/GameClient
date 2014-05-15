package handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.GameClient;
import coordinates.AcceptPlayerCoordinates;
import coordinates.SendPlayerCoordinates;

public class ThreadHandler {

	private final static int NUMBER_OF_THREADS = 2;
	final static Logger logger = LoggerFactory.getLogger(ThreadHandler.class);

	public static void initAll() {
		logger.info("Starting threads...");
		
		/*
		 * 
		 * Add more threads in this location for future functionality.
		 * 
		 */
		try {
			// handle receiving player coordinate updates
			Runnable getPlayerCoordinates = new AcceptPlayerCoordinates();
			Thread getPlayerCoordinatesThread = new Thread(getPlayerCoordinates);
			getPlayerCoordinatesThread.start();
	
			// handle sending coordinates
			Runnable sendPlayerCoordinates = new SendPlayerCoordinates();
			Thread sendPlayerCoordinatesThread = new Thread(sendPlayerCoordinates);
			sendPlayerCoordinatesThread.start();
	
			// handle creature coordinates
//			Runnable handleCreatureCoordinates = new CreatureHandler();
//			Thread handleCreatureCoordinatesThread = new Thread(handleCreatureCoordinates);
//			handleCreatureCoordinatesThread.start();
			
			// handle combat updates.. mostly NYI
//			Runnable combatHandler = new CombatHandler();
//			Thread combatHandlerThread = new Thread(combatHandler);
//			combatHandlerThread.start();
	
			// handle player spells
//			Runnable spellHandler = new SpellHandler();
//			Thread spellHandlerThread = new Thread(spellHandler);
//			spellHandlerThread.start();
	
			// update when modifying threads
			GameClient.getThreadCount().set(NUMBER_OF_THREADS);
		}
		catch (Exception e) {
			logger.error("FATAL: Unable to start all threads");
			System.exit(-1);
		}
	}
}
