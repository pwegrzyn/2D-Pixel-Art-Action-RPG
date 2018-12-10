package pl.edu.agh.to2.yadc.entity;

import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.to2.yadc.game.App;
import pl.edu.agh.to2.yadc.physics.CollisionEngine;

public class MeleeMob extends Mob {
	private int attackCooldown = 0;
    private long lastAttackTime = 0;
    
	public MeleeMob(double xInit, double yInit, double collisionRadius) {
		super(xInit, yInit, collisionRadius);
		this.velocity = 80;
		this.aggresive = false;
		this.statManager.setPhysicalDmg(50);
	}

	/*@Override
	public void advanceSelf(double delta) {
		if(aggresive)
			approachPlayer(delta);
	}*/
	
	@Override
	public void makeAttack() {
		if(aggresive) {
			if (this.spreadingActions.size() == 1 && this.lastAttackTime == 0 || this.lastAttackTime + this.attackCooldown < System.currentTimeMillis()) {
				if(this.spreadingActions.size()<2) addSpreadingEffects();
				this.lastAttackTime = System.currentTimeMillis();
			   	this.attackCooldown = 500;
			}
		}
	}
	
	@Override
	public void performCollisionAction(Entity entity) {
		if(entity instanceof Player) {
			aggresive = true;
		}
		super.performCollisionAction(entity);
	}
	
	private void addSpreadingEffects(){
		List<Action> actionList = new LinkedList<>();
		Action act = new Action(Player.class, entity -> {
			Player player = (Player) entity;
			player.getStatManager().setHealth(player.getStatManager().getCurrentHealth()-getPhysicalDmg()-getMagicDmg());
			if(player.getStatManager().getCurrentHealth()<=0) 
				App.quit();
		});
		actionList.add(act);
		this.spreadingActions.addAll(actionList);
	}
	
	public int getPhysicalDmg() {
		return this.getStatManager().getPhysicalDmg();
	}
	
	public int getMagicDmg() {
		return this.getStatManager().getMagicDmg();
	}
	
	
}
