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
		
		continueStraightRoam(delta);

	}

	@Override
	public void performCollisionAction(Entity entity) {
		// TODO Auto-generated method stub
		
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
}
