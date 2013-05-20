import org.lwjgl.input.Keyboard;

public class States {
	private static int state = 0; //  0:main menu, 1:playing, 2:paused
	
	public static void setState(int newState) {
		state = newState;
	}
	
	public static int getState(){
		return state;
	}
	
	public static void checkPaused() {
		if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
			States.setState(2);
		}
	}
	
	public static boolean checkPlay() {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			States.setState(1);
			return true;
		}
		else {
			return false;
		}
	}
}