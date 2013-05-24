import org.lwjgl.LWJGLException;

public class Main {
	public static boolean exitRequest = false;
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, LWJGLException {
		try {
			GameDisplay.run();
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(1);// if uou look at both client windows you will see they both updated
			// where are the client windows press alt+tab? check other client notice how 2 is still there? yeah
			// because its not implemented the removal yet here watch the code..
		}
		finally {
			exitRequest = true;
		}
	}

}
