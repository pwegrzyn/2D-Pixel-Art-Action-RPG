package pl.edu.agh.to2.yadc.entity;

import java.util.LinkedList;
import java.util.List;

public class Projectile extends Entity {

	protected int velocity = 0;
	protected int physicalDmg = 20;
	protected int magicDmg = 10;
	protected Entity owner = null;
	protected double coveredDistance;
	protected ProjectileTypes type;
	
	public Projectile(ProjectileTypes type, Entity owner, double collisionRadius) {
		super(owner.getXPos(), owner.getYPos(), collisionRadius);
		this.velocity = 300;
		this.owner = owner;
		this.angularRotation = owner.angularRotation;
		this.spreadingActions = new LinkedList<Action>();
		this.coveredDistance = 0;
		this.type = type;
	}

	public Entity getOwner() {
		return this.owner;
	}

	@Override
	public void advanceSelf(double delta) {
		this.xPos += Math.cos(this.angularRotation) * delta * this.velocity;
		this.yPos += Math.sin(this.angularRotation) * delta * this.velocity;
		this.coveredDistance += 15*delta;
		if(owner instanceof Player && this.coveredDistance >= ((Player)owner).getStatManager().getRange()) {
			area.removeEntity(this);
		}
		if(owner instanceof Mob && this.coveredDistance >= ((Mob)owner).getStatManager().getRange())
			area.removeEntity(this);
	}
	
	public void setPhysicalDmg(int dmg) {
		this.physicalDmg = dmg;
	}
	
	public void setMagicDmg(int dmg) {
		this.magicDmg = dmg;
	}
	
	public void addEffects(List<Action> effects) {
		this.spreadingActions.addAll(effects);
	}

	public int getPhysicalDmg() {
		return this.physicalDmg;
	}
	
	public int getMagicDmg() {
		return this.magicDmg;
	}
	
}
