package pl.edu.agh.to2.yadc.area;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import pl.edu.agh.to2.yadc.config.GlobalConfig;
import pl.edu.agh.to2.yadc.entity.Entity;
import pl.edu.agh.to2.yadc.entity.EntityRegister;
import pl.edu.agh.to2.yadc.entity.Player;
import pl.edu.agh.to2.yadc.render.Camera;


public class Area {

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

        int renderXPos = GlobalConfig.getGlobalConfig().getTargetWidth()/2 -camera.getXPos();
        int renderYPos = GlobalConfig.getGlobalConfig().getTargetHeight()/2 -camera.getYPos();
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