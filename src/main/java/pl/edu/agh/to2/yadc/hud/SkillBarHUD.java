package pl.edu.agh.to2.yadc.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import pl.edu.agh.to2.yadc.entity.Player;
import pl.edu.agh.to2.yadc.entity.ProjectileTypes;
import pl.edu.agh.to2.yadc.render.Camera;
import pl.edu.agh.to2.yadc.render.Renderable;

public class SkillBarHUD implements Renderable {

    private Player player;
    private BufferedImage skill_1Active;
    private BufferedImage skill_2Active;
    private BufferedImage skill_3Active;
    private BufferedImage skill_1Inactive;
    private BufferedImage skill_2Inactive;
    private BufferedImage skill_3Inactive;
    private BufferedImage consumable1;
    private BufferedImage consumable2;
    private BufferedImage empty;

    @Override
    public void renderSelf(Graphics graphics, Camera camera) {

        graphics.setColor(Color.black);
        graphics.fillRect(195, 283, 111, 23);
        
        graphics.setColor(Color.BLACK);
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 9);
        graphics.setFont(font);

        if (player.getActiveProjecile() == ProjectileTypes.SLOWING) {
            graphics.drawImage(this.skill_1Active, 196, 284, null);
        } else {
            graphics.drawImage(this.skill_1Inactive, 196, 284, null);
        }
        graphics.drawString("1", 205, 280);

        if (player.getActiveProjecile() == ProjectileTypes.STUNNING) {
            graphics.drawImage(this.skill_2Active, 196 + 22, 284, null);
        } else {
            graphics.drawImage(this.skill_2Inactive, 196 + 22, 284, null);
        }
        graphics.drawString("2", 205 + 22, 280);

        if (player.getActiveProjecile() == ProjectileTypes.TRIPLE) {
            graphics.drawImage(this.skill_3Active, 196 + 22 + 22, 284, null);
        } else {
            graphics.drawImage(this.skill_3Inactive, 196 + 22 + 22, 284, null);
        }
        graphics.drawString("3", 205 + 22 + 22, 280);

        if (player.getConsumable_1() > 0) {
            graphics.drawImage(this.consumable1, 196 + 22 + 22 + 22, 284, null);
        } else {
            graphics.drawImage(this.empty, 196 + 22 + 22 + 22, 284, null);
        }
        graphics.drawString("4", 205 + 22 + 22 + 22, 280);

        if (player.getConsumable_2() > 0) {
            graphics.drawImage(this.consumable2, 196 + 22 + 22 + 22 + 22, 284, null);
        } else {
            graphics.drawImage(this.empty, 196 + 22 + 22 + 22 + 22, 284, null);
        }
        graphics.drawString("5", 205 + 22 + 22 + 22 + 22, 280);

    }

    public void bindPlayer(Player player) {
        this.player = player;
    }

    public void setSkill_1ActiveTexture(BufferedImage texture) {
        this.skill_1Active = texture;
    }

    public void setSkill_2ActiveTexture(BufferedImage texture) {
        this.skill_2Active = texture;
    }

    public void setSkill_3ActiveTexture(BufferedImage texture) {
        this.skill_3Active = texture;
    }

    public void setSkill_1InactiveTexture(BufferedImage texture) {
        this.skill_1Inactive = texture;
    }

    public void setSkill_2InactiveTexture(BufferedImage texture) {
        this.skill_2Inactive = texture;
    }

    public void setSkill_3InactiveTexture(BufferedImage texture) {
        this.skill_3Inactive = texture;
    }

    public void setConsumable_1Texture(BufferedImage texture) {
        this.consumable1 = texture;
    }

    public void setConsumable_2Texture(BufferedImage texture) {
        this.consumable2 = texture;
    }

    public void setEmptyTexture(BufferedImage texture) {
        this.empty = texture;
    }

}