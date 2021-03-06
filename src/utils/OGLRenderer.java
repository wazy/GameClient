package utils;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static entities.Player.PLAYER_WIDTH;
import static handlers.ResourceHandler.font;

import handlers.SpellHandler;
import handlers.WorldObjectHandler;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;

import entities.Player;
import entities.Spell;
import entities.XMLObject;

public class OGLRenderer {

	public static Texture backgroundTexture;
	public static String backgroundTextureName = "BACKGROUND_IMAGE_1";

	// init OpenGL
	public static void setup() throws IOException {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_BLEND); 
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}

	// main drawing loop
	public static void render() {

		drawBackground();
		drawEntities();
		
//		if (SpellHandler.isSpellCasted())
//			drawSpell();
	}

	// clear canvas and texture the background quad
	private static void drawBackground() {
		glClear(GL_COLOR_BUFFER_BIT);
		drawQuad(0, 0, 640, 480, null, backgroundTexture);
	}

	// draw a quad of custom width and height
	public static void drawQuad(int x, int y, int width, int height, String name, Texture texture) {

		// TODO: Make this better?
		// draw entity name at an offset of x and y
		if (name != null)
			font.drawString(calculateNameX(x, name.length()), y-20, name);

		// bind texture to OpenGL
		if (texture != null)
			texture.bind();

		//System.out.println(x + " " + y + " " + width + " " + height + " " + name + " " + texture);
		
		glEnable(GL_TEXTURE_2D);
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
		glDisable(GL_TEXTURE_2D);
	}

	// this calcuates the x where we should start drawing the name
	// uses a magic number 8 for font width
	private static int calculateNameX(int x, int size) {
		return x + ((PLAYER_WIDTH - (size * 8)) / 2);
	}

	public static void drawEntities() {

		// level objects loaded from xml file
		for (XMLObject obj : WorldObjectHandler.getObjectList()) {
			if (obj != null)
				obj.draw();
		}
		
		// draw players in the online list (constantly updated)
		for (Player player : Player.getOnlinePlayers()) {
			if (player != null)
				player.draw();
		}

//		// draw creatures from server
//		for (Creature creature : Creature.creatureList) {
//			if (creature != null)
//				creature.drawNPC("rectangle");
//		}
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
