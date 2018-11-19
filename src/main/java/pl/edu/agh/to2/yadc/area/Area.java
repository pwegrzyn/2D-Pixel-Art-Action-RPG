package pl.edu.agh.to2.yadc.area;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import pl.edu.agh.to2.yadc.entity.Entity;
import pl.edu.agh.to2.yadc.entity.EntityRegister;
import pl.edu.agh.to2.yadc.render.Camera;
import pl.edu.agh.to2.yadc.render.ImageLoader;
import pl.edu.agh.to2.yadc.render.RenderManager;


public class Area {

    private BufferedImage texture;
    private EntityRegister entityRegister;


    public Area(String texturePath) {
        setTexture(texturePath);
        this.entityRegister = new EntityRegister();
    }


    public void addEntity(Entity newEntity) {
        this.entityRegister.register(newEntity);
    }


    public void removeEntity(Entity toDelete) {
        this.entityRegister.unregister(toDelete);
    }


    public void advanceSelf(double delta) {

        entityRegister.foreach(e -> e.advanceSelf(delta));
        entityRegister.synchronize();

    }


    public void renderSelf(Graphics graphics) {

        Camera camera = RenderManager.getCurrentCamera();
        int renderXPos = RenderManager.getCurrentConfiguration().getTargetWidth()/2 -camera.getXPos();
        int renderYPos = RenderManager.getCurrentConfiguration().getTargetHeight()/2 -camera.getYPos();
        graphics.drawImage(this.texture,  renderXPos, renderYPos, texture.getWidth(), texture.getHeight(), null);
        
        entityRegister.foreach(e -> e.renderSelf(graphics));

    }


    public void setTexture(String texturePath) {
        try {
			this.texture = ImageIO.read(new File(texturePath));
		} catch (IOException e) {
			System.out.println("Error while importing map image");
			e.printStackTrace();
		}
    }
    
    public EntityRegister getEntityRegister() {
    	return entityRegister;
    }
}