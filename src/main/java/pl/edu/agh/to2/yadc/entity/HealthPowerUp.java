package pl.edu.agh.to2.yadc.entity;

import pl.edu.agh.to2.yadc.config.GlobalConfig;
import pl.edu.agh.to2.yadc.render.ImageLoader;

public class HealthPowerUp extends PowerUp {

    private int value;
    
    public HealthPowerUp(int xPos, int yPos, int value) {
        super(xPos, yPos);
        this.value = value;
        this.texture = ImageLoader.active.fetchImage("resources/ui_heart_full.png");
    }
    
    @Override
    public void performCollisionAction(Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if(player.getStatManager().getCurrentHealth() + this.value >= player.getStatManager().getMaxHealth()) {
                player.getStatManager().setHealth(player.getStatManager().getMaxHealth());
            } else {
                player.getStatManager().setHealth(player.getStatManager().getCurrentHealth() + this.value);
            }
            GlobalConfig.get().printToChatBox("You have gained " + this.value + " HP");
            area.removeEntity(this);
        }
    }

    @Override
    public void advanceSelf(double delta) {
        super.advanceSelf(delta);
    }

}