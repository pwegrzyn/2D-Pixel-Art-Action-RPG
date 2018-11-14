package pl.edu.agh.to2.yadc.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import pl.edu.agh.to2.yadc.render.Camera;
import pl.edu.agh.to2.yadc.render.ImageLoader;
import pl.edu.agh.to2.yadc.render.RenderManager;


public abstract class Entity {

    protected BufferedImage texture;
    protected double xPos;
    protected double yPos;
    protected Boolean collidable;


    public Entity (double xInit, double yInit) {
        this.xPos = xInit;
        this.yPos = yInit;
        this.collidable = true;
    }

    abstract public void advanceSelf (double delta);


    public void renderSelf(Graphics graphics) {

        Camera currentCamera = RenderManager.getCurrentCamera();
        int width = RenderManager.getCurrentConfiguration().getTargetWidth();
        int height = RenderManager.getCurrentConfiguration().getTargetHeight();
        int xApparent = (int) xPos - currentCamera.getXPos() + width / 2;
        int yApparent = (int) yPos - currentCamera.getYPos() + height / 2;
            
        graphics.drawImage(this.texture, xApparent, yApparent, texture.getWidth(), texture.getHeight(), null);

    }


    public void setTexture(String texturePath) {
        this.texture = ImageLoader.fetchImage(texturePath);
    }

}