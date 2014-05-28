package handlers;

import java.util.ArrayList;

import entities.PhysObject;
import entities.Player;

public class PhysicsHandler {
	
	static int gforce = 10;
	static ArrayList<PhysObject> gameObjects = new ArrayList<PhysObject>();
	
	public static void addObject(PhysObject obj) {
		gameObjects.add(obj);
	}
	
	public static void update() {
		
		
		for (Player p : Player.onlinePlayers)
		{
			if (p.getID() == Player.getPlayerID())
				p.updateYvel(gforce*p.getMass());  //Force = mass*accel(due to gravity)
				break;
		}
		
		for (PhysObject ob : gameObjects) {
			ob.updateYvel(gforce*ob.getMass());  //Force = mass*accel(due to gravity)
		}
	}
	
}