package pl.edu.agh.to2.yadc.item;

public class MeleeWeapon extends Item {

    private int baseDamage;
    private int degradationStatus;
    private int attackSpeed;

    public MeleeWeapon(int dmg, int attackSpeed, ItemQuality quality) {
        super(7);
        this.baseDamage = dmg;
        this.degradationStatus = 0;
        this.attackSpeed = attackSpeed;
        this.quality = quality;
        this.description = "Melee Weapon";
    }

    public int getDamage() {
        return (int) (this.MAX_DEGRADATION_STATUS - (double)this.degradationStatus / MAX_DEGRADATION_STATUS) * this.baseDamage;
    }

    public int getAttackSpeed() {
        return this.attackSpeed;
    }

}