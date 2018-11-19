package pl.edu.agh.to2.yadc.entity;

public class TestEnemy extends Mob{

	public TestEnemy(double xInit, double yInit, double collisionRadius) {
		super(xInit, yInit, collisionRadius);
	}

	@Override
	public void advanceSelf(double delta) {
//		this.xPos -= 2* delta;
//		this.yPos -= 2 * delta;
	}

	@Override
	public void performCollisionAction(Entity entity) {
		// TODO Auto-generated method stub
		
	}
}
