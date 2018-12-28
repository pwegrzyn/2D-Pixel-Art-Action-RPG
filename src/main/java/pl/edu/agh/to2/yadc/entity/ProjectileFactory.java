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

	public static Projectile createNormalArrow(Entity owner, double collisionRadius, BufferedImage texture) {
		Projectile newProjectile = new Projectile(owner, collisionRadius);
		newProjectile.setTexture(texture);
		newProjectile.setPhysicalDmg(100);
		newProjectile.setMagicDmg(0);
		
		List<Action> actionList = new LinkedList<>();
		actionList.add(new Action(Projectile.class, entity -> {
			Projectile projectile = (Projectile)entity;
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
	
	//not working yet
	public static Projectile createSlowingArrow(Entity owner, double collisionRadius, BufferedImage texture) {
		List<Action> actionList = new LinkedList<>();
		actionList.add(new Action(Mob.class, entity -> {
			Mob mob = (Mob)entity;
			mob.activeEffects.add(new Effect(5000, 
					ent -> {
						Mob mob1 = (Mob)ent;
						mob1.velocity = 10;
					},
					ent -> {
						Mob mob1 = (Mob)ent;
						mob1.velocity = 150;
					}
			));
		}));
		actionList.add(new Action(Projectile.class, entity -> {
			Projectile projectile = (Projectile)entity;
			if (projectile.owner instanceof Mob) {
				owner.area.removeEntity(entity);
			}
			if (projectile.owner instanceof Player) {
				owner.area.removeEntity(entity);
			}
		}));
		Projectile newProjectile = new Projectile(owner, collisionRadius);
		newProjectile.setTexture(texture);
		newProjectile.setPhysicalDmg(50);
		newProjectile.setMagicDmg(100);
		return newProjectile;
	}
}
