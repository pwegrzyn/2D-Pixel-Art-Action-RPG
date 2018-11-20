package pl.edu.agh.to2.yadc.entity;

public abstract class Projectile extends Entity {
	int velocity = 0;
	
	int physicalDmg = 0;
	int magicDmg = 0;
	Entity owner = null;

	public Projectile(double xInit, double yInit, double collisionRadius) {
		super(xInit, yInit, collisionRadius);
		
	}
}
