package pl.edu.agh.to2.yadc.item;

public class RangedWeapon extends Item {

    private int baseDamage;
    private int degradationStatus;
    private int range;
    private int attackSpeed;

    public RangedWeapon(int dmg, int attackSpeed, int range, ItemQuality quality) {
        super(3);
        this.baseDamage = dmg;
        this.degradationStatus = 0;
        this.attackSpeed = attackSpeed;
        this.quality = quality;
        this.range = range;
        this.description = "Ranged Weapon";
    }

    public int getDamage() {
        return (int) (this.MAX_DEGRADATION_STATUS - (double)this.degradationStatus / MAX_DEGRADATION_STATUS) * this.baseDamage;
    }

    public int getAttackSpeed() {
        return this.attackSpeed;
    }

    public int getRange() {
        return this.range;
    }

}