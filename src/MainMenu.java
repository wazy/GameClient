import static org.lwjgl.opengl.GL11.*;
import java.sql.SQLException;
import org.lwjgl.input.Mouse;

public class MainMenu {
	
	public static void run() throws SQLException {
		// TODO put this in its own class.. MainMenu.java
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
		}
	}
}
