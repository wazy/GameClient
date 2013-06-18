package main;
import static org.lwjgl.opengl.GL11.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.lwjgl.opengl.GL11;
import java.io.Serializable;

public class Player implements Serializable {
	
	private static final long serialVersionUID = -8405971951484157839L;
	private static int tex;
	public static int playerID = 0;
	private static String playerImg = new File("./img/etc2.png").getAbsolutePath();
	
	public static List<Player> onlinePlayers = Collections.synchronizedList(new ArrayList<Player>(16));

	public static int listPosition = 0; // client's position in the list init to zero
	public int id, x, y;
	public String name;
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
	
	void draw() {
		// draw player name
		SimpleText.drawString(name, x, y+55);
		
		// can't add texture to player without this
		GL11.glDisable(GL_TEXTURE_2D);
		GL11.glEnable(GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
		
		// draw the player (a quad) 50 x 50
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
	public static void setId(String playerId) {
		Player.playerID = Integer.parseInt(playerId);
	}
	public static int getId() {
		return Player.playerID;
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
}