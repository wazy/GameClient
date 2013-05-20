import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import de.matthiasmann.twl.utils.PNGDecoder;

public class TextureLoader {
	public static int setupTextures(String filename) {
	    IntBuffer tmp = BufferUtils.createIntBuffer(1);
	    GL11.glGenTextures(tmp);
	    tmp.rewind();
	    try {
	        InputStream in = new FileInputStream(filename);
	        PNGDecoder decoder = new PNGDecoder(in);

	        //System.out.println("width=" + decoder.getWidth());
	        //System.out.println("height=" + decoder.getHeight());

	        ByteBuffer buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
	        decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
	        buf.flip();

	        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tmp.get(0));
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
	                GL11.GL_NEAREST);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
	                GL11.GL_NEAREST);
	        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 4);
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
	        //int unsigned = (buf.get(0) & 0xff);
	        //System.out.println(unsigned);
	        //System.out.println(buf.get(1));
	        //System.out.println(buf.get(2));
	        //System.out.println(buf.get(3));

	    } catch (java.io.FileNotFoundException ex) {
	        System.out.println("Error " + filename + " not found");
	    } catch (java.io.IOException e) {
	        System.out.println("Error decoding " + filename);
	    }
	    tmp.rewind();
	    return tmp.get(0);
	}
}
