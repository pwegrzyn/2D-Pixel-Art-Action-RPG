package pl.edu.agh.to2.yadc.entity;

import java.awt.image.BufferedImage;

public class RangedMob extends Mob {
	
	private int attackCooldown = 0;
    private long lastAttackTime = 0;
    private BufferedImage projectileTexture;
    private int range = 100;  //hardcoded for test
    
	public RangedMob(double xInit, double yInit, double collisionRadius) {
		super(xInit, yInit, collisionRadius);
		this.velocity = 80;
		this.aggresive = false;
	}

	@Override
	public void makeAttack() {
		if(aggresive) {
			if (this.lastAttackTime == 0 || this.lastAttackTime + this.attackCooldown < System.currentTimeMillis()) {
			   	Projectile bullet = ProjectileFactory.createNormalArrow(this, 5, this.projectileTexture);
			   	this.lastAttackTime = System.currentTimeMillis();
			   	this.attackCooldown = 500;
				this.area.addEntity(bullet);
			}
		}
	}

	public void setProjectileTexture(BufferedImage fetchImage) {
		this.projectileTexture = fetchImage;
	}
}
