import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Movement {
	
	public static void check() {
		
		// just testing .... 
		if (Mouse.next()) { 	// buffered events
			if (Mouse.getEventButtonState()) { // pressed down?
				if (Player.onlinePlayers.get(0).inBounds(Mouse.getX(), Mouse.getY())) {
					SimpleText.drawString("YOU CLICKED BOX 0", 500, 100);
				}
			}
		}
		
		// currently just moving to the mouse coordinates
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			Player.onlinePlayers.get(Player.listPosition).updateY(5);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			Player.onlinePlayers.get(Player.listPosition).updateY(-5);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			Player.onlinePlayers.get(Player.listPosition).updateX(-5);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			Player.onlinePlayers.get(Player.listPosition).updateX(5);
		}		
	/*
	 * int dx = Mouse.getDX();   // speed of movement on x axis
	 * int dy = -Mouse.getDY();   // speed of movement on y axis
	 */
	}
}