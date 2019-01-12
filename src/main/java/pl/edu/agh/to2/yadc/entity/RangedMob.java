package pl.edu.agh.to2.yadc.entity;

import java.awt.image.BufferedImage;

public class RangedMob extends Mob {
	
	private int attackCooldown = 0;
    private long lastAttackTime = 0;
    protected ProjectileTypes activeProjectile = ProjectileTypes.ENEMY;
    
	public RangedMob(double xInit, double yInit, double collisionRadius) {
		super(xInit, yInit, collisionRadius);
		this.velocity = 110;
		this.aggresive = false;
		this.velocity0 = velocity;
	}

	@Override
	public void makeAttack() {
		if(aggresive) {
			if (this.lastAttackTime == 0 || this.lastAttackTime + this.attackCooldown < System.currentTimeMillis()) {
			   	Projectile bullet = ProjectileFactory.createProjectile(activeProjectile, this, 5);
			   	this.lastAttackTime = System.currentTimeMillis();
			   	this.attackCooldown = 500;
				if(this.activeProjectile == ProjectileTypes.ENEMY) this.area.addEntity(bullet);
				else {
					for(Projectile p: ((MultipleProjectile)bullet).getProjectiles()) {
						this.area.addEntity(p);
					}
				}
			}
		}
	}
	
	public void setActiveProjectile(ProjectileTypes type) {
		this.activeProjectile = type;
	}
	
}
