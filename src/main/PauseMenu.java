package main;
import org.newdawn.slick.opengl.Texture;

import utils.OGLRenderer;

// TODO: make this draw from a render call 
public class PauseMenu {

	public static Texture texture;
	public static String textureName = "PAUSE_MENU_1";

	public static void draw() {
		OGLRenderer.drawQuad(0, 0, 640, 480, null, texture);
	}
}
