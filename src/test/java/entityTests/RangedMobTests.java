package entityTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.to2.yadc.area.Area;
import pl.edu.agh.to2.yadc.config.Configuration;
import pl.edu.agh.to2.yadc.config.GlobalConfig;
import pl.edu.agh.to2.yadc.entity.MeleeMob;
import pl.edu.agh.to2.yadc.entity.Player;
import pl.edu.agh.to2.yadc.entity.Projectile;
import pl.edu.agh.to2.yadc.entity.ProjectileFactory;
import pl.edu.agh.to2.yadc.entity.ProjectileTypes;
import pl.edu.agh.to2.yadc.entity.RangedMob;
import pl.edu.agh.to2.yadc.hud.HUD;

public class RangedMobTests {
	private Player player;
	private Area area;
	private RangedMob mob;
	private Configuration config;
	private HUD hud;
	
	@Before 
	public void prepare() {
		area = new Area("area");
		player = new Player(0,0);
		mob = new RangedMob(100, 100, 15);
		mob.setArea(area);
		hud = new HUD();
		config = new Configuration();
		config.setHUD(hud);
		GlobalConfig.setGlobalConfig(config);
		
	}
	
	@Test
	public void getCollisionWithOwnProjectile() {
		Projectile projectile = ProjectileFactory.createProjectile(ProjectileTypes.ENEMY, mob, 5);
		projectile.setPhysicalDmg(100);
		projectile.setMagicDmg(0);
		projectile.setArea(area);
		mob.getStatManager().setHealth(1000);
		mob.performCollisionAction(projectile);
		assertEquals("Mob deals dmg to himself", mob.getStatManager().getCurrentHealth(), 1000);
		assertFalse("Own projectile destroyed", area.getEntityRegister().getEnitiesToDelete().contains(projectile));
	}
	
	@Test
	public void getCollisionWithPlayerProjectileTest() {
		Projectile projectile = ProjectileFactory.createProjectile(ProjectileTypes.NORMAL, player, 5);
		projectile.setPhysicalDmg(100);
		projectile.setMagicDmg(0);
		projectile.setArea(area);
		mob.getStatManager().setHealth(1000);
		mob.performCollisionAction(projectile);
		assertEquals("Mob get unproper dmg from projectile", mob.getStatManager().getCurrentHealth(), 900);
		assertTrue("Projectile not destroyed in contact with mob", area.getEntityRegister().getEnitiesToDelete().contains(projectile));
	}
	
	@Test
	public void getCollisionWithPlayerProjectileAndDieTest() {
		Projectile projectile = ProjectileFactory.createProjectile(ProjectileTypes.NORMAL, player, 5);
		projectile.setPhysicalDmg(100);
		projectile.setMagicDmg(0);
		projectile.setArea(area);
		mob.getStatManager().setHealth(10);
		mob.performCollisionAction(projectile);
		assertTrue("Projectile not destroyed mob", area.getEntityRegister().getEnitiesToDelete().contains(mob));
		assertTrue("Projectile not destroyed in contact with mob", area.getEntityRegister().getEnitiesToDelete().contains(projectile));
	}
	
	@Test
	public void getCollisionWithMobProjectileTest() {
		RangedMob mob2 = new RangedMob(100, 100, 15);
		Projectile projectile = ProjectileFactory.createProjectile(ProjectileTypes.ENEMY, mob2, 5);
		projectile.setPhysicalDmg(100);
		projectile.setMagicDmg(0);
		projectile.setArea(area);
		mob.getStatManager().setHealth(1000);
		mob.performCollisionAction(projectile);
		assertEquals("Friendly fire not work", mob.getStatManager().getCurrentHealth(), 900);
		assertTrue("Projectile not destroyed in contact with mob", area.getEntityRegister().getEnitiesToDelete().contains(projectile));
	}
	
	@Test
	public void getCollisionWithPlayerSlowTest() {
		Projectile projectile = ProjectileFactory.createProjectile(ProjectileTypes.SLOWING, player, 5);
		projectile.setPhysicalDmg(100);
		projectile.setMagicDmg(0);
		projectile.setArea(area);
		mob.getStatManager().setHealth(1000);
		mob.performCollisionAction(projectile);
		assertEquals("Mob get unproper dmg from projectile", mob.getStatManager().getCurrentHealth(), 900);
		assertTrue("Projectile not destroyed in contact with mob", area.getEntityRegister().getEnitiesToDelete().contains(projectile));
		assertFalse("Mob not slowed", mob.spreadingActions.isEmpty());
	}
	
	@Test
	public void getCollisionWithPlayerStunTest() {
		Projectile projectile = ProjectileFactory.createProjectile(ProjectileTypes.STUNNING, player, 5);
		projectile.setPhysicalDmg(100);
		projectile.setMagicDmg(0);
		projectile.setArea(area);
		mob.getStatManager().setHealth(1000);
		mob.performCollisionAction(projectile);
		assertEquals("Mob get unproper dmg from projectile", mob.getStatManager().getCurrentHealth(), 900);
		assertTrue("Projectile not destroyed in contact with mob", area.getEntityRegister().getEnitiesToDelete().contains(projectile));
		assertFalse("Mob not slowed", mob.spreadingActions.isEmpty());
	}
	
	@Test
	public void makeAttackTest() {
		mob.setAggresive(true);
		mob.makeAttack();
		assertFalse("Projectile not created", area.getEntityRegister().getEntitiesToAdd().isEmpty());
	}
}
