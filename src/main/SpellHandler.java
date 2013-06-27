package main;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class SpellHandler implements Runnable{
	public void run() {
		System.out.println("Handling spells..");

		// client still loading
		while (Player.onlinePlayers.size() < 1) {;}

		while (!Main.exitRequest) {
			try {
				while (Keyboard.next()) { 	// buffered keyboard events
					if (Keyboard.getEventKey() == Keyboard.KEY_F) { // key F was triggered
						if (Keyboard.getEventKeyState()) { // F was pressed
							int mouse_X = Mouse.getX();
							int mouse_Y = Mouse.getY();

							// useful for detecting hit/miss airborne shots later
							System.out.printf("You fired toward position %d,%d\n", mouse_X, mouse_Y);

							Player player = Player.onlinePlayers.get(Player.listPosition.get());
							int x1 = player.getX();
							int y1 = player.getY();

							// get hypotenuse
							//	double hypote = Math.hypot(x1 - mouse_X, y1 - mouse_Y);
							double slope = (mouse_Y - y1) / (mouse_X - x1);

							// generate 20 spell objects to represent object path
							//	int results = (int) hypote/20;

							//	System.out.println("Hypotenuse is " + hypote);
							//	System.out.println("Increment value is " + results);

							// not ready to render yet
							while (GameDisplay.drawProjectile) {;}

							// lock it while we add here
							synchronized (Spell.spellMap) {
								int k = 0;

								Spell.spellMap.clear();

								int width = mouse_X - x1;
								for (int i = 0; i < width; i += 20) {
									if (k < 20) {
										float x = x1 + i;
										double y = y1 + (slope * i);
										Spell.spellMap.put(k++, new Spell(1, "", (int)x, (int)y));
									}
								}
								// do our calculations here with incrementing position
								// for (int i = results; i <= hypote; i += results) {
								//	if (k < 20) {
								//		double y = slope * (x1+i) + y1;
								//		Spell.spellMap.put(k++, new Spell(1, "", x1+i, (int)y));
								//	}
								//}
							}
							GameDisplay.drawProjectile = true;

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
				// e.printStackTrace();
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
