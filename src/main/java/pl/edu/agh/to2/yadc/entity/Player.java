package pl.edu.agh.to2.yadc.entity;

import pl.edu.agh.to2.yadc.input.InputManager;


public class Player extends Entity {

    private int velocity;
	private InputManager inputManager;
    
    public Player(double xInit, double yInit) {
        super(xInit, yInit, 10);
        this.velocity = 120;
    }


    @Override
    public void advanceSelf(double delta) {
	    if (inputManager.upPressed()) {
	        this.yPos -= this.velocity * delta;
	    }
	
	    if (inputManager.downPressed()) {
	        this.yPos += this.velocity * delta;
	    }
	
	    if (inputManager.leftPressed()) {
	        this.xPos -= this.velocity * delta;
	    }
	
	    if (inputManager.rightPressed()) {
	        this.xPos += this.velocity * delta;
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