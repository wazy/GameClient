package main;
import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class GameDisplay {

	// TODO: Change this to something better named
	public static volatile boolean drawProjectile = false;

	public static void run() throws IOException {

		// setup LWJGL and OpenGL
		setupWindow();
		OGLRenderer.setup();
		loadResources();

		// while not closed
		while (!Display.isCloseRequested() && !GameClient.isExitRequested()) {
			System.out.println(Mouse.getX() + ", " + (480 - Mouse.getY() - 1));
			switch(States.getState()) {
				case 0:  // entry point -> start threads
					States.setState(1);
					ThreadHandler.initAll();
					break;

				case 1: // runs main game 
					States.checkPaused();
					Movement.check();
					OGLRenderer.render();
					break;

				case 2: // handles pause menu
					PauseMenu.draw();
					States.checkPlay();
					break;

				default:
					System.out.println("Invalid State: Terminating Program!");
					System.exit(1);
			}

			Display.update();
			Display.sync(60);
		}
		
		System.out.println("SHUTDOWN: Main display thread is exiting..");
		
		// cleanup and termination
		deleteResources();
		Display.destroy();
		GameClient.setExitRequest(true);
	}

	// TODO: REMOVE ME!!!!! (Hint -> ResourceLoader)
	private static void loadResources() throws IOException {
		OGLRenderer.loadTexture();
		Player.loadTexture();
		Creature.loadTexture();
		PauseMenu.loadTexture();
	}
	
	// TODO: REMOVE ME!!!!! (Hint -> ResourceLoader)
	private static void deleteResources() throws IOException {
		OGLRenderer.deleteTexture();
		Player.deleteTexture();
		Creature.deleteTexture();
		PauseMenu.deleteTexture();
	}

	public static void setupWindow() {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Game");
			Display.create();
		}
		catch(LWJGLException e){
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static boolean isSpellCasted() {
		return drawProjectile;
	}
}