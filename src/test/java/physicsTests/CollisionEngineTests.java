package physicsTests;

import static org.junit.Assert.*;

import org.junit.Test;

import pl.edu.agh.to2.yadc.entity.Entity;
import pl.edu.agh.to2.yadc.entity.MeleeMob;
import pl.edu.agh.to2.yadc.physics.CollisionEngine;

public class CollisionEngineTests {

	@Test
	public void doCollideTestNo() {
		Entity entity1 = new MeleeMob(0, 0, 10);
		Entity entity2 = new MeleeMob(50, 20, 20);
		assertEquals("No collision", false, CollisionEngine.doCollide(entity1, entity2));
	}
	
	@Test
	public void doCollideTestYes() {
		Entity entity1 = new MeleeMob(0, 0, 10);
		Entity entity2 = new MeleeMob(10, 10, 10);
		assertEquals("Collision", true, CollisionEngine.doCollide(entity1,  entity2));
	}
	
	@Test
	public void doCollideTestNoTouch() {
		Entity entity1 = new MeleeMob(0, 0, 10);
		Entity entity2 = new MeleeMob(30, 0, 20);
		assertEquals("No collision - touch", false, CollisionEngine.doCollide(entity1,  entity2));
	}

}
