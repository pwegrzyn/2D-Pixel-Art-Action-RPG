package pl.edu.agh.to2.yadc.item;

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

}