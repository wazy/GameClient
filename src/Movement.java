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
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) { // check if player is moving up and left
				Player.onlinePlayers.get(Player.listPosition).updateXY(-5,5); // moves the player northwest
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_D)) { // check if player is moving up and right
				Player.onlinePlayers.get(Player.listPosition).updateXY(5, 5); // moves the player northeast
			}
			else {
				Player.onlinePlayers.get(Player.listPosition).updateY(5); // moves the player up 
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) { // check if player is moving down and left
				Player.onlinePlayers.get(Player.listPosition).updateXY(-5,-5); // moves the player southwest
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_D)) { // check if player is moving down and right
				Player.onlinePlayers.get(Player.listPosition).updateXY(5, -5); // moves the player southeast
			}
			else {
				Player.onlinePlayers.get(Player.listPosition).updateY(-5); // moves the player down
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) { // check if player is moving left and up
				Player.onlinePlayers.get(Player.listPosition).updateXY(-5,5); // moves the player northwest
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_S)) { // check if player is moving left and down
				Player.onlinePlayers.get(Player.listPosition).updateXY(-5, -5); // moves the player southwest
			}
			else {
				Player.onlinePlayers.get(Player.listPosition).updateX(-5); // moves the player left
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) { // check if player is moving right and up
				Player.onlinePlayers.get(Player.listPosition).updateXY(5,5); // moves the player northeast
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_S)) { // check if player is moving right and down
				Player.onlinePlayers.get(Player.listPosition).updateXY(5, -5); // moves the player southeast
			}
			else {
				Player.onlinePlayers.get(Player.listPosition).updateX(5); // moves the player right
			}
		}
	/*
	 * int dx = Mouse.getDX();   // speed of movement on x axis
	 * int dy = -Mouse.getDY();   // speed of movement on y axis
	 */
	}
}