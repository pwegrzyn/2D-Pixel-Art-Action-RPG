package pl.edu.agh.to2.yadc.entity;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;


public abstract class Mob extends Entity{
	
	protected int velocity;
	protected int velocity0;
	protected boolean aggresive;
	protected StatManager statManager;
	protected int exp;
	protected int score;
	protected BufferedImage lootTexture;
	protected boolean slowed = false;
	
	public Mob(double xInit, double yInit, double collisionRadius) {
		super(xInit, yInit, collisionRadius);
		this.statManager = new StatManager(0,0,0,0,0,0);
		this.statManager.setRange(25);
		this.spreadingActions = getSpreadingEffects();
	}
	
	@Override
	public void advanceSelf(double delta) {
		super.advanceSelf(delta);
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
	
	@Override
	public void performCollisionAction(Entity entity) {
		if(entity instanceof Player) {
			aggresive = true;
		}
		super.performCollisionAction(entity);
	}
	
	private List<Action> getSpreadingEffects(){
		List<Action> actionList = new LinkedList<>();
		actionList.add(new Action(Projectile.class, entity -> {
			Projectile projectile = (Projectile)entity;
			if(projectile.owner != this && projectile.owner instanceof Player) {
				projectile.area.removeEntity(projectile);
			}
		}));
		return actionList;
	}

	public StatManager getStatManager() {
		return this.statManager;
	}
	
	public void setExp(int exp) {
		this.exp = exp;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setLootTexture(BufferedImage texture) {
		this.lootTexture = texture;
	}

	public BufferedImage getLootTexture() {
		return this.lootTexture;
	}

	public void setAggresive(boolean val) {
		this.aggresive = val;
	}

	public boolean getAggresive() {
		return this.aggresive;
	}
}
