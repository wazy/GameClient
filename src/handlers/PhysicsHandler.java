package handlers;

import java.util.ArrayList;
import entities.PhysObject;

public class PhysicsHandler {
	
	static int gforce;
	static ArrayList<PhysObject> gameObjects = new ArrayList<PhysObject>();
	
	public static void addObject(PhysObject obj) {
		gameObjects.add(obj);
	}
	
	public static void init(int gf) {
		gforce = gf;
		
		for (PhysObject ob : gameObjects) {
			ob.updateYvel(gforce);
		}
	}
	
}