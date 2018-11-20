package pl.edu.agh.to2.yadc.entity;

public class TestProjectile extends Projectile{

	public TestProjectile(Entity owner, double collisionRadius) {
		super(owner.getXPos()+10, owner.getYPos(), collisionRadius);
		this.velocity = 100;
		
	}

	@Override
	public void advanceSelf(double delta) {
		this.xPos += 30*delta;
	}

	@Override
	public void performCollisionAction(Entity entity) {
		System.out.println("BANG");
	}

}
