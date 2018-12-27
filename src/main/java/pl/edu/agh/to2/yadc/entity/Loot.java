package pl.edu.agh.to2.yadc.entity;

import java.util.Random;

import pl.edu.agh.to2.yadc.config.GlobalConfig;
import pl.edu.agh.to2.yadc.item.Armor;
import pl.edu.agh.to2.yadc.item.ArmorPiece;
import pl.edu.agh.to2.yadc.item.ArmorType;
import pl.edu.agh.to2.yadc.item.Item;
import pl.edu.agh.to2.yadc.item.ItemQuality;
import pl.edu.agh.to2.yadc.item.MeleeWeapon;
import pl.edu.agh.to2.yadc.item.RangedWeapon;

public class Loot extends Entity {

    private Item droppedItem;
    private int droppedGold;
    private static final int ARMOR_PIECE_MAX = 5;
    private static final int ARMOR_TYPE_MAX = 4;
    private static final int ITEM_QUALITY_MAX = 5;
    private static final int ARMOR_VALUE_MAX = 50;
    private static final int ARMOR_VALUE_MIN = 20;
    private static final int MELEE_WEAPON_DMG_MAX = 50;
    private static final int MELEE_WEAPON_DMG_MIN = 20;
    private static final int RANGED_WEAPON_DMG_MAX = 45;
    private static final int RANGED_WEAPON_DMG_MIN = 15;
    private static final int MELEE_WEAPON_ATK_SPEED_MAX = 5;
    private static final int MELEE_WEAPON_ATK_SPEED_MIN = 1;
    private static final int RANGED_WEAPON_ATK_SPEED_MIN = 1;
    private static final int RANGED_WEAPON_ATK_SPEED_MAX = 4;
    private static final int RANGED_WEAPON_RANGE_MAX = 200;
    private static final int RANGED_WEAPON_RANGE_MIN = 50;
    private static final int MAX_GOLD_DROP = 1000;

    public Loot(int xLoc, int yLoc) {
        super(xLoc, yLoc, 5);
        Random rnd = new Random();
        switch(rnd.nextInt(4)) {
            case 0:
                // Dropped Armor Piece
                droppedItem = new Armor(ArmorPiece.getByIndex(rnd.nextInt(ARMOR_PIECE_MAX)), 
                    ArmorType.getByIndex(rnd.nextInt(ARMOR_TYPE_MAX)), rnd.nextInt(ARMOR_VALUE_MAX + 1 - ARMOR_VALUE_MIN) + ARMOR_VALUE_MIN, 
                    ItemQuality.getByIndex(rnd.nextInt(ITEM_QUALITY_MAX)));
            break;
            case 1:
                // Dropped Melee Weapon
                droppedItem = new MeleeWeapon(rnd.nextInt(MELEE_WEAPON_DMG_MAX + 1 - MELEE_WEAPON_DMG_MIN) + MELEE_WEAPON_DMG_MIN, 
                    rnd.nextInt(MELEE_WEAPON_ATK_SPEED_MAX + 1 - MELEE_WEAPON_ATK_SPEED_MIN) + MELEE_WEAPON_ATK_SPEED_MIN, 
                    ItemQuality.getByIndex(rnd.nextInt(ITEM_QUALITY_MAX)));
            break;
            case 2:
                // Dropped Ranged Weapon
                droppedItem = new RangedWeapon(rnd.nextInt(RANGED_WEAPON_DMG_MAX + 1 - RANGED_WEAPON_DMG_MIN) + RANGED_WEAPON_DMG_MIN, 
                    rnd.nextInt(RANGED_WEAPON_ATK_SPEED_MAX + 1 - RANGED_WEAPON_ATK_SPEED_MIN) + RANGED_WEAPON_ATK_SPEED_MIN,
                    rnd.nextInt(RANGED_WEAPON_RANGE_MAX + 1 - RANGED_WEAPON_RANGE_MIN) + RANGED_WEAPON_RANGE_MIN,
                    ItemQuality.getByIndex(rnd.nextInt(ITEM_QUALITY_MAX)));
            break;
            case 3:
                // Dropped Gold
                droppedItem = null;
                droppedGold = rnd.nextInt(MAX_GOLD_DROP) + 10;
            break;
            default: break;
        }
    }
    
    @Override
    public void performCollisionAction(Entity entity) {

        if (entity instanceof Player) {
            Player player = (Player) entity;
            if(droppedItem != null) {
                player.getEquipment().addToBackpack(droppedItem);
                GlobalConfig.get().printToChatBox("You have picked up a new item.");
            } else {
                player.getEquipment().addGoldPieces(droppedGold);
                GlobalConfig.get().printToChatBox("You have picked up some gold.");
            }
            area.removeEntity(this);
        }

    }
    
    @Override
    public void advanceSelf(double delta) {
        // nothing
    }

}