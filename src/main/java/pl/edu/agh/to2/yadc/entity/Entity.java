package pl.edu.agh.to2.yadc.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Entity {

    private BufferedImage texture;
    private double xPos;
    private double yPos;

    public Entity (double xInit, double yInit) {
        this.xPos = xInit;
        this.yPos = yInit;
    }

    public void advance (double delta) {
    
        // LOGIC HERE

    }

    public void renderSelf(Graphics graphics) {
        graphics.drawImage(this.texture, (int) xPos - texture.getWidth() / 2, (int) yPos - texture.getHeight() / 2, 
            texture.getWidth(), texture.getHeight(), null);
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

}