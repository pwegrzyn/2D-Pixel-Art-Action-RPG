package pl.edu.agh.to2.yadc.entity;

import pl.edu.agh.to2.yadc.config.GlobalConfig;

public class SpeedPowerUp extends PowerUp {

    private double multiplier;
    private double duration;

    public SpeedPowerUp(int xPos, int yPos, double mult, double time) {
        super(xPos, yPos);
        this.multiplier = mult;
        this.duration = time;
    }

    @Override
    public void performCollisionAction(Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            player.changeSpeedTemporarily(this.duration, this.multiplier);
            GlobalConfig.get().printToChatBox("Gotta go fast!");
            area.removeEntity(this);
        }
    }

    @Override
    public void advanceSelf(double delta) {
        // nothing
    }

}