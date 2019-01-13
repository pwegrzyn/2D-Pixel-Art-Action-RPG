package pl.edu.agh.to2.yadc.entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import pl.edu.agh.to2.yadc.config.GlobalConfig;
public class ProjectileFactory {

	private static Random random = new Random();
	private static BufferedImage normalProjectileTexture;
	private static BufferedImage slowingProjectileTexture;
	private static BufferedImage stunningProjectileTexture;
	private static BufferedImage mobProjectileTexture;
	
	public static void setNormalProjectileTexture(BufferedImage fetchImage) {
		normalProjectileTexture = fetchImage;
	}
	
	public static void setSlowingProjectileTexture(BufferedImage fetchImage) {
		slowingProjectileTexture = fetchImage;
	}
	
	public static void setStunningProjectileTexture(BufferedImage fetchImage) {
		stunningProjectileTexture = fetchImage;
	}
	
	public static void setMobProjectileTexture(BufferedImage fetchImage) {
		mobProjectileTexture = fetchImage;
	}
	
	public static Projectile createProjectile(ProjectileTypes type, Entity owner, double collisionRadius) {
		if(type == ProjectileTypes.NORMAL || type == ProjectileTypes.ENEMY) return createNormalProjectile(type, owner, collisionRadius, normalProjectileTexture);
		else if(type == ProjectileTypes.SLOWING) return createSlowingProjectile(owner, collisionRadius, slowingProjectileTexture);
		else if(type == ProjectileTypes.STUNNING) return createStunningProjectile(owner, collisionRadius, stunningProjectileTexture);
		else if(type == ProjectileTypes.TRIPLE) return createTripleProjectile(owner, collisionRadius);
		else if(type == ProjectileTypes.MULTIPLE) return createMultipleProjectile(owner, collisionRadius);
		else return null;
	}

	public static Projectile createNormalProjectile(ProjectileTypes type, Entity owner, double collisionRadius, BufferedImage texture) {
		Projectile newProjectile = new Projectile(type, owner, collisionRadius);
		newProjectile.setTexture(texture);
		newProjectile.setPhysicalDmg(100);
		newProjectile.setMagicDmg(0);
		
		List<Action> actionList = new LinkedList<>();
		actionList.add(new Action(Projectile.class, entity -> {
			Projectile projectile = (Projectile)entity;
				if(newProjectile.owner != projectile.owner && projectile.type != ProjectileTypes.SLOWING && projectile.type != ProjectileTypes.STUNNING) 
					owner.area.removeEntity(entity);
		}));
		actionList.add(new Action(Player.class, entity -> {
			Player player = (Player)entity;
			player.getStatManager().setHealth(player.getStatManager().getCurrentHealth() - newProjectile.physicalDmg - newProjectile.magicDmg);
			newProjectile.area.removeEntity(newProjectile);
		}));
		actionList.add(new Action(Mob.class, entity -> {
			Mob mob = (Mob)entity;
			mob.aggresive = true;
			if(newProjectile.getOwner() != mob) {
				mob.getStatManager().setHealth(mob.getStatManager().getCurrentHealth()-newProjectile.physicalDmg-newProjectile.magicDmg);
				// If a mob dies from an player-originated projectile (this logic probably shouldnt be in the ProjectileFactory class, future refactoring: move to Mob class)
				if(mob.getStatManager().getCurrentHealth()<=0) {
					if (newProjectile.getOwner() instanceof Player) {
						((Player)newProjectile.getOwner()).addExp(mob.exp);
						((Player) newProjectile.getOwner()).addScore(mob.score);
						((Player)newProjectile.getOwner()).checkQuestsProgress(entity);
						// 1/5 chance of dropping loot
						if(random.nextInt(5) == 0) {
							Loot droppedLoot = new Loot((int) mob.getXPos() + random.nextInt(20),(int) mob.getYPos() + random.nextInt(20));
							droppedLoot.setArea(mob.area);
							droppedLoot.setTexture(mob.getLootTexture());
							mob.area.addEntity(droppedLoot);
							GlobalConfig.get().printToChatBox("The mob has dropped something!");
						}
					}
					mob.area.removeEntity(mob);
				}
				newProjectile.area.removeEntity(newProjectile);
			}
		}));
		
		newProjectile.addEffects(actionList);
		return newProjectile;
	}
	
	public static Projectile createSlowingProjectile(Entity owner, double collisionRadius, BufferedImage texture) {
		Projectile newProjectile = createNormalProjectile(ProjectileTypes.SLOWING, owner, collisionRadius, texture);
		newProjectile.setMagicDmg(50);
		
		Player player = (Player)owner;
		if(player.getStatManager().getCurrentMana()>=10) {
			player.getStatManager().setMana(player.getStatManager().getCurrentMana()-10);
			List<Action> actionList = new LinkedList<>();
			actionList.add(new Action(Mob.class, entity -> {
				Mob mob = (Mob)entity;
				mob.activeEffects.add(new Effect(2000, 
						ent -> {
							Mob mob1 = (Mob)ent;
							if(mob1.velocity0/3 < mob1.velocity) mob1.velocity = mob1.velocity0/3;
							mob.slowed = true;
						},
						ent -> {
							Mob mob1 = (Mob)ent;
							mob1.velocity = mob1.velocity0;
							mob.slowed = false;
						}
				));
			}));
			newProjectile.addEffects(actionList);
		}
		else player.setActiveProjecile(ProjectileTypes.NORMAL);
		return newProjectile;
	}
	
	private static Projectile createStunningProjectile(Entity owner, double collisionRadius, BufferedImage texture) {
		Projectile newProjectile = createNormalProjectile(ProjectileTypes.STUNNING, owner, collisionRadius, texture);
		newProjectile.setMagicDmg(100);
		
		Player player = (Player)owner;
		if(player.getStatManager().getCurrentMana()>=300) {
			player.getStatManager().setMana(player.getStatManager().getCurrentMana()-300);
			List<Action> actionList = new LinkedList<>();
			actionList.add(new Action(Mob.class, entity -> {
				Mob mob = (Mob)entity;
				mob.activeEffects.add(new Effect(2000, 
						ent -> {
							Mob mob1 = (Mob)ent;
							mob1.velocity = 0;
						},
						ent -> {
							Mob mob1 = (Mob)ent;
							mob1.velocity = mob1.velocity0;
						}
				));
			}));
			newProjectile.addEffects(actionList);
		}
		else player.setActiveProjecile(ProjectileTypes.NORMAL);
		return newProjectile;
	}
	
	private static Projectile createTripleProjectile(Entity owner, double collisionRadius) {
		Player player = (Player)owner;
		Projectile newProjectile;
		if(player.getStatManager().getCurrentMana()>=200) {
			player.getStatManager().setMana(player.getStatManager().getCurrentMana()-200);
			newProjectile = (Projectile) new MultipleProjectile(ProjectileTypes.NORMAL, owner, collisionRadius, 3);
		}
		else {
			newProjectile = createProjectile(ProjectileTypes.NORMAL, owner, collisionRadius);
			player.setActiveProjecile(ProjectileTypes.NORMAL);
		}
		return newProjectile;
	}
	
	private static Projectile createMultipleProjectile(Entity owner, double collisionRadius) {
		MultipleProjectile newProjectile = new MultipleProjectile(ProjectileTypes.ENEMY, owner, collisionRadius, 8);
		return newProjectile;
	}
}
