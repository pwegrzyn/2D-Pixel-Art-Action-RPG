package pl.edu.agh.to2.yadc.entity;

import java.awt.image.BufferedImage;

public class MobFactory {
	public Mob createMeleeMob(double xInit, double yInit, double collisionRadius, BufferedImage texture) {
		MeleeMob newMob = new MeleeMob(xInit, yInit, collisionRadius);
		newMob.setTexture(texture);
		newMob.getStatManager().setHealth(400);
		newMob.getStatManager().setBaseHealth(400);
		newMob.setExp(100);
		return newMob;
	}
	
	public Mob createRangedMob(double xInit, double yInit, double collisionRadius, BufferedImage texture, BufferedImage projectileTexture) {
		RangedMob newMob = new RangedMob(xInit, yInit, collisionRadius);
		newMob.setTexture(texture);
		newMob.setProjectileTexture(projectileTexture);
		newMob.getStatManager().setHealth(400);
		newMob.getStatManager().setBaseHealth(400);
		newMob.setExp(200);
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
