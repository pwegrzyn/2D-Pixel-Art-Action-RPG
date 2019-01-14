package pl.edu.agh.to2.yadc.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import pl.edu.agh.to2.yadc.config.GlobalConfig;
import pl.edu.agh.to2.yadc.entity.StatManager;
import pl.edu.agh.to2.yadc.physics.Advanceable;
import pl.edu.agh.to2.yadc.render.Camera;
import pl.edu.agh.to2.yadc.render.Renderable;

public class PauseBannerHUD implements Renderable, Advanceable {

    private String banner = "PAUSED";
    private String subBanner = "Press Enter or Esc to resume";
    private double bannerBlinkTimer = 0;
    private double bannerBlinkCooldown = 0.5;
    private boolean shouldBeRendered = false;
    private StatManager playerStats;

    @Override
    public void advanceSelf(double delta) {
        if(GlobalConfig.get().getFrozenRender()) {
            this.bannerBlinkTimer += delta;
            if(this.bannerBlinkTimer > this.bannerBlinkCooldown) {
                this.shouldBeRendered = !this.shouldBeRendered;
                this.bannerBlinkTimer = 0;
            }
        }
    }

    @Override
    public void renderSelf(Graphics graphics, Camera camera) {
        if(!this.shouldBeRendered || !GlobalConfig.get().getFrozenRender() || this.playerStats.getCurrentHealth() <= 0) return;
        graphics.setColor(Color.WHITE);
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 50);
        graphics.setFont(font);
        graphics.drawString(banner, 163, 170);
        font = new Font(Font.SANS_SERIF, Font.PLAIN, 17);
        graphics.setFont(font);
        graphics.drawString(subBanner, 157, 190);
    }

    public void bindStatManager(StatManager stat) {
        this.playerStats = stat;
    }

}