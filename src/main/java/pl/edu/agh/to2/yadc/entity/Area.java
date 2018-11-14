package pl.edu.agh.to2.yadc.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import pl.edu.agh.to2.yadc.render.Camera;
import pl.edu.agh.to2.yadc.render.ImageLoader;
import pl.edu.agh.to2.yadc.render.RenderManager;


public class Area {

    private BufferedImage texture;
    private Set<Entity> entities;


    public Area(String texturePath) {
        setTexture(texturePath);
        this.entities = new HashSet<>();
    }


    public void addEntity(Entity newEntity) {
        this.entities.add(newEntity);
    }


    public void advanceSelf(double delta) {

        for (Entity ent : this.entities) {
            ent.advanceSelf(delta);
        }

    }


    public void renderSelf(Graphics graphics) {
        

        Camera camera = RenderManager.getCurrentCamera();
        graphics.drawImage(this.texture, camera.getXPos(), camera.getYPos(), texture.getWidth(), texture.getHeight(), null);
        
        for (Entity ent : this.entities) {
            ent.renderSelf(graphics);
        }

    }


    public void setTexture(String texturePath) {
        this.texture = ImageLoader.fetchImage(texturePath);
    }
}