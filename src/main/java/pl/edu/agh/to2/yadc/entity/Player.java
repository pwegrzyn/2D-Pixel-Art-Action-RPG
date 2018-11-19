package pl.edu.agh.to2.yadc.entity;

import java.nio.file.Paths;

import pl.edu.agh.to2.yadc.input.InputManager;
import pl.edu.agh.to2.yadc.render.RenderManager;

public class Player extends Entity {

    private int velocity;
    
    public Player(double xInit, double yInit) {
        super(xInit, yInit, 10);
        setTexture(Paths.get("resources/test_entity.png").toString());
        this.velocity = 120;
    }


    @Override
    public void advanceSelf(double delta) {
	    if (InputManager.upPressed()) {
	        this.yPos -= this.velocity * delta;
	    }
	
	    if (InputManager.downPressed()) {
	        this.yPos += this.velocity * delta;
	    }
	
	    if (InputManager.leftPressed()) {
	        this.xPos -= this.velocity * delta;
	    }
	
	    if (InputManager.rightPressed()) {
	        this.xPos += this.velocity * delta;
	    }
    
        
        //RenderManager.getCurrentCamera().approach((int) this.xPos, (int) this.yPos, (int) (5 * delta));
        RenderManager.getCurrentCamera().moveTo((int) this.xPos, (int) this.yPos);
    }


	@Override
	public void performCollisionAction(Entity entity) {
		double currentDistance = Math.sqrt(Math.pow(Math.abs(entity.getXPos() - this.getXPos()), 2) + Math.pow(Math.abs(entity.getYPos() - this.getYPos()), 2));
		this.yPos = entity.getYPos() + (this.getYPos() > entity.getYPos() ? 1 : -1) * Math.abs(this.getYPos() - entity.getYPos())/currentDistance * (this.collisionRadius + entity.collisionRadius);
		this.xPos = entity.getXPos() + (this.getXPos() > entity.getXPos() ? 1 : -1) * Math.abs(this.getXPos() - entity.getXPos())/currentDistance * (this.collisionRadius + entity.collisionRadius);
		
	}

}