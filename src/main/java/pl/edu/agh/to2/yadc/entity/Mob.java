package pl.edu.agh.to2.yadc.entity;


public abstract class Mob extends Entity{

	protected int velocity;
	protected boolean aggresive;
	protected int range;
	protected StatManager statManager;
	protected int exp;
	
	public Mob(double xInit, double yInit, double collisionRadius) {
		super(xInit, yInit, collisionRadius);
		this.statManager = new StatManager(0,0,0,0,0,0);
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
	
	@Override
	public void performCollisionAction(Entity entity) {
		if(entity instanceof TestProjectile) {
			if(((Projectile) entity).getOwner() != this && (((Projectile) entity).getOwner() instanceof Player)) {
				aggresive = true;
				System.out.println("current HP = " + statManager.getCurrentHealth());
				statManager.setHealth(statManager.getCurrentHealth()-((Projectile)entity).physicalDmg-((Projectile)entity).magicDmg);
				if(statManager.getCurrentHealth()<=0) {
					if (((Projectile) entity).getOwner() instanceof Player) {
						((Player)((Projectile) entity).getOwner()).addExp(this.exp);
					}
					this.area.removeEntity(this);
				}
			}
		}
		if(entity instanceof Player) {
			this.angularRotation = 3.14;
			aggresive = true;
		}
		else {
			super.performCollisionAction(entity);
		}
	}
}
