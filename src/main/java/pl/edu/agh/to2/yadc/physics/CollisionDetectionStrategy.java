package pl.edu.agh.to2.yadc.physics;

import pl.edu.agh.to2.yadc.entity.Entity;

public interface CollisionDetectionStrategy {

    boolean doCollide(Entity entityA, Entity entityB);

}