package entities;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Player extends AbstractMoveableEntity {

	public static Texture texture = null;

	public static int playerID = -1;
	public static volatile int playerHealth = 5;

	public static AtomicInteger listPosition = new AtomicInteger(-1);

	private static final long serialVersionUID = -8405971951484157839L;

	private static final int PLAYER_WIDTH = 50;
	private static final int PLAYER_HEIGHT = 50;

	private static File playerImg = new File("./img/etc2.png");

	public static volatile List<Player> onlinePlayers =	Collections.synchronizedList(
																new ArrayList<Player>(16));
		
	public Player (int id, String name, int x, int y, int textureID) {
		super(id, name, x, y, PLAYER_WIDTH, PLAYER_HEIGHT, textureID);
	}

	public static void loadTexture() throws IOException {
		texture = TextureLoader.getTexture("PNG", new FileInputStream(playerImg));
	}


	public static void deleteTexture() {
		texture.release();
	}

	// finds where the player's info is -- from the server
	public static int findPlayerPosInList(int previousPos) {
		
		// no need to reiterate -- position didn't change
		if ((previousPos >= 0) && (previousPos < onlinePlayers.size()) 
				&& (onlinePlayers.get(previousPos).getID() == previousPos))
			return previousPos;
		
		for (int i = 0; i < onlinePlayers.size(); i++) {
			if (onlinePlayers.get(i).getID() == playerID) {
				listPosition.set(i);
				return i;
			}
		}
		return -1;
	}

	public static void setPlayerID(int playerIDFromServer) {
		playerID = playerIDFromServer;
	}

	public static int getPlayerID() {
		return playerID;
	}

	public static void setOnlinePlayers(List<Player> onlinePlayersFromServer) {
		onlinePlayers = onlinePlayersFromServer;
	}
	
	public static List<Player> getOnlinePlayers() {
		return onlinePlayers;
	}

	public static AtomicInteger getListPosition() {
		return listPosition;
	}

	public static void setListPosition(int positionFromServer) {
		listPosition.set(positionFromServer);
	}
	
}