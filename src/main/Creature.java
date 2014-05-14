package main;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Creature implements Serializable {
	private static final long serialVersionUID = -8405971951484157840L;

	private static Texture tex;
	public static int creatureID = 0;
	private static File creatureImg = new File("./img/monster.png");

	public static List<Creature> creatureList = Collections.synchronizedList(new ArrayList<Creature>(16));
	public static int listPosition = 0; // client's position in the list init to zero
	private int ID, x, y, width, height, faction;
	private String name;
	private boolean targeted = false;
	
	Creature (int id, String name, int x, int y, int width, int height, int alliance) {
		this.ID = id;
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.faction = alliance;
	}
	
	boolean inBounds(int mouseX, int mouseY) {
		if (mouseX > x && mouseX < x + 50 && mouseY > y && mouseY < y + 50)
			return true;
		else
			return false;
	}
	
	public static void loadTexture() throws IOException {
		tex = TextureLoader.getTexture("PNG", new FileInputStream(creatureImg));
	}

	public static Texture getTexture() {
		return tex;
	}

	public static void deleteTexture() {
		tex.release();
	}

	
	void drawNPC(String shape) {
		if (shape.equals("rectangle") == true)
			OGLRenderer.drawQuad(this.x, this.y, this.width, this.height, this.name, tex);
	}
	public static void setID(String npcID) {
		Creature.creatureID = Integer.parseInt(npcID);
	}
	public static int getCreatureID() {
		return Creature.creatureID;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public String getName() {
		return this.name;
	}

	public boolean isTargeted() {
		return targeted;
	}

	public void setTargeted(boolean targeted) {
		this.targeted = targeted;
	}

	public int getFaction() {
		return faction;
	}

	public void setFaction(int faction) {
		this.faction = faction;
	}
	public int getID() {
		return this.ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
}