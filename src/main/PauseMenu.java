package main;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

// TODO: make this draw from a render call 
public class PauseMenu {

	private static Texture texture;
	private static File pauseMenu = new File("./img/PauseMenu.png");

	public static void loadTexture() throws IOException {
		texture = TextureLoader.getTexture("PNG", new FileInputStream(pauseMenu));
	}

	public static Texture getTexture() {
		return texture;
	}

	public static void deleteTexture() {
		texture.release();
	}

	public static void draw() {
		OGLRenderer.drawQuad(0, 0, 640, 480, null, texture);
	}
}
