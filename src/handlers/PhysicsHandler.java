package handlers;

import java.util.ArrayList;

import entities.AbstractMoveableEntity;
import entities.Player;

public class PhysicsHandler {
	
	static int gforce = 10;
	static ArrayList<AbstractMoveableEntity> gameObjects = new ArrayList<AbstractMoveableEntity>();
	
	public static void addObject(AbstractMoveableEntity obj) {
		gameObjects.add(obj);
	}
	
	public static void update() {
		
		
		for (Player p : Player.getOnlinePlayers())
		{
			if (p.getID() == Player.getPlayerID())
				p.updateYvel(gforce*p.getMass());  //Force = mass*accel(due to gravity)
				break;
		}
		
		for (AbstractMoveableEntity ob : gameObjects) {
			ob.updateYvel(gforce*ob.getMass());  //Force = mass*accel(due to gravity)
		}
	}
	
}