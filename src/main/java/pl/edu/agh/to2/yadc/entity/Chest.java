package pl.edu.agh.to2.yadc.entity;

import java.awt.image.BufferedImage;
import java.util.Random;

import pl.edu.agh.to2.yadc.config.GlobalConfig;
import pl.edu.agh.to2.yadc.render.ImageLoader;

public class Chest extends PowerUp {

    private int numberOfLootz;
    private boolean isOpen;
    private double openTimer = 0;
    private double openCooldown = 2.0;
    private BufferedImage openTexture;
    private BufferedImage lootTexture;

    public Chest(int xPos, int yPos, int value) {
        super(xPos, yPos);
        this.numberOfLootz = value;
        this.texture = ImageLoader.active.fetchImage("resources/chest_empty_open_anim_f0.png");
        this.openTexture = ImageLoader.active.fetchImage("resources/chest_empty_open_anim_f2.png");
        this.lootTexture = ImageLoader.active.fetchImage("resources/loot.png");
    }

    @Override
    public void performCollisionAction(Entity entity) {
        if(this.isOpen) return;
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if(player.getEquipment().getBackpack().useKey()) {
                this.isOpen = true;
                this.texture = this.openTexture;
                GlobalConfig.get().printToChatBox("Sweet lootz!");
                Random random = new Random();
                for(int i = 0; i < this.numberOfLootz; i++) {
                    Loot droppedLoot = new Loot((int) this.getXPos() + random.nextInt(60) - 30,
                            (int) this.getYPos() + random.nextInt(60) - 20);
                    droppedLoot.setArea(this.area);
                    droppedLoot.setTexture(this.lootTexture);
                    this.area.addEntity(droppedLoot);
                }
            } else {
                GlobalConfig.get().printToChatBox("You need a key to open this chest");
            }
        }
    }

    @Override
    public void advanceSelf(double delta) {
        super.advanceSelf(delta);
        if(this.isOpen) {
            this.openTimer += delta;
            if(this.openTimer > this.openCooldown) {
                this.area.removeEntity(this);
            }
        }
    }

    public void setOpenTexture(BufferedImage img) {
        this.openTexture = img;
    }

    public void setLootTexture(BufferedImage img) {
        this.lootTexture = img;
    }

}