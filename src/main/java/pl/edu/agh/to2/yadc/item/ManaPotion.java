package pl.edu.agh.to2.yadc.item;

import pl.edu.agh.to2.yadc.entity.Player;

public class ManaPotion extends Item implements Consumable {

    private int manaValue;

    public ManaPotion() {
        super(1);
        this.manaValue = 50;
        this.description = "Mana Potion";
    }

    @Override
    public void consume(Player player) {
        if (player.getStatManager().getCurrentMana() + this.manaValue >= player.getStatManager().getMaxMana()) {
            player.getStatManager().setMana(player.getStatManager().getMaxMana());
        } else {
            player.getStatManager().setMana(player.getStatManager().getCurrentMana() + this.manaValue);
        }
        player.getEquipment().getBackpack().removeItem(this);
    }

}