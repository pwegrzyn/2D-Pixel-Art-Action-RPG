package pl.edu.agh.to2.yadc.item;

public enum ArmorType {
    LIGHT_ARMOR(0),
    MEDIUM_ARMOR(1),
    HEAVY_ARMOR(2),
    HYBRID_ARMOR(3);

    int index;

    ArmorType(int i) {
        index = i;
    }

    int getIndex() {
        return index;
    }

    public static ArmorType getByIndex(int i) {
        for (ArmorType a : ArmorType.values()) {
            if (a.getIndex() == i) {
                return a;
            }
        }
        return null;
    }
}