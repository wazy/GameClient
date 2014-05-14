package handlers;

import main.GameClient;
import entities.Creature;
import entities.Player;

public class CombatHandler implements Runnable {

	public void run () {
		System.out.println("Handling combat..");
		while (!GameClient.exitRequest) {
			try {
				// see if a collision occurred
				if (DetectCollisions()) {
					System.out.println("Collision detected! Player being moved..");

					// some template code for further development
					if (--Player.playerHealth > 0 ) {
						System.out.println("Health is now: " + Player.playerHealth);
					}
					else {
						System.out.println("\nPlayer has succumb to darkness.. farewell.\n");
						GameClient.exitRequest = true;
					}
				}
				Thread.sleep(1000);
			}
			catch (Exception e) {
				e.printStackTrace();
				System.out.println("\nFATAL: Combat handler thread is exiting..");
				GameClient.exitRequest = true;
				GameClient.threadCount.decrementAndGet(); // one less active thread
				return;
			}
		}
		System.out.println("SHUTDOWN: Combat handler thread is exiting..");
		GameClient.threadCount.decrementAndGet(); // one less active thread
		return;
	}

	/* this NEEDS to be synchronized */
	public boolean DetectCollisions() {
		// not sure if Player.listPosition needs to be synchronized...
		synchronized (Player.onlinePlayers) {
			// empty list -- do nothing
			if (Player.onlinePlayers.size() < 1) {
				return false;
			}
			synchronized (Creature.creatureList) {
				// empty list -- do nothing
				if (Creature.creatureList.size() < 1) {
					return false;
				}

				int listPosition = Player.getListPosition().get();
				
				if (Player.getOnlinePlayers().isEmpty() ||
						listPosition <= Player.getOnlinePlayers().size() ||
						listPosition >= Player.getOnlinePlayers().size()) {

					return false;
				}
				
				System.out.println(Player.getOnlinePlayers().size());
				
				Player player = Player.onlinePlayers.get(listPosition);

				// get client's player's position
				int x1 = player.getX();
				int y1 = player.getY();

				// detects collision -- player against creatures
				for (int i = 0; i < Creature.creatureList.size(); i++) {
					Creature creature = Creature.creatureList.get(i);
					int x2 = creature.getX();
					int y2 = creature.getY();

					// creature's coordinates plus a small buffer for movement momentum, etc
					if (((x1 < x2 + 65) &&  (x1 > x2 - 65)) && ((y1 < y2 + 65) && (y1 > y2 - 65))) {

						System.out.println(x1 + " " + x2);
						System.out.println(y1 + " " + y2);

						// just resetting player's coordinates as proof of concept
						player.updateXY(-5, -5);

						// collision happened
						return true;
					}
				}
				// no collision
				return false;
			}
		}
	}
}
