package pl.edu.agh.to2.yadc.entity;

import pl.edu.agh.to2.yadc.config.GlobalConfig;

public class ScorePowerUp extends PowerUp {

    private int value;

    public ScorePowerUp(int xPos, int yPos, int value) {
        super(xPos, yPos);
        this.value = value;
    }

    @Override
    public void performCollisionAction(Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            player.addScore(value);
            GlobalConfig.get().printToChatBox("You have gained " + this.value + " bonus score.");
            area.removeEntity(this);
        }
    }

    @Override
    public void advanceSelf(double delta) {
        // nothing
    }
}