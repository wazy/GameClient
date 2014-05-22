package entities;

public class PhysObject {
	int x;
	int y;
	int vx = 0;
	int vy = 0;

	public int getX() {
		return this.x;
	}
	
	public void setX(int X) {
		this.x = X;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setY(int Y) {
		this.y = Y;
	}
	
	public void updateX(int newXValue) {
		x += newXValue;
	}
	public void updateY(int newYValue) {
		y += newYValue;
	}
	public void updateXY(int newXValue, int newYValue) {
		x += newXValue;
		y += newYValue;
	}
	
	public void updateYvel(int dvy) {
		this.vy +=dvy;
	}
	
	public void updateXvel(int dvx) {
		this.vx +=dvx;
	}
}
