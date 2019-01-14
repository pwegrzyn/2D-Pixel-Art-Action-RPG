package entityTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.to2.yadc.area.Area;
import pl.edu.agh.to2.yadc.config.Configuration;
import pl.edu.agh.to2.yadc.config.GlobalConfig;
import pl.edu.agh.to2.yadc.entity.Player;
import pl.edu.agh.to2.yadc.entity.ScorePowerUp;
import pl.edu.agh.to2.yadc.hud.HUD;

public class ScorePowerUpTests {
	private Player player;
	private Area area;
	private Configuration config;
	private HUD hud;
	private ScorePowerUp powerUp;
	
	@Before 
	public void prepare() {
		area = new Area("area");
		player = new Player(0,0);
		hud = new HUD();
		config = new Configuration();
		config.setHUD(hud);
		GlobalConfig.setGlobalConfig(config);
		powerUp = new ScorePowerUp(100, 100, 100);
		powerUp.setArea(area);
	}
	
	@Test
	public void getCollisionWithPlayerTest() {
		int score = player.getScore();
		powerUp.performCollisionAction(player);
		assertEquals("PowerUp don't rise score", player.getScore(), score+100);
		assertTrue("PowerUp not destroyed", area.getEntityRegister().getEnitiesToDelete().contains(powerUp));
	}
}
