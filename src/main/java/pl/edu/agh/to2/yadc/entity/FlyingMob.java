package pl.edu.agh.to2.yadc.entity;

public class FlyingMob extends Mob{

	public FlyingMob(double xInit, double yInit, double collisionRadius) {
		super(xInit, yInit, collisionRadius);
		this.velocity = 80;
		this.aggresive = false;
	}

	@Override
	public void makeAttack() {
		// TODO Auto-generated method stub
		
	}

}
