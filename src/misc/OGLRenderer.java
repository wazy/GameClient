package misc;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import handlers.SpellHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import entities.Creature;
import entities.Player;
import entities.Spell;

public class OGLRenderer {

	private static Texture texture;
	private static File backgroundImg = new File("./img/background.png");
	
	// init OpenGL
	public static void setup() throws IOException {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
	}

	// main drawing loop
	public static void render() {
		// clear canvas
		glClear(GL_COLOR_BUFFER_BIT);
		
		drawBackground();
		drawEntities();
		
		if (SpellHandler.isSpellCasted()) {
			drawSpell();
		}
	}

	private static void drawBackground() {
		drawQuad(0, 0, 640, 480, null, texture);
	}

	public static void loadTexture() throws IOException {
		texture = TextureLoader.getTexture("PNG", new FileInputStream(backgroundImg));
	}

	public static void deleteTexture() {
		texture.release();
	}

	// draw a quad of custom width and height
	public static void drawQuad(int x, int y, int width, int height, String name, Texture texture) {

		// draw entity name
		if (name != null)
			SimpleText.drawString(name, x, y-height);

		// bind texture to OpenGL
		texture.bind();

		glBegin(GL_QUADS);
			glTexCoord2f(0.0f, 0.0f);
			glVertex2f(x, y);				// Upper-left
			
			glTexCoord2f(1.0f, 0.0f);
			glVertex2f(x+width, y);			// Upper-right
			
			glTexCoord2f(1.0f, 1.0f);
			glVertex2f(x+width, y+height);	// Bottom-right
			
			glTexCoord2f(0.0f, 1.0f);
			glVertex2f(x, y+height);		// Bottom-left
		glEnd();
	}

	public static void drawEntities() {
		// draw players in the online list (constantly updated)
		for (Player player : Player.onlinePlayers) {
			if (player != null)
				player.draw("rectangle");
		}

		// draw creatures from server
		for (Creature creature : Creature.creatureList) {
			if (creature != null)
				creature.drawNPC("rectangle");
		}
	}

	// TODO: NYI: DO NOT TOUCH - USE AT YOUR OWN RISK
	public static void drawSpell() {
		int k = 0;
		//System.out.println(Spell.spellMap.size());
		if (k < Spell.spellMap.size()) {  // drawing parts
			Spell spell = Spell.spellMap.get(k);
			if (spell != null) {
				// System.out.println(spell.getX() + " " + spell.getY());
				spell.drawSpell();
			}
			k++;
		}
		else { // all parts have been drawn -- clear to cast again
			int x2 = Spell.spellMap.get(Spell.spellMap.size() - 1).getX();
			int y2 = Spell.spellMap.get(Spell.spellMap.size() - 1).getY();
			System.out.println(x2 + " " + y2);
			
			// temp to show final spell coordinates
			glBegin(GL_QUADS);
				glTexCoord2f(0.0f, 0.0f);
				glVertex2f(x2, y2);
				glTexCoord2f(1.0f, 0.0f);
				glVertex2f(x2+50, y2);
				glTexCoord2f(1.0f, 1.0f);
				glVertex2f(x2+50, y2+50);
				glTexCoord2f(0.0f, 1.0f);
				glVertex2f(x2, y2+50);
			glEnd();
			
			SpellHandler.setIsSpellCasted(false);
			k = 0;
		}
	}
}
