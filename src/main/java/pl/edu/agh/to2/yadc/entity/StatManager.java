package pl.edu.agh.to2.yadc.entity;

import java.util.HashMap;
import java.util.Map;

public class StatManager {

	private int health;
	private int mana;
	private int baseHealth;
	private int baseMana;
	private int basePhysicalDmg;
	private int baseMagicDmg;
	private int speed;
	private int strength;
	private int stamina;
	private int intelligence;
	private int currentLvl;
	private int currentExp;
	private int expToNextLevel;
	private Map<Multipliers, Integer> multipliers;
	
	public StatManager(Integer strToHp, Integer strToDmg, Integer stamToHp, Integer stamToMana, Integer intToDmg, Integer intToMana) {

		multipliers = new HashMap<>();
		multipliers.put(Multipliers.STR_HP, strToHp);
		multipliers.put(Multipliers.STR_DMG, strToDmg);
		multipliers.put(Multipliers.STAM_HP, stamToHp);
		multipliers.put(Multipliers.STAM_MANA, stamToMana);
		multipliers.put(Multipliers.INT_DMG, intToDmg);
		multipliers.put(Multipliers.INT_MANA, intToMana);

		this.currentExp = 0;
		this.currentLvl = 1;
		this.expToNextLevel = 4225;
		this.baseHealth = 100;
		this.health = 73;
		this.baseMana = 100;
		this.mana = 24;
	}
	
	public int getMaxHealth() {
		return baseHealth + multipliers.get(Multipliers.STR_HP) * strength + multipliers.get(Multipliers.STAM_HP) * stamina;
	}
	
	public int getCurrentHealth() {
		return health;
	}
	
	
	public int getMaxMana() {
		return baseMana + multipliers.get(Multipliers.INT_MANA) * intelligence + multipliers.get(Multipliers.STAM_MANA) * stamina;
	}
	
	public int getCurrentMana() {
		return mana;
	}
	
	
	public int getSpeec() {
		return speed;
	}
	
	
	public int getPhysicalDmg() {
		return basePhysicalDmg + multipliers.get(Multipliers.STR_DMG) * strength;
	}
	
	
	public int getMagicDmg() {
		return baseMagicDmg + multipliers.get(Multipliers.INT_DMG) * intelligence;
	}
	
	
	public Map<Stats, Integer> getCurrentStatValues() {
		Map<Stats, Integer> statMap = new HashMap<>();
		statMap.put(Stats.BASE_HP, baseHealth);
		statMap.put(Stats.BASE_MANA, baseHealth);
		statMap.put(Stats.HP, health);
		statMap.put(Stats.MANA, mana);
		statMap.put(Stats.PHY_DMG, basePhysicalDmg);
		statMap.put(Stats.MAG_DMG, baseMagicDmg);
		statMap.put(Stats.SPEED, speed);
		statMap.put(Stats.STR, strength);
		statMap.put(Stats.INT, intelligence);
		statMap.put(Stats.STAM, stamina);
		
		return statMap;
	}
	
	
	public void setBaseHealth(int value) {
		baseHealth = value;
	}
	
	public void setBaseMana(int value) {
		baseMana = value;
	}
	
	public void setHealth(int value) {
		health = value;
	}
	
	public void setMana(int value) {
		mana = value;
	}
	
	public void setSpeed(int value) {
		speed = value;
	}
	
	public void setPhysicalDmg(int value) {
		basePhysicalDmg = value;
	}
	
	public void setMagicDmg(int value) {
		baseMagicDmg = value;
	}

	public int getCurrentLvl() {
		return this.currentLvl;
	}

	public int getCurrentExp() {
		return this.currentExp;
	}

	public int getExpToNextLvl() {
		return this.expToNextLevel;
	}
}

enum Multipliers
{
	STR_HP,
	STR_DMG,
	STAM_HP,
	STAM_MANA,
	INT_DMG,
	INT_MANA
}
