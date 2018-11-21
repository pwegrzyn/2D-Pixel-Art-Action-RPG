package pl.edu.agh.to2.yadc.entity;

public class TestProjectile extends Projectile{

	public TestProjectile(Entity owner, double collisionRadius) {
		super(owner.getXPos(), owner.getYPos(), collisionRadius);
		this.velocity = 300;
		this.owner = owner;
		this.angularRotation = owner.angularRotation;
		
	}

}
