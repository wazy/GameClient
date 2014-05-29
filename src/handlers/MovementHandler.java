package handlers;

import org.lwjgl.input.Keyboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entities.Player;

public class MovementHandler {

	final static Logger logger = LoggerFactory.getLogger(MovementHandler.class);

	private final static int PLAYER_SPEED = 1;
	private final static int PLAYER_LETHAL_COLLISION = 5;
	private static int counter = 0;
	private static int gForce = 1;
	
	public static void check() {
		counter++;

		// client still loading
		if (Player.listPosition.get() < 0 || Player.onlinePlayers.size() < 1 
				|| Player.onlinePlayers.size() <= Player.listPosition.get()) {
			return;
		}

		Player player = Player.getOnlinePlayers().get(Player.getListPosition().get());

		int x = player.getX();
		int y = player.getY();
		
		// TODO: PUT IN PHYSICS HANDLER!!!
		// update physics every 10 frames (60/10 = 6 updates per second) 
		if (counter >= 10) {
			if (player.getY() < 350) {
				player.updateY(gForce++);
			}
			else if (gForce > PLAYER_LETHAL_COLLISION) {
				logger.info("YOU DIED!");
				gForce = 1;
			}
			else
				gForce = 1;
			
			counter = 0;
		}
		
		//System.out.println(x + " " + y);
		
		// if player is heading out of screen boundary
		// then push player back -> player X and Y are top left corner
		// so subtract height and width from x and y for calculation
		// 640-50 = 590 and 480-50 = 430 
		if (x >= 590)
			player.setX(x - PLAYER_SPEED);
		
		 if (y >= 430)
			 player.setY(y - PLAYER_SPEED);
		 
		 if (x <= 0) {
			 player.setX(x + PLAYER_SPEED);
			 ResourceHandler.testingWavEffect.playAsSoundEffect(1.0f, 1.0f, false);
		 }
		 
		 if (y <= 0)
			 player.setY(y + PLAYER_SPEED);
		 
//		// just testing .... 
//		if (Mouse.next()) { // buffered events
//			if (Mouse.getEventButtonState()) { // pressed down?
//				if (player.inBounds(Mouse.getX(), 480 - Mouse.getY() - 1)) {
//					ResourceHandler.getFont().drawString(300, 300, "YOU CLICKED PLAYER");
//				}
//			}
//		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) { // check if player is moving up and left
				player.updateXY(-PLAYER_SPEED, -PLAYER_SPEED); // moves the player northwest
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_D)) { // check if player is moving up and right
				player.updateXY(PLAYER_SPEED, -PLAYER_SPEED); // moves the player northeast
			}
			else {
				player.updateY(-PLAYER_SPEED); // moves the player up 
			}
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) { // check if player is moving down and left
				player.updateXY(-PLAYER_SPEED, PLAYER_SPEED); // moves the player southwest
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_D)) { // check if player is moving down and right
				player.updateXY(PLAYER_SPEED, PLAYER_SPEED); // moves the player southeast
			}
			else {
				player.updateY(PLAYER_SPEED); // moves the player down
			}
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) { // check if player is moving left and up
				player.updateXY(-PLAYER_SPEED,-PLAYER_SPEED); // moves the player northwest
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_S)) { // check if player is moving left and down
				player.updateXY(-PLAYER_SPEED, PLAYER_SPEED); // moves the player southwest
			}
			else {
				player.updateX(-PLAYER_SPEED); // moves the player left
			}
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) { // check if player is moving right and up
				player.updateXY(PLAYER_SPEED, -PLAYER_SPEED); // moves the player northeast
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_S)) { // check if player is moving right and down
				player.updateXY(PLAYER_SPEED, PLAYER_SPEED); // moves the player southeast
			}
			else {
				player.updateX(PLAYER_SPEED); // moves the player right
			}
		}
	/*
	 * int dx = Mouse.getDX();   // speed of movement on x axis
	 * int dy = -Mouse.getDY();   // speed of movement on y axis
	 */
	}
}