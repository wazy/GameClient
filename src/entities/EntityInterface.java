package entities;

import org.newdawn.slick.opengl.Texture;

public interface EntityInterface {
	public void draw();
	public void update(int delta);
	public void fly(double flySpeed);
	public void teleport(int x, int y);
	//public boolean collides(Entity other);

	public void setX(int x);
	public void setY(int y);
	public void setWidth(int width);
	public void setHeight(int height);
	public void setID(int newID);
	public void setName(String newName);
	public void setTexture(Texture texture);

	public int getX();
	public int getY();
	public int getHeight();
	public int getWidth();
	public int getID();
	public String getName();
	public Texture getTexture();

}
