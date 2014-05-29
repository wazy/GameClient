package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Textures {
	private static HashMap<String, Texture> textureMap = new HashMap<String, Texture> (32);
	
	public static final String BACKGROUND_IMAGE_1 	= "BACKGROUND_IMAGE_1";
	public static final String PAUSE_MENU_1 		= "PAUSE_MENU_1";
	public static final String PLAYER_IMAGE_1 		= "PLAYER_IMAGE_1";
	public static final String PLAYER_IMAGE_2 		= "PLAYER_IMAGE_2";
	public static final String GROUND_IMAGE_1 		= "GROUND_IMAGE_1";
	public static final String GRASS_IMAGE_1		= "GRASS_IMAGE_1";
	

	public static void loadTextures() throws FileNotFoundException, IOException {
		addTexture(BACKGROUND_IMAGE_1);
		addTexture(PAUSE_MENU_1);
		addTexture(PLAYER_IMAGE_1);
		addTexture(PLAYER_IMAGE_2);
		addTexture(GROUND_IMAGE_1);
		addTexture(GRASS_IMAGE_1);
	}

	public static void addTexture(String key) throws FileNotFoundException, IOException {
		Texture value = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/img/" + key + ".png")));
		textureMap.put(key, value);
	}


	public static void deleteTextures() {
		for (Texture texture : textureMap.values()) {
			if (texture != null)
				texture.release();
		}
	}
	
	public static Texture getTextureFromHashMap(String key) {
		return textureMap.get(key);
	}
}
