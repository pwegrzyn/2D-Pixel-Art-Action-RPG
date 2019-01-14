package pl.edu.agh.to2.yadc.entity;

import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.to2.yadc.config.GlobalConfig;
import pl.edu.agh.to2.yadc.item.Equipment;

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
	private int range;
	private Map<Multipliers, Integer> multipliers;
	private Equipment equipment;
	
	public StatManager(Equipment equipment, Integer strToHp, Integer strToDmg, Integer stamToHp, Integer stamToMana, Integer intToDmg, Integer intToMana) {

		multipliers = new HashMap<>();
		multipliers.put(Multipliers.STR_HP, strToHp);
		multipliers.put(Multipliers.STR_DMG, strToDmg);
		multipliers.put(Multipliers.STAM_HP, stamToHp);
		multipliers.put(Multipliers.STAM_MANA, stamToMana);
		multipliers.put(Multipliers.INT_DMG, intToDmg);
		multipliers.put(Multipliers.INT_MANA, intToMana);

		this.currentExp = 0;
		this.currentLvl = 1;
		this.expToNextLevel = 1000;
		this.baseHealth = 100;
		this.health = 73;
		this.baseMana = 100;
		this.mana = 24;
		this.speed = 150;
		
		this.equipment = equipment;
	}
	
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
		this.expToNextLevel = 1000;
		this.baseHealth = 100;
		this.health = 73;
		this.baseMana = 100;
		this.mana = 24;
		this.speed = 150;
		
		this.equipment = new Equipment(null);
	}
	
	
	public int getMaxHealth() {
		return (baseHealth + equipment.calculateTotalBuffedStats().get(Stats.BASE_HP)) + 
				multipliers.get(Multipliers.STR_HP) * (strength + equipment.calculateTotalBuffedStats().get(Stats.STR)) + 
				multipliers.get(Multipliers.STAM_HP) * (stamina + equipment.calculateTotalBuffedStats().get(Stats.STAM));
	}
	
	public int getCurrentHealth() {
		return health;
	}
	
	
	public int getMaxMana() {
		return (baseMana + equipment.calculateTotalBuffedStats().get(Stats.BASE_MANA)) + 
				multipliers.get(Multipliers.INT_MANA) * (intelligence + equipment.calculateTotalBuffedStats().get(Stats.INT)) + 
				multipliers.get(Multipliers.STAM_MANA) * (stamina + equipment.calculateTotalBuffedStats().get(Stats.STAM));
	}
	
	public int getCurrentMana() {
		return mana;
	}
	
	
	public int getSpeed() {
		return speed + equipment.calculateTotalBuffedStats().get(Stats.SPEED);
	}
	
	
	public int getPhysicalDmg() {
		return (basePhysicalDmg + equipment.calculateTotalBuffedStats().get(Stats.PHY_DMG)) +
				multipliers.get(Multipliers.STR_DMG) * (strength + equipment.calculateTotalBuffedStats().get(Stats.STR));
	}
	
	
	public int getMagicDmg() {
		return (baseMagicDmg + equipment.calculateTotalBuffedStats().get(Stats.MAG_DMG)) + 
				multipliers.get(Multipliers.INT_DMG) * (intelligence + equipment.calculateTotalBuffedStats().get(Stats.INT));
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
	
	public void addExp(int exp) {
		GlobalConfig.get().printToChatBox("Received " + exp + " xp.");
		if (currentExp + exp >= this.expToNextLevel) {
			this.currentLvl++;
			GlobalConfig.get().printToChatBox("Congratulations! You have gained a new level.");
			GlobalConfig.get().printToChatBox("You are now level " + this.currentLvl + ".");
			currentExp += exp - this.expToNextLevel;
			this.expToNextLevel *= 2;
			this.strength ++;
			this.stamina ++;
			this.intelligence ++;
			this.baseHealth += 100;
			this.baseMana += 100;
			this.health = this.baseHealth;
			this.mana = this.baseMana;
		}
		else {
			this.currentExp += exp;
		}
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

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}
	
	public int getStrength() {
		return this.strength;
	}
	
	public void setStrength(int strength) {
		this.strength  = strength;
	}
	
	
	public int getStamina() {
		return this.stamina;
	}
	
	public void setStamina(int stamina) {
		this.stamina = stamina;
	}
	
	
	public int getIntelligence() {
		return this.intelligence;
	}
	
	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}
	
	public void setExp(int exp) {
		this.currentExp = exp;
	}
	
	public int getExp() {
		return this.currentExp;
	}
	
	public void setExpToNextLvl(int exp) {
		this.expToNextLevel = exp;
	}
	
	public void setLvl(int lvl) {
		this.currentLvl = lvl;
	}
	
	public int getLvl() {
		return this.currentLvl;
	}
	
	public int getBaseHealth() {
		return this.baseHealth;
	}
	
	public int getBaseMana() {
		return this.baseMana;
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
