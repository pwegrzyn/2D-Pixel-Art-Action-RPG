package pl.edu.agh.to2.yadc.area;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import pl.edu.agh.to2.yadc.config.GlobalConfig;
import pl.edu.agh.to2.yadc.entity.Entity;
import pl.edu.agh.to2.yadc.entity.EntityRegister;
import pl.edu.agh.to2.yadc.entity.Player;
import pl.edu.agh.to2.yadc.physics.Advanceable;
import pl.edu.agh.to2.yadc.render.Camera;
import pl.edu.agh.to2.yadc.render.Renderable;


public class Area implements Renderable, Advanceable {

    private BufferedImage texture;
    private EntityRegister entityRegister;
    private String name;
    private Player player;


    public Area(String name) {
        this.name = name;
        this.entityRegister = new EntityRegister();
    }


    public void addEntity(Entity newEntity) {
        this.entityRegister.register(newEntity);
        newEntity.setArea(this);
        if(newEntity instanceof Player) {
            this.player = (Player) newEntity;
        }
    }


    public void removeEntity(Entity toDelete) {
        this.entityRegister.unregister(toDelete);
    }


    public void advanceSelf(double delta) {

        entityRegister.foreach(e -> e.advanceSelf(delta));
        entityRegister.synchronize();

    }


    public void renderSelf(Graphics graphics, Camera camera) {

        int renderXPos = GlobalConfig.get().getTargetWidth()/2 -camera.getXPos();
        int renderYPos = GlobalConfig.get().getTargetHeight()/2 -camera.getYPos();
        graphics.drawImage(this.texture,  renderXPos, renderYPos, texture.getWidth(), texture.getHeight(), null);
        
        entityRegister.foreach(e -> e.renderSelf(graphics, camera));

    }


    public void setTexture(BufferedImage texture) {
		this.texture = texture;
    }
    

    public EntityRegister getEntityRegister() {
    	return entityRegister;
    }


    public String getName() {
        return this.name;
    }


    public Player getPlayer() {
        return this.player;
    }
}