package handlers;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

/* These functions come from the LWJGL Wiki. */
public class TimeHandler {
	private static int fps;
	private static long lastFPS;
	private static long lastFrame;
	
	/** 
	 * Calculate how many milliseconds have passed 
	 * since last frame.
	 * 
	 * @return milliseconds passed since last frame 
	 */
	public static int getDelta() {
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	 
	    return delta;
	}

	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public static long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public static void updateFPS() {
	    if (getTime() - lastFPS > 1000) {
	        Display.setTitle("Game Window | FPS: " + fps); 
	        fps = 0; //reset the FPS counter
	        lastFPS += 1000; //add one second
	    }
	    fps++;
	}

	public static void setLastFPS(long time) {
		lastFPS = time;
	}
}
