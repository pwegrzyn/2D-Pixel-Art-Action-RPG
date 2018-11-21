package pl.edu.agh.to2.yadc.entity;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import pl.edu.agh.to2.yadc.input.InputManager;
import pl.edu.agh.to2.yadc.physics.Vector;
import pl.edu.agh.to2.yadc.render.ImageLoader;
import pl.edu.agh.to2.yadc.render.RenderManager;


public class Player extends Entity {

    private int velocity;
	private InputManager inputManager;
	
	private int attackCooldown = 0;
    private long lastAttackTime = 0;
	
    public Player(double xInit, double yInit) {
        super(xInit, yInit, 10);
        this.velocity = 120;
    }

    
    private boolean up = false;
    private boolean down = false;
    private boolean right = false;
    private boolean left = false;
    
    Vector moveVector = new Vector(0, 0);
    

    

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
    
        if (inputManager.spacePressed()) {
        	System.out.println(this.angularRotation);
        	if (this.lastAttackTime == 0 || this.lastAttackTime + this.attackCooldown < System.currentTimeMillis()) {
		    	TestProjectile bullet = new TestProjectile(this, 5);
		    	this.lastAttackTime = System.currentTimeMillis();
		    	this.attackCooldown = 500;
				try {
					bullet.setTexture(ImageIO.read(new File("resources/bullet.png")));
					this.area.addEntity(bullet);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
        //RenderManager.getCurrentCamera().approach((int) this.xPos, (int) this.yPos, (int) (5 * delta));
    }


	@Override
	public void performCollisionAction(Entity entity) {
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

}