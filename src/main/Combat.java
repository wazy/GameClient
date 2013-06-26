package main;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Combat {
	public static void check() {

		// client still loading
		if (Player.onlinePlayers.size() < 1) {
			return;
		}

		// Player player = Player.onlinePlayers.get(Player.listPosition.get());
		// int x = player.getX();
		// int y = player.getY();

		while (Keyboard.next()) { 	// buffered keyboard events
			if (Keyboard.getEventKey() == Keyboard.KEY_F) { // key F was triggered
				if (Keyboard.getEventKeyState()) { // F was pressed
					int mouse_X = Mouse.getX();
					int mouse_Y = Mouse.getY();

					// useful for detecting hit/miss airborne shots later
					System.out.printf("You fired toward position %d,%d\n", mouse_X, mouse_Y);

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
}
