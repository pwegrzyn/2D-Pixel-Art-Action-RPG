package pl.edu.agh.to2.yadc.entity;

import java.util.LinkedList;
import java.util.List;

public class TestProjectile extends Projectile{

	public TestProjectile(Entity owner, double collisionRadius) {
		super(owner.getXPos(), owner.getYPos(), collisionRadius);
		this.velocity = 300;
		this.owner = owner;
		this.angularRotation = owner.angularRotation;
		
		this.spreadingActions = getSpreadingEffects();
	}
	
	private List<Action> getSpreadingEffects(){
		List<Action> actionList = new LinkedList<>();
		actionList.add(new Action(Mob.class, entity -> {
			Mob mob = (Mob)entity;
	//		mob.area.removeEntity(mob);
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
		
		return actionList;
	}

}
