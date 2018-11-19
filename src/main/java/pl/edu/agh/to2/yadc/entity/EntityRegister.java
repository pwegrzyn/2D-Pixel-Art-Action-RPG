package pl.edu.agh.to2.yadc.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class EntityRegister {

    private Set<Entity> activeEntities;
    private Set<Entity> toDeleteEntities;
    private Set<Entity> toAddEntites;

    public EntityRegister() {
        activeEntities = new HashSet<>();
        toAddEntites = new HashSet<>();
        toDeleteEntities = new HashSet<>();
    }

    public Set<Entity> getActiveSet() {
        return this.activeEntities;
    }
    
    public void register(Entity newEntity) {
        toAddEntites.add(newEntity);
    }

    public void unregister(Entity entity) {
        toDeleteEntities.add(entity);
    }

    public void synchronize() {
        activeEntities.removeAll(toDeleteEntities);
        activeEntities.addAll(toAddEntites);
    }

	public void foreach(Consumer<Entity> mapper) {

        for (Entity ent : this.activeEntities) {
            mapper.accept(ent);
        }
	}
	
	public Set<Entity> getActiveEntities(){
		return activeEntities;
	}

}