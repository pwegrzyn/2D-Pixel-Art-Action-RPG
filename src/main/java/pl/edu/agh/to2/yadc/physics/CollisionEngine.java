package pl.edu.agh.to2.yadc.physics;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import pl.edu.agh.to2.yadc.area.Area;
import pl.edu.agh.to2.yadc.entity.Entity;


public class CollisionEngine {

    public static List<Entity> getCollisions(Entity testedEntity, Area area){
    	Set<Entity> activeEntities = area.getEntityRegister().getActiveEntities();
    	List<Entity> collidedEntities = new LinkedList<>();
    	for (Entity ent : activeEntities) {
    		if (ent != testedEntity) {
				if (Math.sqrt(Math.pow(Math.abs(ent.getXPos() - testedEntity.getXPos()), 2) + Math.pow(Math.abs(ent.getYPos() 
					- testedEntity.getYPos()), 2)) < ent.getCollisionRadius() + testedEntity.getCollisionRadius()) {
    				
    				collidedEntities.add(ent);
    			}
    		}
    	}
    	return collidedEntities;
    }

}