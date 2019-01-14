package pl.edu.agh.to2.yadc.entity;

public abstract class PowerUp extends Entity {

    public PowerUp(int xPos, int yPos) {
        super(xPos, yPos, 5);
    }

    @Override
    abstract public void performCollisionAction(Entity entity);

}