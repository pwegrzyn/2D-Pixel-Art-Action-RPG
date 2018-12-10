package pl.edu.agh.to2.yadc.entity;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class ProjectileFactory {
	public static Projectile createNormalArrow(Entity owner, double collisionRadius, BufferedImage texture) {
		Projectile newProjectile = new Projectile(owner, collisionRadius, new LinkedList<>());
		newProjectile.setTexture(texture);
		return newProjectile;
	}
	
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
		Projectile newProjectile = new Projectile(owner, collisionRadius, actionList);
		newProjectile.setTexture(texture);
		return newProjectile;
	}
}
