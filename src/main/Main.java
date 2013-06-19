package main;
import org.lwjgl.LWJGLException;

public class Main {
	public static boolean exitRequest = false;
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, LWJGLException {
		try {
			LoginFrontEnd.init();
			while (true) {;}
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		finally {
			exitRequest = true;
		}
	}

}
