package pl.edu.agh.to2.yadc.item;

import pl.edu.agh.to2.yadc.entity.Player;


public class HealthPotion extends Item implements Consumable {

    private int healthValue;
    
    public HealthPotion() {
        super(1);
        this.healthValue = 100;
        this.description = "Health Potion";
    }
    
    @Override
    public void consume(Player player) {
        if (player.getStatManager().getCurrentHealth() + this.healthValue >= player.getStatManager().getMaxHealth()) {
            player.getStatManager().setHealth(player.getStatManager().getMaxHealth());
        } else {
            player.getStatManager().setHealth(player.getStatManager().getCurrentHealth() + this.healthValue);
        }
        player.getEquipment().getBackpack().removeItem(this);
    }

}