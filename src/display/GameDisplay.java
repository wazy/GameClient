package display;
import handlers.MovementHandler;
import handlers.ResourceHandler;
import handlers.ThreadHandler;
import handlers.TimeHandler;

import java.io.IOException;

import main.GameClient;
import main.PauseMenu;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.openal.SoundStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.OGLRenderer;
import utils.States;


public class GameDisplay {

	final static Logger logger = LoggerFactory.getLogger(GameDisplay.class);

	public static void run() throws IOException {

		// setup LWJGL, OpenGL, and Resources
		setupWindow(); 
		OGLRenderer.setup();
		ResourceHandler.loadResources();

		logger.info("Display, Renderer, and Resources are loaded.");

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
					logger.error("Invalid State: Terminating Program!");
					System.exit(1);
			}
			
			SoundStore.get().poll(0);
			TimeHandler.updateFPS();
			Display.update();
			Display.sync(60);
		}
		
		logger.info("SHUTDOWN: Main display thread is exiting..");
		
		// cleanup and termination
		ResourceHandler.deleteResources();

		Display.destroy();
		GameClient.setExitRequest(true);
	}

	public static void setupWindow() {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.create();

			// init the FPS
			TimeHandler.setLastFPS(TimeHandler.getTime());
		}
		catch(LWJGLException e){
			e.printStackTrace();
			System.exit(1);
		}
	}
}