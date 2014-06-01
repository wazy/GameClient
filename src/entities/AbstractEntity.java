package entities;

import handlers.ResourceHandler;

import java.io.Serializable;

import org.newdawn.slick.opengl.Texture;

import utils.OGLRenderer;

public abstract class AbstractEntity implements EntityInterface, Serializable {

	private static final long serialVersionUID = -8405971951484157839L;
	
	protected Texture texture;
	protected boolean falling = false;
	protected String name, textureName;
	protected int id, x, y, width, height, textureID;
	
	public AbstractEntity(int id, String name, int x, int y, int width, int height, String textureName) {
		this.id = id;
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.textureName = textureName;
		this.texture = ResourceHandler.getTexture(textureName);
	}

	public boolean isFalling() {
		return this.falling;
	}

	public void isFalling(boolean value) {
		this.falling = value;
	}

	public String getTextureName() {
		return this.textureName;
	}
	
	// Do nothing for now: stationary object
	public void update(int delta) {
		return;
	}

	public void draw() {
		OGLRenderer.drawQuad(x, y, width, height, name, texture);
	}
	
	@Override
	public void teleport(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void fly(double flyValue) {
		// Make magic happen.
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public void setID(int id) {
		this.id = id;
	}

	@Override
	public void setName(String newName) {
		this.name = newName;
	}
	
	@Override
	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Texture getTexture() {
		return texture;
	}
}
