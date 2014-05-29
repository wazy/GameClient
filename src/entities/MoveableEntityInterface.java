package entities;

public interface MoveableEntityInterface extends EntityInterface {
	public double getDX();
	public double getDY();
	public int getMass();
	
	public void setDX(double dx);
	public void setDY(double dy);
	public void setMass(int newMass);
	
	public void updateXvel(int dvx);
	public void updateYvel(int dvy);
	public void updateY(int y);
}
