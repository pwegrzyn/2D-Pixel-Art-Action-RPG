package pl.edu.agh.to2.yadc.entity;

import java.util.LinkedList;
import java.util.List;

public class TestEnemy extends RangedMob{

	private double roamingTimer;
	
	public TestEnemy(double xInit, double yInit, double collisionRadius) {
		super(xInit, yInit, collisionRadius);
		this.roamingTimer = 0;
		this.velocity = 80;
		
		this.spreadingActions = getSpreadingEffects();
	}

	
	private List<Action> getSpreadingEffects(){
		List<Action> actionList = new LinkedList<>();
		actionList.add(new Action(Projectile.class, entity -> {
			Projectile projectile = (Projectile)entity;
			if(projectile.owner != this) {
				projectile.angularRotation += Math.PI;
				projectile.velocity /= 2;
			}
		}));
		
		return actionList;
	}

	/*
	private void continueStraightRoam(double delta) {
		this.roamingTimer += delta;
		if(this.roamingTimer <= 2) {
			this.xPos += this.velocity * delta;
		} else if(this.roamingTimer > 2 && this.roamingTimer <= 4) {
			this.xPos -= this.velocity * delta;
		} else {
			this.roamingTimer = 0;
		}
	}
	*/
}
