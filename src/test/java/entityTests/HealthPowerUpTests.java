package entityTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.to2.yadc.area.Area;
import pl.edu.agh.to2.yadc.config.Configuration;
import pl.edu.agh.to2.yadc.config.GlobalConfig;
import pl.edu.agh.to2.yadc.entity.HealthPowerUp;
import pl.edu.agh.to2.yadc.entity.Player;
import pl.edu.agh.to2.yadc.hud.HUD;

public class HealthPowerUpTests {
	private Player player;
	private Area area;
	private Configuration config;
	private HUD hud;
	private HealthPowerUp powerUp;
	
	@Before 
	public void prepare() {
		area = new Area("area");
		player = new Player(0,0);
		hud = new HUD();
		config = new Configuration();
		config.setHUD(hud);
		GlobalConfig.setGlobalConfig(config);
		powerUp = new HealthPowerUp(100, 100, 100);
		powerUp.setArea(area);
	}
	
	@Test
	public void getCollisionWithPlayerTest() {
		player.getStatManager().setHealth(500);
		powerUp.performCollisionAction(player);
		assertEquals("PowerUp not healed", player.getStatManager().getCurrentHealth(), 600);
		assertTrue("PowerUp not destroyed", area.getEntityRegister().getEnitiesToDelete().contains(powerUp));
	}
	
	@Test
	public void getCollisionWithPlayerMaxHPTest() {
		player.getStatManager().setHealth(player.getStatManager().getMaxHealth());
		powerUp.performCollisionAction(player);
		assertEquals("Player don't have full HP", player.getStatManager().getCurrentHealth(), player.getStatManager().getMaxHealth());
		assertTrue("PowerUp not destroyed", area.getEntityRegister().getEnitiesToDelete().contains(powerUp));
	}
}
