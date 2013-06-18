package main;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

import java.io.File;

import org.lwjgl.opengl.GL11;

// TODO: implement own classes for each state 
public class StateDrawer {
	
	private static String pauseMenu = new File("./img/PauseMenu.png").getAbsolutePath();
	private static int tex;
	
	public static boolean inBoundsMainMenu(int mouseX, int mouseY) {
		if (mouseX > 280 && mouseX < 360 && mouseY > 200 && mouseY < 280)
			return true;
		else
			return false;
	}
	
	public static void drawMainMenu(String rep) {
		glColor3f(0, 0, 0);
		glBegin(GL_QUADS);
			glVertex2f(0, 0);
			glVertex2f(640, 0);
			glVertex2f(640, 480);
			glVertex2f(0, 480);
		glEnd();
		
		float x = (float) .7;
		float y = (float) .9;
		float z = (float) .7;
		
		glColor3f(x, y, z);
		glBegin(GL_QUADS);
			glVertex2f(280, 200);
			glVertex2f(360, 200);
			glVertex2f(360, 280);
			glVertex2f(280, 280);
		glEnd();
	}
	
	// load pause menu texture
	public static void loadTexture() {
		tex = TextureLoader.setupTextures(pauseMenu);
	}
	
	public static void deleteTexture() {
		GL11.glDeleteTextures(tex);
	}
	
	public static void drawPauseMenu() {
		GL11.glEnable(GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
		glColor3f(1f, 1f, 1f);   // stabilize coloring
		glBegin(GL_QUADS);
			glTexCoord2f(0.0f, 0.0f);
			glVertex2f(0, 0);
			glTexCoord2f(1.0f, 0.0f);
			glVertex2f(640, 0);
			glTexCoord2f(1.0f, 1.0f);
			glVertex2f(640, 480);
			glTexCoord2f(0.0f, 1.0f);
			glVertex2f(0, 480);
		glEnd();
	}
}
