package entities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import misc.OGLRenderer;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Spell { 

		private static Texture texture;
		private static File spellImg = new File("./img/monster.png");

		public static Map<Integer, Spell> spellMap = new HashMap<Integer, Spell>(20);
		public int spellID, x, y;
		public String name;
		
		public Spell (int id, String name, int x, int y) {
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
		
		public static void loadTexture() throws IOException {
			texture = TextureLoader.getTexture("PNG", new FileInputStream(spellImg));
		}

		public static Texture getTexture() {
			return texture;
		}

		public static void deleteTexture() {
			texture.release();
		}
		
		public void drawSpell() {
			OGLRenderer.drawQuad(x, y, 15, 15, null, texture);
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
