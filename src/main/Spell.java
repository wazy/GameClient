package main;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

public class Spell { 

		private static int texture;
		private static String spellImg = new File("./img/monster.png").getAbsolutePath();

		public static List<Spell> spellList = Collections.synchronizedList(new ArrayList<Spell>(10));
		public int spellID, x, y;
		public String name;
		
		Spell (int id, String name, int x, int y) {
			this.spellID = id;
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
			texture = TextureLoader.setupTextures(spellImg);
		}
		
		public static void deleteTexture() {
			// dont forget to delete the texture after use
			GL11.glDeleteTextures(texture);
		}
		
		void drawSpell() {
			
			// can't add texture to Creature without this
			GL11.glDisable(GL_TEXTURE_2D);
			GL11.glEnable(GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
			
			// draw a monster (a quad) 50 x 50
			glBegin(GL_QUADS);
				glTexCoord2f(0.0f, 0.0f);
				glVertex2f(x, y);
				glTexCoord2f(1.0f, 0.0f);
				glVertex2f(x+15, y);
				glTexCoord2f(1.0f, 1.0f);
				glVertex2f(x+15, y+15);
				glTexCoord2f(0.0f, 1.0f);
				glVertex2f(x, y+15);
			glEnd();
		}
		public int getId() {
			return this.spellID;
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
