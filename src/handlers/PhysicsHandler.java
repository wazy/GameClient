package handlers;

import java.util.ArrayList;

import entities.AbstractEntity;
import entities.Player;

public class PhysicsHandler {

	private int gforce = 10;
	private ArrayList<AbstractEntity> gameObjects = new ArrayList<AbstractEntity>();

	public PhysicsHandler() { }

	public void addObject(AbstractEntity obj) {
		gameObjects.add(obj);
	}

	public void update() {
		int position = -1;
		while ((position = Player.getListPosition().get()) < 0) {;}
		Player player = Player.getOnlinePlayers().get(position); 
		
		if (player.getID() == Player.getPlayerID())
			player.updateYvel(player.getMass() * gforce);  //Force (of fall) = mass*accel(due to gravity)

		int px = player.getX(); int py = player.getY();
		int pw = player.getWidth(); int ph = player.getHeight();

		for (AbstractEntity ob : WorldObjectHandler.getObjectList()) {
			int ox = ob.getX(); int oy = ob.getY();
			int ow = ob.getWidth(); int oh = ob.getHeight();

			//if ((py >= (oy - oh) && py <= oy) || (oy >= (py - ph) && oy <= py)) {
			//	MovementHandler.ground = oy;
			//	System.out.println("Y collision");
			//}
			
			// collision (from left) between an object and the player
			if ((px >= (ox - ow) && px <= ox)) {
				if (py <= oy) {
					MovementHandler.ground = oy-oh;
					System.out.println("Ground Set " + (oy-oh));
					continue;
				}
				player.updateX(-1);
				System.out.printf("Collision (L) -> Object (x, y, w, h): (%d, %d, %d, %d) | " +
												"Player (x, y, w, h): (%d, %d, %d, %d)\n",
												ox, oy, ow, oh, px, py, pw, ph);
			}
			// collision (from right) between an object and the player
			else if ((ox >= (px - pw) && ox <= px) && (oy >= (py - ph) && oy <= py)) {
				player.updateX(1);
				System.out.printf("Collision (R) -> Object (x, y, w, h): (%d, %d, %d, %d) | " +
												"Player (x, y, w, h): (%d, %d, %d, %d)\n",
												ox, oy, ow, oh, px, py, pw, ph);
			}
		}
	}
}
