package entityTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.to2.yadc.area.Area;
import pl.edu.agh.to2.yadc.config.Configuration;
import pl.edu.agh.to2.yadc.config.GlobalConfig;
import pl.edu.agh.to2.yadc.entity.Player;
import pl.edu.agh.to2.yadc.entity.SpeedPowerUp;
import pl.edu.agh.to2.yadc.entity.StatManager;
import pl.edu.agh.to2.yadc.hud.HUD;

public class StatManagerTests {
	private Configuration config;
	private HUD hud;
	private SpeedPowerUp powerUp;
	
	@Before 
	public void prepare() {
		hud = new HUD();
		config = new Configuration();
		config.setHUD(hud);
		GlobalConfig.setGlobalConfig(config);
	}
	
	@Test
	public void getMaxHealthTest(){
		int strToHp = 10;
		int stamToHp = 40;
		int baseHp = 100;
		int baseStr = 20;
		int baseStam = 20;
		StatManager statManager = new StatManager(strToHp, 0, stamToHp, 0, 0, 0);
		statManager.setBaseHealth(baseHp);
		statManager.setStrength(baseStr);
		statManager.setStamina(baseStam);
		assertEquals("Max health formula", baseHp + strToHp * baseStr + stamToHp * baseStam, statManager.getMaxHealth());
	}
	
	
	@Test
	public void getMaxManaTest(){
		int intToMana = 20;
		int stamToMana = 30;
		int baseMana = 200;
		int baseInt = 50;
		int baseStam = 20;
		StatManager statManager = new StatManager(0, 0, 0, stamToMana, 0, intToMana);
		statManager.setBaseMana(baseMana);
		statManager.setIntelligence(baseInt);
		statManager.setStamina(baseStam);
		assertEquals("Max mana formula", baseMana + intToMana * baseInt + stamToMana * baseStam, statManager.getMaxMana());
	}
	
	
	@Test
	public void getPhysicalDmgTest() {
		int baseDmg = 50;
		int strToDmg = 20;
		int baseStr = 10;
		StatManager statManager = new StatManager(0, strToDmg, 0, 0, 0, 0);
		statManager.setPhysicalDmg(baseDmg);
		statManager.setStrength(baseStr);
		assertEquals("Physical dmg formula", baseDmg + baseStr * strToDmg, statManager.getPhysicalDmg());
	}
	
	
	@Test
	public void getMagicDmgTest() {
		int baseDmg = 30;
		int intToDmg = 35;
		int baseInt = 10;
		StatManager statManager = new StatManager(0, 0, 0, 0, intToDmg, 0);
		statManager.setMagicDmg(baseDmg);
		statManager.setIntelligence(baseInt);
		assertEquals("Magic dmg formula", baseDmg + baseInt * intToDmg, statManager.getMagicDmg());
	}
	
	@Test
	public void addExpNormalTest() {
		StatManager statManager = new StatManager(0, 0, 0, 0, 35, 0);
		statManager.setExp(0);
		statManager.setExpToNextLvl(1000);
		statManager.addExp(100);
		assertEquals("Bad number of added exp", statManager.getExp(), 100);
	}
	
	@Test
	public void addExpLvlUpTest() {
		StatManager statManager = new StatManager(0, 0, 0, 0, 35, 0);
		statManager.setExpToNextLvl(1000);
		statManager.setExp(990);
		statManager.setLvl(2);
		statManager.addExp(100);
		assertEquals("Bad number of added exp", statManager.getExp(), 90);
		assertEquals("Bad lvl calculated", statManager.getLvl(), 3);
	}

}
