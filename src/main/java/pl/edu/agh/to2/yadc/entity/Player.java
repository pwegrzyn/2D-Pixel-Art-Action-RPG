package pl.edu.agh.to2.yadc.entity;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.to2.yadc.game.App;
import pl.edu.agh.to2.yadc.input.InputManager;
import pl.edu.agh.to2.yadc.physics.Vector;


public class Player extends Entity {

    private int velocity;
	private InputManager inputManager;
	
	private int attackCooldown = 0;
    private long lastAttackTime = 0;
    
    private StatManager statManager;
	
    public Player(double xInit, double yInit) {
        super(xInit, yInit, 10);
		this.velocity = 120;
		this.statManager = new StatManager(0, 0, 0, 0, 0, 0);
		this.statManager.setRange(20);
		this.statManager.setBaseHealth(1000);
		this.statManager.setHealth(1000);
    }

    private boolean up = false;
    private boolean down = false;
    private boolean right = false;
    private boolean left = false;
    private Vector moveVector = new Vector(0, 0);
	private BufferedImage projectileTexture;

	private boolean performingAttack;

    @Override
    public void advanceSelf(double delta) {

		reactToUserInput(delta);
    
        if (performingAttack) {
        	if (this.lastAttackTime == 0 || this.lastAttackTime + this.attackCooldown < System.currentTimeMillis()) {
		    	Projectile bullet = ProjectileFactory.createNormalArrow(this, 4, projectileTexture);
		    	this.lastAttackTime = System.currentTimeMillis();
		    	this.attackCooldown = 100;
				bullet.setTexture(this.projectileTexture);
				this.area.addEntity(bullet);
			}
			performingAttack = false;
		}
	}
	
	private void reactToUserInput(double delta) {

		boolean isInputDisabled = inputManager.isNonChatInputDisabled();
		
		if (inputManager.getPressedByName("up") && !isInputDisabled) {
	    	this.yPos -= this.velocity * delta;
	    }
	    if (inputManager.getPressedByName("down") && !isInputDisabled) {
	    	this.yPos += this.velocity * delta;
	    }
	    if (inputManager.getPressedByName("left") && !isInputDisabled) {
	    	this.xPos -= this.velocity * delta;
	    } 
	    if (inputManager.getPressedByName("right") && !isInputDisabled) {
    	    this.xPos += this.velocity * delta;  
	    }
		
		if (inputManager.getPressedByName("lookUp") && !isInputDisabled) {
	    	if (!up) {
		        this.angularRotation = moveVector.addAndUpdate(0,  -1, this.angularRotation);
				up = true;
				performingAttack = true;
	    	}
	    }
	    else {
	    	if (up) {
	    		up = false;
	    		this.angularRotation = moveVector.addAndUpdate(0,  1, this.angularRotation);
	    	}
	    }
	
	    if (inputManager.getPressedByName("lookDown") && !isInputDisabled) {
	    	if (!down) {
		    	down = true;
				this.angularRotation = moveVector.addAndUpdate(0,  1, this.angularRotation);
				performingAttack = true;
	    	}
	    }
	    else {
	    	if (down) {
	    		down = false;
	    		this.angularRotation = moveVector.addAndUpdate(0,  -1, this.angularRotation);
	    	}
	    }
	
	    if (inputManager.getPressedByName("lookLeft") && !isInputDisabled) {
	    	if (!left) {
		        left = true;
				this.angularRotation = moveVector.addAndUpdate(-1,  0, this.angularRotation);
				performingAttack = true;
	    	}
	    } 
	    else {
	    	if (left) {
	    		left = false;
	    		this.angularRotation = moveVector.addAndUpdate(1,  0, this.angularRotation);
	    	}
	    }
	
	    if (inputManager.getPressedByName("lookRight") && !isInputDisabled) { 
    	    if (!right) {
		        right = true;		    
				this.angularRotation = moveVector.addAndUpdate(1,  0, this.angularRotation);
				performingAttack = true;
	    	}
	    }
	    else {
	    	if (right) {
	    		right = false;
	    		this.angularRotation = moveVector.addAndUpdate(-1,  0, this.angularRotation);
	    	}
		}
		
	}

	
	@Override
	public void performCollisionAction(Entity entity) {
		if(entity instanceof Projectile) {
			if(((Projectile)entity).getOwner() == this) {
				return;
			}
		}
		else if(entity instanceof MeleeMob) {
			List<Action> copy = new LinkedList<Action>(entity.spreadingActions);
			for (Action effect : copy) {
				effect.activate(this);
				if(entity.spreadingActions.get(0)!=effect) entity.spreadingActions.remove(effect);
			}
		}
		super.performCollisionAction(entity);
		// Kek

		double currentDistance = Math.sqrt(Math.pow(Math.abs(entity.getXPos() - this.getXPos()), 2) 
			+ Math.pow(Math.abs(entity.getYPos() - this.getYPos()), 2));
		this.yPos = entity.getYPos() + (this.getYPos() > entity.getYPos() ? 1 : -1) * Math.abs(this.getYPos() 
			- entity.getYPos())/currentDistance * (this.collisionRadius + entity.collisionRadius);
		this.xPos = entity.getXPos() + (this.getXPos() > entity.getXPos() ? 1 : -1) * Math.abs(this.getXPos() 
			- entity.getXPos())/currentDistance * (this.collisionRadius + entity.collisionRadius);
		
	}


	public void setInputManager(InputManager input) {
		this.inputManager = input;
	}

	public void setProjectileTexture(BufferedImage fetchImage) {
		this.projectileTexture = fetchImage;
	}

	public StatManager getStatManager() {
		return this.statManager;
	}
	
	public void addExp(int exp) {
		int currentExp = this.statManager.getCurrentExp();
		int expToNextLvl = this.statManager.getExpToNextLvl();
		if (currentExp + exp >= expToNextLvl) {
			this.statManager.setLvl(this.statManager.getLvl() + 1);
			this.statManager.setExpToNextLvl(this.statManager.getExpToNextLvl() * 2);
			this.statManager.setExp(currentExp + exp - expToNextLvl);
		}
		else {
			this.statManager.setExp(this.statManager.getExp() + exp);
		}
	}

}