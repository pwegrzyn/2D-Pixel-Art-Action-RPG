package pl.edu.agh.to2.yadc.item;

public enum ItemQuality {
    COMMON(0),
    UNCOMMON(1),
    RARE(2),
    EPIC(3),
    MYTHIC(4);

    int index;

    ItemQuality(int i) {
        index = i;
    }

    int getIndex() {
        return index;
    }

    public static ItemQuality getByIndex(int i) {
        for (ItemQuality a : ItemQuality.values()) {
            if (a.getIndex() == i) {
                return a;
            }
        }
        return null;
    }
}