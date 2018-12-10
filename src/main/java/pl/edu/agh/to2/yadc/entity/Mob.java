package pl.edu.agh.to2.yadc.entity;

import java.awt.Graphics;

import pl.edu.agh.to2.yadc.physics.Vector;
import pl.edu.agh.to2.yadc.render.Camera;


public abstract class Mob extends Entity{

	protected int velocity;
	protected boolean aggresive;
	protected StatManager statManager;
	
	public Mob(double xInit, double yInit, double collisionRadius) {
		super(xInit, yInit, collisionRadius);
		this.statManager = new StatManager(0,0,0,0,0,0);
		this.statManager.setRange(20);
	}
	
	@Override
	public void advanceSelf(double delta) {
		if(aggresive)
			approachPlayer(delta);
			makeAttack();
	}
	
	private int getDirection(double playerPos, double enemyPos) {
		if(Math.abs(playerPos-enemyPos)<3) return 0;
		else return (int) Math.signum(playerPos - enemyPos);
	}
	
	protected void approachPlayer(double delta) {
		int playerXPos = (int) this.area.getPlayer().getXPos();
		int playerYPos = (int) this.area.getPlayer().getYPos();
		
		int newXdirection = getDirection(playerXPos, this.xPos);
		int newYdirection = getDirection(playerYPos, this.yPos);

		this.xPos+=this.velocity * delta * newXdirection;
		this.yPos+=this.velocity * delta * newYdirection;
		if(newXdirection!= 0) {
			this.angularRotation = newXdirection >= 0 ? Math.atan(newYdirection/newXdirection) : Math.atan(newYdirection/newXdirection) + Math.PI;
		}
		else this.angularRotation = newYdirection >= 0 ? Math.PI/2 : Math.PI*3/2;
	}
	
	abstract public void makeAttack();
	
	private void getMeleeAttackEffect(Player player) {
		
	}
	
	private void getRangedAttackEffect(Projectile projectile) {
		aggresive = true;
		System.out.println("current HP = " + statManager.getCurrentHealth());
		statManager.setHealth(statManager.getCurrentHealth()-projectile.physicalDmg-projectile.magicDmg);
		if(statManager.getCurrentHealth()<=0)
			this.area.removeEntity(this);
	}
	
	@Override
	public void performCollisionAction(Entity entity) {
		
		if(entity instanceof Projectile) {
			if(((Projectile) entity).getOwner() != this) {
				getRangedAttackEffect((Projectile)entity);
				super.performCollisionAction(entity);
			}
		}
		if(entity instanceof Player) {
			//this.angularRotation = 3.14;
			aggresive = true;
		}
	}

	public StatManager getStatManager() {
		return statManager;
	}
}
