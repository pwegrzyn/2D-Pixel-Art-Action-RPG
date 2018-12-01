package pl.edu.agh.to2.yadc.entity;

public class MeleeMob extends Mob {
	public MeleeMob(double xInit, double yInit, double collisionRadius) {
		super(xInit, yInit, collisionRadius);
		this.velocity = 80;
		this.aggresive = false;
	}

	@Override
	public void makeAttack() {
		
	}
}
