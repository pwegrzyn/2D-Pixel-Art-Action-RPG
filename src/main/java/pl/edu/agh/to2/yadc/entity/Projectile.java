package pl.edu.agh.to2.yadc.entity;

public abstract class Projectile extends Entity {

	protected int velocity = 0;
	protected int physicalDmg = 2;
	protected int magicDmg = 1;
	protected Entity owner = null;

	public Projectile(double xInit, double yInit, double collisionRadius) {
		super(xInit, yInit, collisionRadius);
		
	}

	public Entity getOwner() {
		return this.owner;
	}

	@Override
	public void advanceSelf(double delta) {
		this.xPos += Math.cos(this.angularRotation) * delta * this.velocity;
		this.yPos += Math.sin(this.angularRotation) * delta * this.velocity;
	}

	// @Override
	// public void performCollisionAction(Entity entity) {
	// 	if(entity instanceof Player) {
	// 		return;
	// 	}
	// 	if(entity instanceof Mob) {
	// 		this.area.removeEntity(this);
	// 	}
		
	// 	if (this.owner != entity) {
	// 		for (Effect effect : entity.spreadingEffects) {
	// 			effect.activate(this);
	// 		}
	// 	}
	// }
}
