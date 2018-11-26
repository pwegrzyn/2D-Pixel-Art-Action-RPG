package pl.edu.agh.to2.yadc.entity;

public class TestEnemy extends Mob{

	private double roamingTimer;
	
	public TestEnemy(double xInit, double yInit, double collisionRadius) {
		super(xInit, yInit, collisionRadius);
		this.roamingTimer = 0;
		this.velocity = 80;
	}

	@Override
	public void advanceSelf(double delta) {
		
		//continueStraightRoam(delta);
		approachPlayer(delta);

	}

	@Override
	public void performCollisionAction(Entity entity) {
		
		if(entity instanceof TestProjectile) {
			this.area.removeEntity(this);
		}
		
	}

	private void continueStraightRoam(double delta) {
		this.roamingTimer += delta;
		if(this.roamingTimer <= 2) {
			this.xPos += this.velocity * delta;
		} else if(this.roamingTimer > 2 && this.roamingTimer <= 4) {
			this.xPos -= this.velocity * delta;
		} else {
			this.roamingTimer = 0;
		}
	}

	private void approachPlayer(double delta) {
		int playerXPos = (int) this.area.getPlayer().getXPos();
		int playerYPos = (int) this.area.getPlayer().getYPos();
		int newXdirection = this.xPos > playerXPos ? -1 : 1;
		int newYdirection = this.yPos > playerYPos ? -1 : 1;
		this.xPos += newXdirection * this.velocity * delta;
		this.yPos += newYdirection * this.velocity * delta;
	}
}
