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
				if (doCollide(testedEntity, ent))
					collidedEntities.add(ent);
    		}
    	}
    	return collidedEntities;
	}
	

	public static Boolean doCollide(Entity entA, Entity entB) {
		
		return Math.sqrt(Math.pow(Math.abs(entB.getXPos() - entA.getXPos()), 2) + Math.pow(Math.abs(entB.getYPos() 
		- entA.getYPos()), 2)) < entB.getCollisionRadius() + entA.getCollisionRadius();

	}

}