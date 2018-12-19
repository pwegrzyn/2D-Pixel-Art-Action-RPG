package entityTests;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

import pl.edu.agh.to2.yadc.entity.Entity;
import pl.edu.agh.to2.yadc.entity.EntityRegister;
import pl.edu.agh.to2.yadc.entity.Player;

public class EntityRegisterTests {

	@Test
	public void getActiveEntitiesEmptySetTest() {
		EntityRegister entityRegister = new EntityRegister();
		assertEquals("Empty activeSet", Collections.emptySet(), entityRegister.getActiveEntities());
	}
	
	
	@Test
	public void getActiveEntitiesRegisterBeforeSync() {
		EntityRegister entityRegister = new EntityRegister();
		Entity entity = new Player(0,0);
		entityRegister.register(entity);
		Set<Entity> setBeforeSync = entityRegister.getActiveEntities();
		assertEquals("Register entity - empty set before sync", Collections.emptySet(), setBeforeSync);
	}
	
	
	@Test
	public void getActiveEntitiesRegisterAfterSyncSize() {
		EntityRegister entityRegister = new EntityRegister();
		Entity entity = new Player(0,0);
		entityRegister.register(entity);
		entityRegister.synchronize();
		Set<Entity> setAfterSync = entityRegister.getActiveEntities();
		assertEquals("Register entity - one element in set", 1, setAfterSync.size());
	}
	
	
	@Test
	public void getActiveEntitiesRegisterBefor3eSyncContains() {
		EntityRegister entityRegister = new EntityRegister();
		Entity entity = new Player(0,0);
		entityRegister.register(entity);
		entityRegister.synchronize();
		Set<Entity> setAfterSync = entityRegister.getActiveEntities();
		assertEquals("Regisyer entity - correct element in set", true, setAfterSync.contains(entity));
	}
	
	
	
	
	@Test
	public void getActiveEntitiesUnregisterBeforeSync() {
		EntityRegister entityRegister = new EntityRegister();
		Entity entity1 = new Player(0,0);
		Entity entity2 = new Player(0,0);
		entityRegister.register(entity1);
		entityRegister.register(entity2);
		Set<Entity> initialSet = entityRegister.getActiveEntities();
		entityRegister.synchronize();
		entityRegister.unregister(entity1);
		Set<Entity> setBeforeSync = entityRegister.getActiveEntities();
		assertEquals("Unregister entity - empty set before sync", initialSet, setBeforeSync);
	}
	
	
	@Test
	public void getActiveEntitiesUnregisterAfterSyncSize() {
		EntityRegister entityRegister = new EntityRegister();
		Entity entity1 = new Player(0,0);
		Entity entity2 = new Player(0,0);
		entityRegister.register(entity1);
		entityRegister.register(entity2);
		entityRegister.synchronize();
		entityRegister.unregister(entity2);
		entityRegister.synchronize();
		Set<Entity> setAfterSync = entityRegister.getActiveEntities();
		assertEquals("Unregister entity - one element in set", 1, setAfterSync.size());
	}
	
	
	@Test
	public void getActiveEntitiesUnregisterAfterSyncContains() {
		EntityRegister entityRegister = new EntityRegister();
		Entity entity1 = new Player(0,0);
		Entity entity2 = new Player(0,0);
		entityRegister.register(entity1);
		entityRegister.register(entity2);
		entityRegister.synchronize();
		entityRegister.unregister(entity2);
		entityRegister.synchronize();
		Set<Entity> setAfterSync = entityRegister.getActiveEntities();
		assertEquals("Unregister entity - contains correct entity", true, setAfterSync.contains(entity1));
	}
	

}
