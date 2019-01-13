package pl.edu.agh.to2.yadc.entity;

import java.awt.image.BufferedImage;

public class MobFactory {
	public Mob createMeleeMob(double xInit, double yInit, double collisionRadius, BufferedImage texture, BufferedImage lootTexture) {
		MeleeMob newMob = new MeleeMob(xInit, yInit, collisionRadius);
		newMob.setTexture(texture);
		newMob.getStatManager().setHealth(400);
		newMob.getStatManager().setBaseHealth(400);
		newMob.setExp(100);
		newMob.setScore(1000);
		newMob.setLootTexture(lootTexture);
		return newMob;
	}

	public Mob createMeleeBoss(double xInit, double yInit, double collisionRadius, BufferedImage texture, BufferedImage lootTexture) {
		MeleeMob newMob = new MeleeMob(xInit, yInit, collisionRadius);
		newMob.setTexture(texture);
		newMob.getStatManager().setHealth(2500);
		newMob.getStatManager().setBaseHealth(2500);
		newMob.setExp(5000);
		newMob.setScore(20000);
		newMob.setLootTexture(lootTexture);
		newMob.setPhysicalDmg(150);
		newMob.setMagicDmg(50);
		newMob.setAggresive(false);
		return newMob;
	}
	
	public Mob createRangedBoss(double xInit, double yInit, double collisionRadius, BufferedImage texture, BufferedImage lootTexture) {
		RangedMob newMob = new RangedMob(xInit, yInit, collisionRadius);
		newMob.setActiveProjectile(ProjectileTypes.MULTIPLE);
		newMob.setTexture(texture);
		newMob.getStatManager().setHealth(2500);
		newMob.getStatManager().setBaseHealth(2500);
		newMob.setExp(5000);
		newMob.setScore(20000);
		newMob.setLootTexture(lootTexture);
		newMob.setAggresive(false);
		return newMob;
	}
	
	public Mob createRangedMob(double xInit, double yInit, double collisionRadius, BufferedImage texture, BufferedImage projectileTexture, BufferedImage lootTexture) {
		RangedMob newMob = new RangedMob(xInit, yInit, collisionRadius);
		newMob.setTexture(texture);
		newMob.getStatManager().setHealth(500);
		newMob.getStatManager().setBaseHealth(500);
		newMob.setExp(150);
		newMob.setScore(1500);
		newMob.setLootTexture(lootTexture);
		return newMob;
	}
	
	public Mob createFlyingMob(double xInit, double yInit, double collisionRadius, BufferedImage texture) {
		MeleeMob newMob = new MeleeMob(xInit, yInit, collisionRadius);
		newMob.setTexture(texture);
		newMob.getStatManager().setHealth(400);
		newMob.getStatManager().setBaseHealth(400);
		return newMob;
	}
	
	//tylko testowo
	/*public Mob createTestMob(double xInit, double yInit, double collisionRadius, BufferedImage texture, BufferedImage projectileTexture) {
		TestEnemy newMob = new TestEnemy(xInit, yInit, collisionRadius);
		newMob.setTexture(texture);
		newMob.setProjectileTexture(projectileTexture);
		newMob.getStatManager().setHealth(400);
		newMob.getStatManager().setBaseHealth(400);
		return newMob;
	}*/
}
