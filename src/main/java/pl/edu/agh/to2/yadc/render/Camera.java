package pl.edu.agh.to2.yadc.render;

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
	
	public void moveTo(int newX, int newY) {
		this.xPos = newX;
		this.yPos = newY;
	}

	// Potentially useless? :feels:
	public void approach(int x, int y, int approachSpeed) {

		if (x > this.xPos) {
			this.xPos += approachSpeed;
		} else if (x < this.xPos) {
			this.xPos -= approachSpeed;
		}
		if (y > this.yPos) {
			this.yPos += approachSpeed;
		} else if (y < this.yPos) {
			this.yPos -= approachSpeed;
		}

	}
}
