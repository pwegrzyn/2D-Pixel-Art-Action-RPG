package entityTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.to2.yadc.area.Area;
import pl.edu.agh.to2.yadc.config.Configuration;
import pl.edu.agh.to2.yadc.config.GlobalConfig;
import pl.edu.agh.to2.yadc.entity.Player;
import pl.edu.agh.to2.yadc.entity.SpeedPowerUp;
import pl.edu.agh.to2.yadc.hud.HUD;

public class SpeedPowerUpTests {
	private Player player;
	private Area area;
	private Configuration config;
	private HUD hud;
	private SpeedPowerUp powerUp;
	
	@Before 
	public void prepare() {
		area = new Area("area");
		player = new Player(0,0);
		hud = new HUD();
		config = new Configuration();
		config.setHUD(hud);
		GlobalConfig.setGlobalConfig(config);
		powerUp = new SpeedPowerUp(100, 100, 2, 10);
		powerUp.setArea(area);
	}
	
	@Test
	public void getCollisionWithPlayerTest() {
		int speed = player.getStatManager().getSpeed();
		powerUp.performCollisionAction(player);
		assertTrue("PowerUp not speeded", Math.abs(player.getSpeedMultiplayer()-2.0) < 0.01);
		assertTrue("PowerUp not destroyed", area.getEntityRegister().getEnitiesToDelete().contains(powerUp));
	}
}
