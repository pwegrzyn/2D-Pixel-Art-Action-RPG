package entityTests;

import static org.junit.Assert.*;

import org.junit.Test;

import pl.edu.agh.to2.yadc.entity.StatManager;

public class StatManagerTests {

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

}
