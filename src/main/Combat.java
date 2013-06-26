package main;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Combat {
	public static void check() {

		// client still loading
		if (Player.onlinePlayers.size() < 1) {
			return;
		}

		Player player = Player.onlinePlayers.get(Player.listPosition.get());
		int x = player.getX();
		int y = player.getY();

		if (Keyboard.next()) { 	// buffered events
			if (Keyboard.isKeyDown(Keyboard.KEY_F)); { // pressed down?
				if (Keyboard.isKeyDown(Keyboard.KEY_F)) { 
					System.out.println("fire button is pressed");
				}
			}
		}
	}
}
