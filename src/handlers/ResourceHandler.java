package handlers;

import java.io.IOException;

import utils.OGLRenderer;

import main.PauseMenu;
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
