package handlers;

import java.io.IOException;

import main.PauseMenu;

import org.lwjgl.openal.AL;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import utils.OGLRenderer;
import entities.Creature;
import entities.Player;

public class ResourceHandler {

	public static UnicodeFont font;
	public static Audio testingWavEffect;

	public static void loadResources() throws IOException {
		OGLRenderer.loadTexture();
		Player.loadTexture();
		Creature.loadTexture();
		PauseMenu.loadTexture();

		setUpFonts();
		
		loadSounds();
	}

	public static void deleteResources() throws IOException {
		OGLRenderer.deleteTexture();
		Player.deleteTexture();
		Creature.deleteTexture();
		PauseMenu.deleteTexture();

		font.destroy();
		testingWavEffect.release();

		AL.destroy();
	}
	
	@SuppressWarnings("unchecked")
	private static void setUpFonts() {
		java.awt.Font awtFont = new java.awt.Font("Times New Roman", java.awt.Font.BOLD, 18);
		font = new UnicodeFont(awtFont);
		font.getEffects().add(new ColorEffect(java.awt.Color.black));
		font.addAsciiGlyphs();
		try {
			font.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public static UnicodeFont getFont() {
		return font;
	}

	private static void loadSounds() throws IOException {
		testingWavEffect = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("sound/testing.wav"));		
	}
}
