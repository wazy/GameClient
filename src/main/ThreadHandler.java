package main;

public class ThreadHandler {
	public static void initAll() {
		System.out.println("Starting threads...");
		
		/*
		 * 
		 * Add more threads in this location for future functionality.
		 * 
		 */
		
		// handle receiving player coordinate updates
		Runnable getPlayerCoordinates = new AcceptPlayerCoordinates();
		Thread getPlayerCoordinatesThread = new Thread(getPlayerCoordinates);
		getPlayerCoordinatesThread.start();

		// handle sending coordinates
		Runnable sendPlayerCoordinates = new SendPlayerCoordinates();
		Thread sendPlayerCoordinatesThread = new Thread(sendPlayerCoordinates);
		sendPlayerCoordinatesThread.start();

		// handle creature coordinates
//		Runnable handleCreatureCoordinates = new CreatureHandler();
//		Thread handleCreatureCoordinatesThread = new Thread(handleCreatureCoordinates);
//		handleCreatureCoordinatesThread.start();
		
		// handle combat updates.. mostly NYI
//		Runnable combatHandler = new CombatHandler();
//		Thread combatHandlerThread = new Thread(combatHandler);
//		combatHandlerThread.start();

		// handle player spells
//		Runnable spellHandler = new SpellHandler();
//		Thread spellHandlerThread = new Thread(spellHandler);
//		spellHandlerThread.start();

		// update when modifying threads
		GameClient.threadCount.set(5);
	}
}
