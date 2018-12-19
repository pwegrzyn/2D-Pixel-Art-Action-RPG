package pl.edu.agh.to2.yadc.item;

public class Armor extends Item {

    private ArmorPiece piece;
    private ArmorType type;
    private int armorValue;
    private int degradationStatus;

    public Armor(ArmorPiece piece, ArmorType type, int armorValue, ItemQuality quality) {
        super(5);
        this.piece = piece;
        this.type = type;
        this.armorValue = armorValue;
        this.degradationStatus = 0;
        this.quality = quality;
    }

    public ArmorPiece getArmorPiece() {
        return this.piece;
    }

    public ArmorType getArmorType() {
        return this.type;
    }

    public int getArmorValue() {
        return (int) (this.MAX_DEGRADATION_STATUS - (double)this.degradationStatus / MAX_DEGRADATION_STATUS) * this.armorValue;
    }

}