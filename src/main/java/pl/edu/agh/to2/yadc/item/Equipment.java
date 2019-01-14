package pl.edu.agh.to2.yadc.item;

import java.util.HashMap;
import java.util.Map;

//import com.google.common.base.CaseFormat;

import pl.edu.agh.to2.yadc.entity.Player;
import pl.edu.agh.to2.yadc.entity.Stats;

public class Equipment {

    private Backpack backpack;
    private Armor equippedHelm;
    private Armor equippedChest;
    private Armor equippedLegs;
    private Armor equippedGloves;
    private Armor equippedShoes;
    private MeleeWeapon equippedMeleeWeapon;
    private RangedWeapon equippedRangedWeapon;
    private long goldPieces;

    public Equipment() {
        this.backpack = new Backpack(100);
        this.goldPieces = 0;
    }

    public int calculateTotalArmorValue() {
        int armorSum = 0;
        if(equippedHelm != null) armorSum += equippedHelm.getArmorValue();
        if(equippedChest != null) armorSum += equippedChest.getArmorValue();
        if(equippedLegs != null) armorSum += equippedLegs.getArmorValue();
        if(equippedGloves != null) armorSum += equippedGloves.getArmorValue();
        if(equippedShoes != null) armorSum += equippedShoes.getArmorValue();
        return armorSum;
    }
    
    public Map<Stats, Integer> calculateTotalBuffedStats(){
    	Map<Stats, Integer> buffedStats = new HashMap<>();
    	buffedStats.put(Stats.BASE_HP, 0);
    	buffedStats.put(Stats.BASE_MANA, 0);
    	buffedStats.put(Stats.INT, 0);
    	buffedStats.put(Stats.MAG_DMG, 0);
    	buffedStats.put(Stats.PHY_DMG, 0);
    	buffedStats.put(Stats.SPEED, 0);
    	buffedStats.put(Stats.STAM, 0);
    	buffedStats.put(Stats.STR, 0);
    	
    	if(equippedHelm != null) buffedStats = combineStatMaps(buffedStats, equippedHelm.getBuffedStats());
    	if(equippedChest != null) buffedStats = combineStatMaps(buffedStats, equippedChest.getBuffedStats());
    	if(equippedGloves != null) buffedStats = combineStatMaps(buffedStats, equippedGloves.getBuffedStats());
    	if(equippedLegs != null) buffedStats = combineStatMaps(buffedStats, equippedLegs.getBuffedStats());
    	if(equippedShoes != null) buffedStats = combineStatMaps(buffedStats, equippedShoes.getBuffedStats());
    	if(equippedMeleeWeapon != null) buffedStats = combineStatMaps(buffedStats, equippedMeleeWeapon.getBuffedStats());
    	if(equippedRangedWeapon != null) buffedStats = combineStatMaps(buffedStats, equippedRangedWeapon.getBuffedStats());
    	
    	return buffedStats;
    }
    
    private Map<Stats, Integer> combineStatMaps(Map<Stats, Integer> baseMap, Map<Stats, Integer> newMap){
    	Map<Stats, Integer> result = new HashMap<>();
    	result.put(Stats.BASE_HP, baseMap.get(Stats.BASE_HP) + newMap.get(Stats.BASE_HP));
    	result.put(Stats.BASE_MANA, baseMap.get(Stats.BASE_MANA) + newMap.get(Stats.BASE_MANA));
    	result.put(Stats.INT, baseMap.get(Stats.INT) + newMap.get(Stats.INT));
    	result.put(Stats.MAG_DMG, baseMap.get(Stats.MAG_DMG) + newMap.get(Stats.MAG_DMG));
    	result.put(Stats.PHY_DMG, baseMap.get(Stats.PHY_DMG) + newMap.get(Stats.PHY_DMG));
    	result.put(Stats.SPEED, baseMap.get(Stats.SPEED) + newMap.get(Stats.SPEED));
    	result.put(Stats.STAM, baseMap.get(Stats.STAM) + newMap.get(Stats.STAM));
    	result.put(Stats.STR, baseMap.get(Stats.STR) + newMap.get(Stats.STR));
    	
    	return result;
    }

    // Backpack
    public boolean addToBackpack(Item item) {
        return this.backpack.addItem(item);
    }

    public Backpack getBackpack() {
        return this.backpack;
    }

    // Money
    public void addGoldPieces(long moneyz) {
        this.goldPieces += moneyz;
    }

    public boolean removeGoldPieces(long moneyz) {
        if(this.goldPieces >= moneyz) {
            this.goldPieces -= moneyz;
            return true;
        }
        return false;
    }

    public long getCurrentGold() {
        return this.goldPieces;
    }

    // Melee Weapon
    public boolean equipMeleeWeapon(MeleeWeapon meleeWeapon) {
        if(this.equippedRangedWeapon == null) {
            this.equippedMeleeWeapon = meleeWeapon;
            return true;
        }
        return false;
    }

    public boolean unequipMeleeWeapon() {
        if(this.equippedMeleeWeapon != null) {
            boolean freeSpaceExisted = this.backpack.addItem(this.equippedMeleeWeapon);
            this.equippedMeleeWeapon = null;
            return freeSpaceExisted;
        }
        return false;
    }

    public MeleeWeapon getEquippedMeleeWeapon() {
        return this.equippedMeleeWeapon;
    }

