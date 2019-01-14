package pl.edu.agh.to2.yadc.entity;

import pl.edu.agh.to2.yadc.config.GlobalConfig;

public class ManaPowerUp extends PowerUp {

    private int value;

    public ManaPowerUp(int xPos, int yPos, int value) {
        super(xPos, yPos);
        this.value = value;
    }

    @Override
    public void performCollisionAction(Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (player.getStatManager().getCurrentMana() + this.value >= player.getStatManager().getMaxMana()) {
                player.getStatManager().setMana(player.getStatManager().getMaxMana());
            } else {
                player.getStatManager().setMana(player.getStatManager().getCurrentMana() + this.value);
            }
            GlobalConfig.get().printToChatBox("You have gained " + this.value + " Mana");
            area.removeEntity(this);
        }
    }

    @Override
    public void advanceSelf(double delta) {
        // nothing
    }

}