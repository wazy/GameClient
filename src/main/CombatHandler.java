package main;

public class CombatHandler implements Runnable {
	
	private boolean Collision = false;
	
	public void run () {
		System.out.println("Handling combat..");
		while (!Main.exitRequest) {
			try {
				// see if a collision occurred
				Collision = DetectCollisions();
				if (Collision) {
					System.out.println("Collision detected! Player being moved..");
					// health--;
				}
					Thread.sleep(1000);
			}
			catch (Exception e) {
				e.printStackTrace();
				System.out.println("\nFATAL: Combat handler thread is exiting..");
				Main.exitRequest = true;
				Main.threadCount.decrementAndGet(); // one less active thread
				return;
			}
		}
		System.out.println("SHUTDOWN: Combat handler thread is exiting..");
		Main.threadCount.decrementAndGet(); // one less active thread
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
			synchronized (Creature.CreatureList) {
				// empty list -- do nothing
				if (Creature.CreatureList.size() < 1) {
					return false;
				}
				
				int listPosition = Player.listPosition.get();
				Player player = Player.onlinePlayers.get(listPosition);
				
				// get client's player's position
				int x1 = player.getX();
				int y1 = player.getY();
				
				// detects collision -- player against creatures
				for (int i = 0; i < Creature.CreatureList.size(); i++) {
					int x2 = Creature.CreatureList.get(i).getX();
					int y2 = Creature.CreatureList.get(i).getY();
					
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
