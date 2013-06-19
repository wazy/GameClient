package main;

public class ThreadHandler {
	public static void initAll() {
		System.out.println("Starting threads...");
		// handle receiving player coordinate updates
		Runnable getPlayerCoordinates = new UpdatePlayerCoordinates();
		Thread getPlayerCoordinatesThread = new Thread(getPlayerCoordinates);
		getPlayerCoordinatesThread.start();

		// handle sending coordinates
		Runnable sendPlayerCoordinates = new SendPlayerCoordinates();
		Thread sendPlayerCoordinatesThread = new Thread(sendPlayerCoordinates);
		sendPlayerCoordinatesThread.start();

		// handle monster coordinates
		Runnable handleMonsterCoordinates = new CreatureHandler();
		Thread handleMonsterCoordinatesThread = new Thread(handleMonsterCoordinates);
		handleMonsterCoordinatesThread.start();

		/*
		 * Add more threads in this location for future functionality.
		 * TODO: cleanup.. need better design here.
		 */
	}
}
