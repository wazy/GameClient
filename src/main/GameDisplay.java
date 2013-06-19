package main;
import static org.lwjgl.opengl.GL11.*;
import java.sql.SQLException;
import org.lwjgl.opengl.*;
import org.lwjgl.*;

public class GameDisplay {
	public static void run() throws SQLException {
		DisplayMode x;
		try{
			x = new DisplayMode(640, 480);
			Display.setDisplayMode(x);
			Display.setTitle("Game");
			Display.create();
		}
		catch(LWJGLException e){
			e.printStackTrace();
		}
		
		// init opengl
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 0, 480, 1, -1); // x,y,z  z is one b/c 2D
		glMatrixMode(GL_MODELVIEW);
		
		StateDrawer.drawMainMenu("");
		
		// while not closed
		while (!Display.isCloseRequested()) {
			if (Main.exitRequest) { // check if a reason exists to close
				System.out.println("SHUTDOWN: Main display thread is exiting..");
				break;
			}
			
			if (States.getState() == 2) {  // 2:paused
				StateDrawer.drawPauseMenu();
				if (States.checkPlay()) {
					glClear(GL_COLOR_BUFFER_BIT);
				}
				Display.sync(5); // framerate
			}
			else if (States.getState() == 1){  // 1:playing
				glClear(GL_COLOR_BUFFER_BIT);
				States.checkPaused();
				Movement.check();
				
				//ServerConnection.receiveOnlinePlayers();
				
				// draw players in the online list (constantly updated)
				Player.loadTexture();
				for (Player player : Player.onlinePlayers) {
					if (player != null)
						player.draw();
				}
				Player.deleteTexture();
				
				// draw creatures from server
				Creature.loadTexture();
				for (Creature creature : Creature.CreatureList) {
					if (creature != null)
						creature.drawNPC();
				}
				Creature.deleteTexture();
				
				Display.sync(10); // framerate
			}
			else {  // 0 just started client
				States.setState(1);
				ThreadHandler.initAll(); // start threads
				
				Display.sync(5);
				//MainMenu.run();
			}
			Display.update(); // update window if something changes	
		}
		StateDrawer.deleteTexture();
		Display.destroy();
	}
}