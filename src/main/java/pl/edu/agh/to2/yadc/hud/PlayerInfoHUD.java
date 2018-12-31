package pl.edu.agh.to2.yadc.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import pl.edu.agh.to2.yadc.entity.StatManager;
import pl.edu.agh.to2.yadc.physics.Advanceable;
import pl.edu.agh.to2.yadc.render.Camera;
import pl.edu.agh.to2.yadc.render.Renderable;


public class PlayerInfoHUD implements Renderable, Advanceable {

    private String currentLvlString;
    private String currentExpString;
    private StatManager playerStats;
    private int HPBarValue;
    private int ManaBarValue;
    
    @Override
    public void advanceSelf(double delta) {
        int currentExp = playerStats.getCurrentExp();
        int expToNextLevel = playerStats.getExpToNextLvl();
        int percentExpToNextLevel = (int)((((double)currentExp) / expToNextLevel) * 100);
        currentExpString = "EXP: " + currentExp + " / " + expToNextLevel + " (" + percentExpToNextLevel + " %)";
        currentLvlString = "LVL: " + playerStats.getCurrentLvl();
        HPBarValue = (int) ((((double)playerStats.getCurrentHealth()) / playerStats.getMaxHealth()) * 167);
        ManaBarValue = (int) ((((double)playerStats.getCurrentMana()) / playerStats.getMaxMana()) * 167);
    }

    @Override
	public void renderSelf(Graphics graphics, Camera camera) {
        graphics.setColor(Color.black);
        graphics.fillRect(4, 283, 169, 23);
        // HP
        graphics.setColor(Color.GRAY);
        graphics.fillRect(5, 295, 167, 10);
        graphics.setColor(Color.RED);
        graphics.fillRect(5, 295, HPBarValue, 10);
        // Mana
        graphics.setColor(Color.GRAY);
        graphics.fillRect(5, 284, 167, 10);
        graphics.setColor(Color.BLUE);
        graphics.fillRect(5, 284, ManaBarValue, 10);
        // EXP
        graphics.setColor(Color.YELLOW);
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 9);
        graphics.setFont(font);
        graphics.drawString(this.currentExpString, 5, 278);
        // LVL
        font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        graphics.setFont(font);
        graphics.setColor(Color.YELLOW);
        graphics.drawString(this.currentLvlString, 5, 266);
    }
    
    public void bindStatManager(StatManager stats) {
        this.playerStats = stats;
    }

}