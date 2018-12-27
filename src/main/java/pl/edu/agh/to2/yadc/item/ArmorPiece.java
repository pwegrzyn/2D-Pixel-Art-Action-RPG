package pl.edu.agh.to2.yadc.item;

public enum ArmorPiece {
    CHEST(0),
    HELM(1),
    LEGS(2),
    GLOVES(3),
    SHOES(4);

    int index;
    
    ArmorPiece(int i) {
        index = i;
    }

    int getIndex() {
        return index;
    }

    public static ArmorPiece getByIndex(int i) {
        for(ArmorPiece a : ArmorPiece.values()) {
            if(a.getIndex() == i) {
                return a;
            }
        }
        return null;
    }
}