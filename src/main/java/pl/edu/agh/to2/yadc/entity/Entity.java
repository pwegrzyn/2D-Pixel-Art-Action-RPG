package pl.edu.agh.to2.yadc.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import pl.edu.agh.to2.yadc.physics.CollisionEngine;
import pl.edu.agh.to2.yadc.render.Camera;
import pl.edu.agh.to2.yadc.render.ImageLoader;
import pl.edu.agh.to2.yadc.render.RenderManager;


public abstract class Entity {

    protected BufferedImage texture;
    protected double xPos;
    protected double yPos;
    protected Boolean collidable;
    protected double collisionRadius;
    protected double angularRotation;


    public Entity (double xInit, double yInit, double collisionRadius) {
        this.xPos = xInit;
        this.yPos = yInit;
        this.collidable = true;
        this.collisionRadius = collisionRadius;
        this.angularRotation = 0.0;
    }

    abstract public void advanceSelf (double delta);


    public final void renderSelf(Graphics graphics) {
    	checkCollisions();
        Camera currentCamera = RenderManager.getCurrentCamera();
        int width = RenderManager.getCurrentConfiguration().getTargetWidth();
        int height = RenderManager.getCurrentConfiguration().getTargetHeight();
        int xApparent = (int) xPos - currentCamera.getXPos() + width / 2;
        int yApparent = (int) yPos - currentCamera.getYPos() + height / 2;
        graphics.drawImage(this.texture, xApparent - texture.getWidth()/2, yApparent - texture.getHeight()/2, texture.getWidth(), texture.getHeight(), null);

    }
    
    public final void checkCollisions() {
    	List<Entity> collidedEntities = CollisionEngine.getCollisions(this);
    	for (Entity ent : collidedEntities) {
    		this.performCollisionAction(ent);
    	}
    }
    
    abstract public void performCollisionAction(Entity entity);


    public void setTexture(String texturePath) {
        this.texture = ImageLoader.fetchImage(texturePath);
    }
    
    public double getXPos() {
    	return xPos;
    }
    
    public double getYPos() {
    	return yPos;
    }
    
    public double getCollisionRadius() {
    	return collisionRadius;
    }

}