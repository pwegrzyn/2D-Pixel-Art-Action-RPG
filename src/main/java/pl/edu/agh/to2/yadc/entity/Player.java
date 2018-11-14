package pl.edu.agh.to2.yadc.entity;

import pl.edu.agh.to2.yadc.input.InputManager;
import pl.edu.agh.to2.yadc.render.RenderManager;

public class Player extends Entity {

    private int velocity;
    
    public Player(double xInit, double yInit) {
        super(xInit, yInit);
        setTexture("resources\\test_entity.png");
        this.velocity = 70;
    }


    @Override
    public void advanceSelf(double delta) {

        if (InputManager.upPressed()) {
            this.yPos += this.velocity * delta;
        }

        if (InputManager.downPressed()) {
            this.yPos -= this.velocity * delta;
        }

        if (InputManager.leftPressed()) {
            this.xPos += this.velocity * delta;
        }

        if (InputManager.rightPressed()) {
            this.xPos -= this.velocity * delta;
        }

        RenderManager.getCurrentCamera().moveTo((int) this.xPos, (int) this.yPos);

    }

}