package main;
import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.lwjgl.opengl.GL11;

import java.io.Serializable;

public class Player implements Serializable {

	private static int tex = -1;
	public static int playerID = -1;
	public static volatile int playerHealth = 5;

	public static AtomicInteger listPosition = new AtomicInteger(-1);

	private static final long serialVersionUID = -8405971951484157839L;
	private static String playerImg = new File("./img/etc2.png").getAbsolutePath();

	public static volatile List<Player> onlinePlayers =	Collections.synchronizedList(
																new ArrayList<Player>(16));
	
	public String name;

	// x and y should probably be atomic for thread safety?
	public int id, x, y;
	
	public boolean selected = false;
	
	Player (int id, String name, int x, int y) {
		this.id = id;
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	boolean inBounds(int mouseX, int mouseY) {
		if (mouseX > x && mouseX < x + 50 && mouseY > y && mouseY < y + 50)
			return true;
		else
			return false;
	}
	
	public static void loadTexture() {
		tex = TextureLoader.setupTextures(playerImg);
	}
	
	public static void deleteTexture() {
		// dont forget to delete the texture after use
		GL11.glDeleteTextures(tex);
	}
	
	void draw(String shape) {
		if (shape.equals("rectangle") == true)
			OpenGLShapes.drawQuad(this.x, this.y, 50, 50, this.name, tex);
	}
	
	// this can help prevent wrong client d/c on server
	public void setID(String playerID) {
		this.id = Integer.parseInt(playerID);
	}
	public int getID() {
		return this.id;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getX() {
		return this.x;
	}
	
	public void setX(int X) {
		this.x = X;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setY(int Y) {
		this.y = Y;
	}
	
	void updateX(int newXValue) {
		x += newXValue;
	}
	void updateY(int newYValue) {
		y += newYValue;
	}
	void updateXY(int newXValue, int newYValue) {
		x += newXValue;
		y += newYValue;
	}

	// finds where the player's info is -- from the server
	public static int findPlayerPosInList(int previousPos) {
		
		// no need to reiterate -- position didn't change
		if ((previousPos >= 0) && (previousPos < onlinePlayers.size()) 
				&& (onlinePlayers.get(previousPos).getID() == previousPos))
			return previousPos;
		
		for (int i = 0; i < onlinePlayers.size(); i++) {
			if (onlinePlayers.get(i).getID() == playerID) {
				listPosition.set(i);
				return i;
			}
		}
		return -1;
	}
}