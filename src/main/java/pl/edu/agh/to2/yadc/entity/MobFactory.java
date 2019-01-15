package pl.edu.agh.to2.yadc.entity;

import java.awt.image.BufferedImage;

import pl.edu.agh.to2.yadc.render.Animation;
import pl.edu.agh.to2.yadc.render.AnimationType;
import pl.edu.agh.to2.yadc.render.ImageLoader;

public class MobFactory {
	public Mob createMeleeMob(double xInit, double yInit) {
		MeleeMob newMob = new MeleeMob(xInit, yInit, 10);
		newMob.getStatManager().setHealth(400);
		newMob.getStatManager().setBaseHealth(400);
		newMob.setExp(100);
		newMob.setScore(1000);
		newMob.setLootTexture(ImageLoader.active.fetchImage("resources/loot.png"));
		Animation animation = new Animation(AnimationType.IDLE, new BufferedImage[] { 
					ImageLoader.active.fetchImage("resources/swampy_idle_anim_f0.png"),
					ImageLoader.active.fetchImage("resources/swampy_idle_anim_f1.png"),
					ImageLoader.active.fetchImage("resources/swampy_idle_anim_f2.png"),
					ImageLoader.active.fetchImage("resources/swampy_idle_anim_f3.png")	
		}, 0.1);
		newMob.assignAnimation(animation);
		newMob.pickAnimation(AnimationType.IDLE);
		return newMob;
	}

	public Mob createMeleeBoss(double xInit, double yInit) {
		MeleeMob newMob = new MeleeMob(xInit, yInit, 10);
		newMob.getStatManager().setHealth(2500);
		newMob.getStatManager().setBaseHealth(2500);
		newMob.setExp(5000);
		newMob.setScore(20000);
		newMob.setLootTexture(ImageLoader.active.fetchImage("resources/loot.png"));
		newMob.setPhysicalDmg(150);
		newMob.setMagicDmg(50);
		newMob.setAggresive(false);
		Animation animation = new Animation(AnimationType.IDLE, new BufferedImage[] { 
					ImageLoader.active.fetchImage("resources/big_zombie_idle_anim_f0.png"),
					ImageLoader.active.fetchImage("resources/big_zombie_idle_anim_f1.png"),
					ImageLoader.active.fetchImage("resources/big_zombie_idle_anim_f2.png"),
					ImageLoader.active.fetchImage("resources/big_zombie_idle_anim_f3.png")	
		}, 0.1);
		newMob.assignAnimation(animation);
		newMob.pickAnimation(AnimationType.IDLE);
		return newMob;
	}
	
	public Mob createRangedBoss(double xInit, double yInit) {
		RangedMob newMob = new RangedMob(xInit, yInit, 10);
		newMob.setActiveProjectile(ProjectileTypes.MULTIPLE);
		newMob.getStatManager().setHealth(2500);
		newMob.getStatManager().setBaseHealth(2500);
		newMob.setExp(5000);
		newMob.setScore(20000);
		newMob.setLootTexture(ImageLoader.active.fetchImage("resources/loot.png"));
		newMob.setAggresive(false);
		Animation animation = new Animation(AnimationType.IDLE, new BufferedImage[] { 
					ImageLoader.active.fetchImage("resources/big_demon_idle_anim_f0.png"),
					ImageLoader.active.fetchImage("resources/big_demon_idle_anim_f1.png"),
					ImageLoader.active.fetchImage("resources/big_demon_idle_anim_f2.png"),
					ImageLoader.active.fetchImage("resources/big_demon_idle_anim_f3.png")	
		}, 0.1);
		newMob.assignAnimation(animation);
		newMob.pickAnimation(AnimationType.IDLE);
		return newMob;
	}
	
	public Mob createRangedMob(double xInit, double yInit) {
		RangedMob newMob = new RangedMob(xInit, yInit, 10);
		newMob.getStatManager().setHealth(500);
		newMob.getStatManager().setBaseHealth(500);
		newMob.setExp(150);
		newMob.setScore(1500);
		newMob.setLootTexture(ImageLoader.active.fetchImage("resources/loot.png"));
		Animation animation = new Animation(AnimationType.IDLE, new BufferedImage[] { 
					ImageLoader.active.fetchImage("resources/zombie_idle_anim_f0.png"),
					ImageLoader.active.fetchImage("resources/zombie_idle_anim_f1.png"),
					ImageLoader.active.fetchImage("resources/zombie_idle_anim_f2.png"),
					ImageLoader.active.fetchImage("resources/zombie_idle_anim_f3.png")	
		}, 0.1);
		newMob.assignAnimation(animation);
		newMob.pickAnimation(AnimationType.IDLE);
		return newMob;
	}
	
}
