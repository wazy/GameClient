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

		if (Mouse.next()) { 	// buffered events
			if (Keyboard.isKeyDown(Keyboard.KEY_F) && Mouse.getEventButtonState()){ // key F pressed down && click mouse?
				int mouse_X = Mouse.getX();
				int mouse_Y = Mouse.getY();
				
				System.out.printf("you fired toward position %d,%d\n",Mouse.getX(),Mouse.getY());// useful for detecting hit/miss airborne shots later
				for (Creature i : Creature.CreatureList) { //loop through creatures?? not efficient WIP
					if (i.inBounds(mouse_X, mouse_Y)) { 
						System.out.printf("you shot at %s\n", i.getName());
						break;
					}
				}
			}
		}
	}
}
