package handlers;

import java.io.IOException;

import main.PauseMenu;
import misc.OGLRenderer;
import entities.Creature;
import entities.Player;

public class ResourceHandler {

	public static void loadResources() throws IOException {
		OGLRenderer.loadTexture();
		Player.loadTexture();
		Creature.loadTexture();
		PauseMenu.loadTexture();
	}

	public static void deleteResources() throws IOException {
		OGLRenderer.deleteTexture();
		Player.deleteTexture();
		Creature.deleteTexture();
		PauseMenu.deleteTexture();
	}
}
