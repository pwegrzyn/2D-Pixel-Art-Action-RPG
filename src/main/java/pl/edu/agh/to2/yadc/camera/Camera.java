package pl.edu.agh.to2.yadc.camera;

public class Camera {
	private int xPos;
	private int yPos;
	
	public Camera(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;	
	}
	
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
	
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}
	
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
	public void move(int deltaX, int deltaY) {
		this.xPos += deltaX;
		this.yPos += deltaY;
	}
}
