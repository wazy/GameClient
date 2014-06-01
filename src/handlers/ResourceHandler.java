package handlers;

import static utils.Textures.getTextureFromHashMap;

import java.io.IOException;

import main.PauseMenu;

import org.lwjgl.openal.AL;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;

import utils.OGLRenderer;
import utils.Textures;

public class ResourceHandler {

	public static UnicodeFont font;
	public static Audio testingWavEffect;

	public static void loadResources() throws IOException {
		Textures.loadTextures();

		setGameBackgrounds();

		setUpFonts();
		loadSounds();

		WorldObjectHandler.loadObjects("level-1");
	}

	public static void deleteResources() throws IOException {
		Textures.deleteTextures();
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

	public static Texture getTexture(String textureName) {
		return getTextureFromHashMap(textureName);
	}

	// TODO: Put these somewhere better.
	// sets background image and pause menu (textures)
	private static void setGameBackgrounds() {
		OGLRenderer.backgroundTexture = ResourceHandler.getTexture(OGLRenderer.backgroundTextureName);
		PauseMenu.texture = ResourceHandler.getTexture(PauseMenu.textureName);
	}
}
