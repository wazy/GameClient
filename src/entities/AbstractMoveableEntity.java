package entities;


public abstract class AbstractMoveableEntity extends AbstractEntity implements MoveableEntityInterface {
	
	private static final long serialVersionUID = -8405971951484157839L;

	protected double dx, dy;
	protected int x, y, vx, vy, mass;

	public AbstractMoveableEntity(int id, String name, int x, int y, int width, int height) {
		super(id, name, x, y, width, height);
		this.dx = 0;
		this.dy = 0;
		this.vx = 0;
		this.vy = 0;
		this.mass = 1;
	}

	public void updateX(int x) {
		this.x += x;
	}

	public void updateY(int y) {
		this.y += y;
	}

	public void updateXY(int x, int y) {
		this.x += x;
		this.y += y;
	}

	public void updateYvel(int dvy) {
		this.vy +=dvy;
	}

	public void updateXvel(int dvx) {
		this.vx +=dvx;
	}

	public void setMass(int newmass) {
		this.mass = newmass;
	}
	
	public int getMass() {
		return this.mass;
	}

	public void update(int delta) {
		this.x += delta * dx;
		this.y += delta * dy;
	}

	public double getDX() {
		return dx;
	}

	public double getDY() {
		return dy;
	}

	public void setDX(double dx) {
		this.dx = dx;
	}

	public void setDY(double dy) {
		this.dy = dy;
	}	
}
