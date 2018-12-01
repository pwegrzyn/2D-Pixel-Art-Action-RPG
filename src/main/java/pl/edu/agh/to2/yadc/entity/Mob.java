package pl.edu.agh.to2.yadc.entity;

import java.awt.Graphics;

import pl.edu.agh.to2.yadc.physics.Vector;
import pl.edu.agh.to2.yadc.render.Camera;

public abstract class Mob extends Entity{

	protected int velocity;
	protected boolean aggresive;
	protected int range;
	Vector moveVector = new Vector(0, 0);
	
	public Mob(double xInit, double yInit, double collisionRadius) {
		super(xInit, yInit, collisionRadius);
	}
	
	@Override
	public void advanceSelf(double delta) {
		if(aggresive)
			approachPlayer(delta);
	}
	
	private int getDirection(double playerPos, double enemyPos) {
		if(Math.abs(playerPos-enemyPos)<3) return 0;
		else return (int) Math.signum(playerPos - enemyPos);
	}
	
	private void approachPlayer(double delta) {
		int playerXPos = (int) this.area.getPlayer().getXPos();
		int playerYPos = (int) this.area.getPlayer().getYPos();
		
		int newXdirection = getDirection(playerXPos, this.xPos);
		int newYdirection = getDirection(playerYPos, this.yPos);
		
		/*
		this.xPos += newXdirection * this.velocity * delta;
		this.yPos += newYdirection * this.velocity * delta;
		*/

		this.xPos+=this.velocity * delta * newXdirection;
		this.yPos+=this.velocity * delta * newYdirection;
		//this.angularRotation = moveVector.addAndUpdate(newXdirection, newYdirection, this.angularRotation);
		if(newXdirection!= 0) {
			this.angularRotation = newXdirection >= 0 ? Math.atan(newYdirection/newXdirection) : Math.atan(newYdirection/newXdirection) + Math.PI;
		}
		else this.angularRotation = newYdirection >= 0 ? Math.PI/2 : Math.PI*3/2;
	}
	
	abstract public void makeAttack();
	
	@Override
	public void performCollisionAction(Entity entity) {
		
		if(entity instanceof TestProjectile) {
			if(((Projectile) entity).getOwner() != this)
			this.area.removeEntity(this);
		}
		if(entity instanceof Player) {
			this.angularRotation = 3.14;
			aggresive = true;
		}
	}
	
	@Override
	public final void renderSelf(Graphics graphics, Camera currentCamera) {
		super.renderSelf(graphics, currentCamera);
		makeAttack();
	}
}
