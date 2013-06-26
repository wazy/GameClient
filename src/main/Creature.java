package main;
import static org.lwjgl.opengl.GL11.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.lwjgl.opengl.GL11;
import java.io.Serializable;

public class Creature implements Serializable {
	private static final long serialVersionUID = -8405971951484157840L;

	private static int texture;
	public static int creatureID = 0;
	private static String creatureImg = new File("./img/monster.png").getAbsolutePath();

	public static List<Creature> creatureList = Collections.synchronizedList(new ArrayList<Creature>(16));
	public static int listPosition = 0; // client's position in the list init to zero
	public int id, x, y, alliance;
	public String name;
	public boolean selected = false;
	
	Creature (int id, String name, int x, int y, int alliance) {
		this.id = id;
		this.name = name;
		this.alliance = alliance;
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
		texture = TextureLoader.setupTextures(creatureImg);
	}
	
	public static void deleteTexture() {
		// dont forget to delete the texture after use
		GL11.glDeleteTextures(texture);
	}
	
	void drawNPC() {
		// draw player name
		SimpleText.drawString(name, x, y+55);
		
		// can't add texture to Creature without this
		GL11.glDisable(GL_TEXTURE_2D);
		GL11.glEnable(GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		
		// draw a monster (a quad) 50 x 50
		glBegin(GL_QUADS);
			glTexCoord2f(0.0f, 0.0f);
			glVertex2f(x, y);
			glTexCoord2f(1.0f, 0.0f);
			glVertex2f(x+50, y);
			glTexCoord2f(1.0f, 1.0f);
			glVertex2f(x+50, y+50);
			glTexCoord2f(0.0f, 1.0f);
			glVertex2f(x, y+50);
		glEnd();
	}
	public static void setId(String npcID) {
		Creature.creatureID = Integer.parseInt(npcID);
	}
	public static int getId() {
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
}