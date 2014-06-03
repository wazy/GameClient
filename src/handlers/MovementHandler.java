package handlers;

import org.lwjgl.input.Keyboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entities.Player;

import static org.lwjgl.input.Keyboard.isKeyDown;

public class MovementHandler {

	final static Logger logger = LoggerFactory.getLogger(MovementHandler.class);

	private static int gForce = 1;
	public static int ground = 350;
	private static int fallDelay = 0;

	private final static int PLAYER_SPEED = 1;
	private final static int PLAYER_JUMP_SPEED = 50;
	private final static int PLAYER_LETHAL_COLLISION = 15;

	public static void check() {

		// client still loading
		if (Player.listPosition.get() < 0 || Player.onlinePlayers.size() < 1 
				|| Player.onlinePlayers.size() <= Player.listPosition.get()) {
			return;
		}

		Player player = Player.getOnlinePlayers().get(Player.getListPosition().get());
		
		int x = player.getX();
		int y = player.getY();

		// if player should be falling
		if (!player.isFalling() && y < ground)
			player.isFalling(true);
		
		fallDelay++;

		// TODO: PUT IN PHYSICS HANDLER!!!
		if (fallDelay >= 2 && player.isFalling()) {
			if (y < ground) { // (above ground)
				player.updateY(gForce++);
			}
			else if (gForce > PLAYER_LETHAL_COLLISION) {
				logger.info("YOU DIED!");
				player.isFalling(false);
				gForce = 1;
			}
			else {
				player.isFalling(false);
				gForce = 1;
			}
			fallDelay = 0;
		}

		//System.out.println(x + " " + y);
		
		// if player is heading out of screen boundary
		// then push player back -> player X and Y are top left corner
		// so subtract height and width from x and y for calculation
		// 640-50 = 590 and 480-50 = 430 
		if (x > 590)
			player.setX(590);

		if (x < 0) {
			player.setX(0);
			if (!ResourceHandler.testingWavEffect.isPlaying())
				ResourceHandler.testingWavEffect.playAsSoundEffect(1.0f, 1.0f, false);
		}

		if (y >= 430)
			player.setY(y - PLAYER_SPEED);

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

//		while (Keyboard.next()) {
//			switch(Keyboard.getEventCharacter()) {
//			case Keyboard.KEY_W:
//				System.out.println("W PRESSED!!!!");
//			}
//		}

		if (isKeyDown(Keyboard.KEY_W) && !player.isFalling()) {
			if (isKeyDown(Keyboard.KEY_A)) { // check if player is moving up and left
				player.updateXY(-PLAYER_JUMP_SPEED, -PLAYER_JUMP_SPEED); // moves the player northwest
			}
			else if (isKeyDown(Keyboard.KEY_D)) { // check if player is moving up and right
				player.updateXY(PLAYER_JUMP_SPEED, -PLAYER_JUMP_SPEED); // moves the player northeast
			}
			else {
				player.updateY(-PLAYER_JUMP_SPEED); // moves the player up 
			}
		}
		else if (isKeyDown(Keyboard.KEY_S)) {
			if (isKeyDown(Keyboard.KEY_A)) { // check if player is moving down and left
				player.updateXY(-PLAYER_SPEED, PLAYER_SPEED); // moves the player southwest
			}
			else if (isKeyDown(Keyboard.KEY_D)) { // check if player is moving down and right
				player.updateXY(PLAYER_SPEED, PLAYER_SPEED); // moves the player southeast
			}
			else {
				player.updateY(PLAYER_SPEED); // moves the player down
			}
		}
		else if (isKeyDown(Keyboard.KEY_A)) {
			if (isKeyDown(Keyboard.KEY_W)) { // check if player is moving left and up
				player.updateXY(-PLAYER_SPEED,-PLAYER_SPEED); // moves the player northwest
			}
			else if (isKeyDown(Keyboard.KEY_S)) { // check if player is moving left and down
				player.updateXY(-PLAYER_SPEED, PLAYER_SPEED); // moves the player southwest
			}
			else {
				player.updateX(-PLAYER_SPEED); // moves the player left
			}
		}
		else if (isKeyDown(Keyboard.KEY_D)) {
			if (isKeyDown(Keyboard.KEY_W)) { // check if player is moving right and up
				player.updateXY(PLAYER_SPEED, -PLAYER_SPEED); // moves the player northeast
			}
			else if (isKeyDown(Keyboard.KEY_S)) { // check if player is moving right and down
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