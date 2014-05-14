package display;
import handlers.MovementHandler;
import handlers.ResourceHandler;
import handlers.ThreadHandler;

import java.io.IOException;

import main.GameClient;
import main.PauseMenu;
import misc.OGLRenderer;
import misc.States;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class GameDisplay {
	public static void run() throws IOException {

		// setup LWJGL, OpenGL, and Resources
		setupWindow();
		OGLRenderer.setup();
		ResourceHandler.loadResources();

		// the main game loop runs until window is closed
		while (!Display.isCloseRequested() && !GameClient.isExitRequested()) {

			switch(States.getState()) {
				case 0:  // entry point -> start threads
					States.setState(1);
					ThreadHandler.initAll();
					break;

				case 1: // runs main game 
					States.checkPaused();
					MovementHandler.check();
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
		ResourceHandler.deleteResources();

		Display.destroy();
		GameClient.setExitRequest(true);
	}

	public static void setupWindow() {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Game Window");
			Display.create();
		}
		catch(LWJGLException e){
			e.printStackTrace();
			System.exit(1);
		}
	}
}