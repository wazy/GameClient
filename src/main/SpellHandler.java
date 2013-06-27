package main;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class SpellHandler implements Runnable{
	public void run() {
		System.out.println("Handling spells..");

		// client still loading
		while (Player.onlinePlayers.size() < 1) {;}

		// Player player = Player.onlinePlayers.get(Player.listPosition.get());
		// int x = player.getX();
		// int y = player.getY();
		while (!Main.exitRequest) {
			try {
				while (Keyboard.next()) { 	// buffered keyboard events
					if (Keyboard.getEventKey() == Keyboard.KEY_F) { // key F was triggered
						if (Keyboard.getEventKeyState()) { // F was pressed
							int mouse_X = Mouse.getX();
							int mouse_Y = Mouse.getY();

							// useful for detecting hit/miss airborne shots later
							System.out.printf("You fired toward position %d,%d\n", mouse_X, mouse_Y);
							
							GameDisplay.drawProjectile = true;
							Spell.spellList.add(new Spell(1,"",mouse_X,mouse_Y));
							
							synchronized (Creature.creatureList) {
								for (Creature creature : Creature.creatureList) { // necessary.. probably need more efficient
									if (creature.inBounds(mouse_X, mouse_Y)) { 
										System.out.println("You hit " + creature.getName());
										break;
									}
								}
							}
						}
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				System.out.println("\nFATAL: spell handler thread is exiting..");
				Main.exitRequest = true;
				Main.threadCount.decrementAndGet(); // one less active thread
				return;
			}
		}
		System.out.println("SHUTDOWN: Spell handler thread is exiting..");
		Main.threadCount.decrementAndGet(); // one less active thread
		return;
	}
}