    // Ranged Weapon
    public boolean equipRangedWeapon(RangedWeapon rangedWeapon) {
        if(this.equippedMeleeWeapon == null) {
            this.equippedRangedWeapon = rangedWeapon;
            return true;
        }
        return false;
    }

    public boolean unequipRangedWeapon() {
        if(this.equippedRangedWeapon != null) {
            boolean freeSpaceExisted = this.backpack.addItem(this.equippedRangedWeapon);
            this.equippedRangedWeapon = null;
            return freeSpaceExisted;
        }
        return false;
    }

    public RangedWeapon getEquippedRangedWeapon() {
        return this.equippedRangedWeapon;
    }

    // Helm
    public boolean equipHelm(Armor helm) {
        if(helm.getArmorPiece() == ArmorPiece.HELM) {
            this.equippedHelm = helm;
            return true;
        }
        return false;
    }

    public boolean unequipHelm() {
        if(this.equippedHelm != null) {
            boolean freeSpaceExisted = this.backpack.addItem(this.equippedHelm);
            this.equippedHelm = null;
            return freeSpaceExisted;
        }
        return false;
    }

    public Armor getEquippedHelm() {
        return this.equippedHelm;
    }

    // Chest
    public boolean equipChest(Armor chest) {
        if(chest.getArmorPiece() == ArmorPiece.CHEST) {
            this.equippedChest = chest;
            return true;
        }
        return false;
    }

    public boolean unequipChest() {
        if(this.equippedChest != null) {
            boolean freeSpaceExisted = this.backpack.addItem(this.equippedChest);
            this.equippedChest = null;
            return freeSpaceExisted;
        }
        return false;
    }

    public Armor getEquippedChest() {
        return this.equippedChest;
    }

    // Legs
    public boolean equipLegs(Armor legs) {
        if(legs.getArmorPiece() == ArmorPiece.LEGS) {
            this.equippedLegs = legs;
            return true;
        }
        return false;
    }

    public boolean unequipLegs() {
        if(this.equippedLegs != null) {
            boolean freeSpaceExisted = this.backpack.addItem(this.equippedLegs);
            this.equippedLegs = null;
            return freeSpaceExisted;
        }
        return false;
    }

    public Armor getEquippedLegs() {
        return this.equippedHelm;
    }

    // Gloves
    public boolean equipGloves(Armor gloves) {
        if(gloves.getArmorPiece() == ArmorPiece.GLOVES) {
            this.equippedGloves = gloves;
            return true;
        }
        return false;
    }

    public boolean unequipGloves() {
        if(this.equippedGloves != null) {
            boolean freeSpaceExisted = this.backpack.addItem(this.equippedGloves);
            this.equippedGloves = null;
            return freeSpaceExisted;
        }
        return false;
    }

    public Armor getEquippedGloves() {
        return this.equippedGloves;
    }

    // Shoes
    public boolean equipShoes(Armor shoes) {
        if(shoes.getArmorPiece() == ArmorPiece.SHOES) {
            this.equippedShoes = shoes;
            return true;
        }
        return false;
    }

    public boolean unequipShoes() {
        if(this.equippedShoes != null) {
            boolean freeSpaceExisted = this.backpack.addItem(this.equippedShoes);
            this.equippedShoes = null;
            return freeSpaceExisted;
        }
        return false;
    }

    public Armor getEquippedShoes() {
        return this.equippedShoes;
    }
    
    public boolean equipItem(String id) {
    	Item item = backpack.getItemById(id.toLowerCase());
    	if (item != null) {
    		Equipment eq = Player.getEquipment();
    		if (item instanceof Armor) {
    			Armor armor = (Armor)item;
    			Armor armorBuf = null;
    			switch (armor.getArmorPiece()) {
    				case CHEST:
    					armorBuf = eq.getEquippedChest();
    					eq.equipChest(armor);
    					backpack.removeItemById(id.toLowerCase());
    					if (armorBuf != null) backpack.addItem(armorBuf);
    					break;
    				case HELM:
    					armorBuf = eq.getEquippedHelm();
    					eq.equipHelm(armor);
    					backpack.removeItemById(id.toLowerCase());
    					if (armorBuf != null) backpack.addItem(armorBuf);
    					break;
    				case LEGS:
    					armorBuf = eq.getEquippedLegs();
    					eq.equipLegs(armor);
    					backpack.removeItemById(id.toLowerCase());
    					if (armorBuf != null) backpack.addItem(armorBuf);
    					break;
    				case GLOVES:
    					armorBuf = eq.getEquippedGloves();
    					eq.equipGloves(armor);
    					backpack.removeItemById(id.toLowerCase());
    					if (armorBuf != null) backpack.addItem(armorBuf);
    					break;
    				case SHOES:
    					armorBuf = eq.getEquippedShoes();
    					eq.equipShoes(armor);
    					backpack.removeItemById(id.toLowerCase());
    					if (armorBuf != null) backpack.addItem(armorBuf);
    					break;
    			}
    			return true;
    				
    		}
    		else if (item instanceof RangedWeapon) {
    			RangedWeapon rangedWeapon = (RangedWeapon)item;
    			rangedWeapon = Player.getEquipment().getEquippedRangedWeapon();
    			backpack.removeItemById(id.toLowerCase());
    			if (rangedWeapon != null) backpack.addItem(rangedWeapon);
    			return true;
    		}
    		else {
    			return false;
    		}
    	}
    	else {
    		return false;
    	}
    }

}