package handlers;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import utils.SimpleText;

import entities.Player;

public class MovementHandler {
	
	public static void check() {

		// client still loading
		if (Player.listPosition.get() < 0 || Player.onlinePlayers.size() < 1 
				|| Player.onlinePlayers.size() <= Player.listPosition.get()) {
			return;
		}

		Player player = Player.onlinePlayers.get(Player.listPosition.get());
		int x = player.getX();
		int y = player.getY();
		
		//System.out.println(x + " " + y);
		
		// if player is out of screen boundary
		// this should actually push player back
		// temporarily going to reset coordinates
		if (x > 640 || y > 480 || x < 0 || y < 0) {
			player.setX(100);
			player.setY(100);
		}
		
		// just testing .... 
		if (Mouse.next()) { // buffered events
			if (Mouse.getEventButtonState()) { // pressed down?
				if (player.inBounds(Mouse.getX(), Mouse.getY())) {
					SimpleText.drawString("YOU CLICKED PLAYER", 500, 100);
				}
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) { // check if player is moving up and left
				player.updateXY(-5,-5); // moves the player northwest
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_D)) { // check if player is moving up and right
				player.updateXY(5, -5); // moves the player northeast
			}
			else {
				player.updateY(-5); // moves the player up 
			}
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) { // check if player is moving down and left
				player.updateXY(-5,5); // moves the player southwest
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_D)) { // check if player is moving down and right
				player.updateXY(5, 5); // moves the player southeast
			}
			else {
				player.updateY(5); // moves the player down
			}
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) { // check if player is moving left and up
				player.updateXY(-5,-5); // moves the player northwest
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_S)) { // check if player is moving left and down
				player.updateXY(-5, 5); // moves the player southwest
			}
			else {
				player.updateX(-5); // moves the player left
			}
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) { // check if player is moving right and up
				player.updateXY(5, -5); // moves the player northeast
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_S)) { // check if player is moving right and down
				player.updateXY(5, 5); // moves the player southeast
			}
			else {
				player.updateX(5); // moves the player right
			}
		}
	/*
	 * int dx = Mouse.getDX();   // speed of movement on x axis
	 * int dy = -Mouse.getDY();   // speed of movement on y axis
	 */
	}
}