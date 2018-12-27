package pl.edu.agh.to2.yadc.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import pl.edu.agh.to2.yadc.quest.Quest;
import pl.edu.agh.to2.yadc.quest.QuestLog;
import pl.edu.agh.to2.yadc.quest.SlayQuest;
import pl.edu.agh.to2.yadc.render.Camera;
import pl.edu.agh.to2.yadc.render.Renderable;

public class QuestTrackerHUD implements Renderable {

    private QuestLog questLog;

    @Override
    public void renderSelf(Graphics graphics, Camera camera) {

        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(380, 20, 85, 13);

        graphics.setColor(Color.BLACK);
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        graphics.setFont(font);

        graphics.drawString("Quest Tracker:", 382, 31);

        graphics.setColor(Color.WHITE);
        font = new Font(Font.SANS_SERIF, Font.PLAIN, 9);
        graphics.setFont(font);

        int xPosInit = 388;
        int yPosInit = 45;
        int counter = 0;
        
        for(Quest quest : questLog.getActiveQuests()) {
            graphics.drawString(quest.getName() , xPosInit, yPosInit + counter * 50);
            graphics.drawString(quest.getShortDescription(), xPosInit + 2, yPosInit + 10 + counter * 50);
            if(quest instanceof SlayQuest) {
                SlayQuest slayQuest = (SlayQuest) quest;
                graphics.drawString("Progress: " + slayQuest.getProgress() + " / " + slayQuest.getToKill(), xPosInit + 2, yPosInit + 20 + counter * 50);
            }
            counter++;
        }

    }

    public void bindQuestLog(QuestLog quests) {
        this.questLog = quests;
    }

}