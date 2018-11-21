package pl.edu.agh.to2.yadc.entity;

public class TestProjectile extends Projectile{

	public TestProjectile(Entity owner, double collisionRadius) {
		super(owner.getXPos()+10, owner.getYPos()+10, collisionRadius);
		this.velocity = 100;
		this.owner = owner;
		this.angularRotation = owner.angularRotation;
		
	}

	@Override
	public void advanceSelf(double delta) {
		this.xPos += Math.cos(this.angularRotation) * delta * this.velocity;
		this.yPos += Math.sin(this.angularRotation) * delta * this.velocity;
	}

	@Override
	public void performCollisionAction(Entity entity) {
		System.out.println("BANG");
	}

}
