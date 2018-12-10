package pl.edu.agh.to2.yadc.entity;

import java.util.LinkedList;
import java.util.List;

public class Projectile extends Entity {

	protected int velocity = 0;
	protected int physicalDmg = 30;
	protected int magicDmg = 15;
	protected Entity owner = null;
	protected int coveredDistance;
	
	public Projectile(Entity owner, double collisionRadius, List<Action> spreadingEffects) {
		super(owner.getXPos(), owner.getYPos(), collisionRadius);
		this.velocity = 300;
		this.owner = owner;
		this.angularRotation = owner.angularRotation;
		this.spreadingActions = spreadingEffects;
		this.coveredDistance = 0;
	}

	public Entity getOwner() {
		return this.owner;
	}

	@Override
	public void advanceSelf(double delta) {
		this.xPos += Math.cos(this.angularRotation) * delta * this.velocity;
		this.yPos += Math.sin(this.angularRotation) * delta * this.velocity;
		this.coveredDistance += 100*delta;
		if(owner instanceof Player && this.coveredDistance >= ((Player)owner).getStatManager().getRange())
			area.removeEntity(this);
		//System.out.println(delta + "  " + this.coveredDistance + "  " + ((Player)owner).getStatManager().getRange());
		if(owner instanceof Mob && this.coveredDistance >= ((Mob)owner).getStatManager().getRange())
			area.removeEntity(this);
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
