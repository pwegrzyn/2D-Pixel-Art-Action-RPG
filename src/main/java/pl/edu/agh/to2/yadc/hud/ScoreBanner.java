package pl.edu.agh.to2.yadc.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import pl.edu.agh.to2.yadc.entity.Player;
import pl.edu.agh.to2.yadc.physics.Advanceable;
import pl.edu.agh.to2.yadc.render.Camera;
import pl.edu.agh.to2.yadc.render.Renderable;

public class ScoreBanner implements Renderable, Advanceable {

    private Player player;
    private String currentScoreString;

    @Override
    public void advanceSelf(double delta) {
        currentScoreString = "Score: " + player.getScore();
    }

    @Override
    public void renderSelf(Graphics graphics, Camera camera) {
        graphics.setColor(Color.ORANGE);
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 13);
        graphics.setFont(font);
        graphics.drawString(this.currentScoreString, 210, 20);
    }

    public void bindPlayer(Player player) {
        this.player = player;
    }

}