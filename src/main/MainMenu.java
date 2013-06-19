package main;
import static org.lwjgl.opengl.GL11.*;
import java.sql.SQLException;
import org.lwjgl.input.Mouse;

public class MainMenu {

	public static void run() throws SQLException {
		SimpleText.drawString("Welcome to Game", 260, 300);
		// if within the box dimensions
		if (Mouse.isButtonDown(0) && StateDrawer.inBoundsMainMenu(Mouse.getX(), 480 - Mouse.getY())) {
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
