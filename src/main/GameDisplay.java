package main;
import static org.lwjgl.opengl.GL11.*;

import java.sql.SQLException;

import org.lwjgl.opengl.*;
import org.lwjgl.*;

public class GameDisplay {
	public static volatile boolean drawProjectile = false;

	public static void run() throws SQLException {
		int k = 0;
		DisplayMode x;
		try {
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
			else if (States.getState() == 1) {  // 1:playing
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
				for (Creature creature : Creature.creatureList) {
					if (creature != null)
						creature.drawNPC();
				}
				Creature.deleteTexture();

				if (GameDisplay.drawProjectile) {
//					 System.out.println(Spell.spellMap.size());
					if (k < Spell.spellMap.size()) {  // drawing parts
						Spell spell = Spell.spellMap.get(k);
						if (spell != null) {
							// System.out.println(spell.getX() + " " + spell.getY());
							spell.drawSpell();
						}
						k++;
					}
					else { // all parts have been drawn -- clear to cast again
						int x2 = Spell.spellMap.get(Spell.spellMap.size() - 1).getX();
						int y2 = Spell.spellMap.get(Spell.spellMap.size() - 1).getY();
						System.out.println(x2 + " " + y2);
						
						// temp to show final spell coordinates
						glBegin(GL_QUADS);
							glTexCoord2f(0.0f, 0.0f);
							glVertex2f(x2, y2);
							glTexCoord2f(1.0f, 0.0f);
							glVertex2f(x2+50, y2);
							glTexCoord2f(1.0f, 1.0f);
							glVertex2f(x2+50, y2+50);
							glTexCoord2f(0.0f, 1.0f);
							glVertex2f(x2, y2+50);
						glEnd();
						
						GameDisplay.drawProjectile = false;
						k = 0;
					}
				}
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
		Main.exitRequest = true;	// tell everything to terminate
	}
}