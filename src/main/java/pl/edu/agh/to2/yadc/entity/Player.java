package pl.edu.agh.to2.yadc.entity;

import java.awt.image.BufferedImage;
import pl.edu.agh.to2.yadc.input.InputManager;
import pl.edu.agh.to2.yadc.physics.Vector;


public class Player extends Entity {

    private int velocity;
	private InputManager inputManager;
	
	private int attackCooldown = 0;
    private long lastAttackTime = 0;
    
    public StatManager statManager;
	
    public Player(double xInit, double yInit) {
        super(xInit, yInit, 10);
		this.velocity = 120;
		this.statManager = new StatManager(0, 0, 0, 0, 0, 0);
    }

    private boolean up = false;
    private boolean down = false;
    private boolean right = false;
    private boolean left = false;
    Vector moveVector = new Vector(0, 0);
	private BufferedImage projectileTexture;

    @Override
    public void advanceSelf(double delta) {
	    if (inputManager.upPressed()) {
	    	this.yPos -= this.velocity * delta;
	    	if (!up) {
		        this.angularRotation = moveVector.addAndUpdate(0,  -1, this.angularRotation);
		        up = true;
	    	}
	    }
	    else {
	    	if (up) {
	    		up = false;
	    		this.angularRotation = moveVector.addAndUpdate(0,  1, this.angularRotation);
	    	}
	    }
	
	    if (inputManager.downPressed()) {
	    	this.yPos += this.velocity * delta;
	    	if (!down) {
		    	down = true;
		        this.angularRotation = moveVector.addAndUpdate(0,  1, this.angularRotation);
	    	}
	    }
	    else {
	    	if (down) {
	    		down = false;
	    		this.angularRotation = moveVector.addAndUpdate(0,  -1, this.angularRotation);
	    	}
	    }
	
	    if (inputManager.leftPressed()) {
	    	this.xPos -= this.velocity * delta;
	    	if (!left) {
		        left = true;
		    	this.angularRotation = moveVector.addAndUpdate(-1,  0, this.angularRotation);
	    	}
	    } 
	    else {
	    	if (left) {
	    		left = false;
	    		this.angularRotation = moveVector.addAndUpdate(1,  0, this.angularRotation);
	    	}
	    }
	
	    if (inputManager.rightPressed()) {
    	    this.xPos += this.velocity * delta;  
    	    if (!right) {
		        right = true;		    
		    	this.angularRotation = moveVector.addAndUpdate(1,  0, this.angularRotation);
	    	}
	    }
	    else {
	    	if (right) {
	    		right = false;
	    		this.angularRotation = moveVector.addAndUpdate(-1,  0, this.angularRotation);
	    	}
	    }
    
        if (inputManager.attackPressed()) {
        	if (this.lastAttackTime == 0 || this.lastAttackTime + this.attackCooldown < System.currentTimeMillis()) {
		    	TestProjectile bullet = new TestProjectile(this, 5);
		    	this.lastAttackTime = System.currentTimeMillis();
		    	this.attackCooldown = 100;
				bullet.setTexture(this.projectileTexture);
				this.area.addEntity(bullet);
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

}