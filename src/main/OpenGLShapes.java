package main;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.opengl.GL11;

public class OpenGLShapes {
	
	public static void drawQuad(int x, int y, int width, int height, String name, int texture) {
		// draw a square of custom width and height
		
		// draw creature name
				SimpleText.drawString(name, x, y+55);
				
				// can't add texture to Creature without this
				GL11.glDisable(GL_TEXTURE_2D);
				GL11.glEnable(GL_TEXTURE_2D);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
				
		glBegin(GL_QUADS);
			glTexCoord2f(0.0f, 0.0f);
			glVertex2f(x, y);
			glTexCoord2f(1.0f, 0.0f);
			glVertex2f(x+width, y);
			glTexCoord2f(1.0f, 1.0f);
			glVertex2f(x+width, y+height);
			glTexCoord2f(0.0f, 1.0f);
			glVertex2f(x, y+height);
		glEnd();
	}

}
