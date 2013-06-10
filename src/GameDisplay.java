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
		NPC.NPCs.add(new NPC(0,"Dan",200,200,true));
		// while not closed
		while (!Display.isCloseRequested()) {
			// clear screen
			//glClear(GL_COLOR_BUFFER_BIT);
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
				for (Player player : Player.onlinePlayers) {
					if (player != null)
						player.draw();
				}
				Display.sync(10); // framerate
				NPC.loadTexture();
				for (NPC npc : NPC.NPCs) {
					if (npc != null)
						npc.drawNPC();
				}
			}
			else {  // 0:main menu
				Display.sync(5);
				MainMenu.run();
				/*
				String playerInfo[] = null;
				SimpleText.drawString("Welcome to Game", 260, 300);
				// if within the box dimensions
				if (Mouse.isButtonDown(0) && StateDrawer.inBoundsMainMenu(Mouse.getX(), 480 - Mouse.getY())) {
					if ((playerInfo = ServerConnection.authenticate()) != null) { //successful login
						// add client player -- {id, name, x, y}
						Player.onlinePlayers.add(new Player(Integer.parseInt(playerInfo[0].trim()), playerInfo[1].trim(),
								Integer.parseInt(playerInfo[2].trim()), Integer.parseInt(playerInfo[3].trim())));
						States.setState(1);
						Player.loadTexture();
						StateDrawer.loadTexture();
						glClear(GL_COLOR_BUFFER_BIT); // wipe screen
					}
					else {
						glClear(GL_COLOR_BUFFER_BIT);
						StateDrawer.drawMainMenu("");
						SimpleText.drawString("Welcome to Game", 260, 300);
						SimpleText.drawString("Login failed", 275, 285);
						SimpleText.drawString("Perhaps start server", 255, 175);
					}
				} */
			} 
			Display.update(); // update window if something changes	
		}
		Player.deleteTexture();
		StateDrawer.deleteTexture();
		Display.destroy();
	}
}