package pl.edu.agh.to2.yadc.entity;

import pl.edu.agh.to2.yadc.config.GlobalConfig;
import pl.edu.agh.to2.yadc.entity.Entity;
import pl.edu.agh.to2.yadc.entity.Player;

public class Vendor extends Entity {

    private int keyPrice;
    private int HPPotPrice;
    private int ManaPotPrice;

    public Vendor(int xLoc, int yLoc) {
        super(xLoc, yLoc, 20);
        this.keyPrice = 3000;
        this.ManaPotPrice = 600;
        this.HPPotPrice = 500;
    }

    @Override
    public void advanceSelf(double delta) {
        super.advanceSelf(delta);
    }

    @Override
    public void performCollisionAction(Entity entity) {

        if (entity instanceof Player) {
            GlobalConfig.get().printToChatBox("---Gnome Merchant---");
            GlobalConfig.get().printToChatBox("Shiny key: " + this.keyPrice + " gold");
            GlobalConfig.get().printToChatBox("Health Potion: " + this.HPPotPrice + " gold");
            GlobalConfig.get().printToChatBox("Mana Potion: " + this.ManaPotPrice + " gold");
        }

    }
}