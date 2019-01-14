package pl.edu.agh.to2.yadc.hud;

import java.awt.Graphics;

import pl.edu.agh.to2.yadc.config.GlobalConfig;
import pl.edu.agh.to2.yadc.entity.Player;
import pl.edu.agh.to2.yadc.input.InputManager;
import pl.edu.agh.to2.yadc.physics.Advanceable;
import pl.edu.agh.to2.yadc.render.Camera;
import pl.edu.agh.to2.yadc.render.Renderable;


public class HUD implements Renderable, Advanceable {

    private ChatBoxHUD chatbox;
    private PlayerInfoHUD playerInfo;
    private QuestTrackerHUD questTracker;
    private ScoreBanner scoreBanner;
    private SkillBarHUD skillBar;
    private PauseBannerHUD pauseBanner;

    public HUD() {
        this.playerInfo = new PlayerInfoHUD();
        this.chatbox = new ChatBoxHUD();
        this.questTracker = new QuestTrackerHUD();
        this.scoreBanner = new ScoreBanner();
        this.skillBar = new SkillBarHUD();
        this.pauseBanner = new PauseBannerHUD();
    }
    
    @Override
	public void renderSelf(Graphics graphics, Camera camera) {
        if(!GlobalConfig.get().getUIVisibility()) return;
        playerInfo.renderSelf(graphics, camera);
        chatbox.renderSelf(graphics, camera);
        questTracker.renderSelf(graphics, camera);
        scoreBanner.renderSelf(graphics, camera);
        skillBar.renderSelf(graphics, camera);
        pauseBanner.renderSelf(graphics, camera);
	}

    @Override
    public void advanceSelf(double delta) {
        chatbox.advanceSelf(delta);
        playerInfo.advanceSelf(delta);
        scoreBanner.advanceSelf(delta);
        pauseBanner.advanceSelf(delta);
    }

    public void printToChatBox(String str) {
        this.chatbox.printToChatBox(str);
    }

    public boolean isChatInputActive() {
        return this.chatbox.isChatActive();
    }

    public void setInputManager(InputManager input) {
        this.chatbox.setInputManager(input);
    }

    public void bindPlayer(Player player) {
        this.playerInfo.bindStatManager(player.getStatManager());
        this.questTracker.bindQuestLog(player.getQuestLog());
        this.scoreBanner.bindPlayer(player);
        this.skillBar.bindPlayer(player);
        this.pauseBanner.bindStatManager(player.getStatManager());
    }

    public SkillBarHUD getSkillBar() {
        return this.skillBar;
    }

}