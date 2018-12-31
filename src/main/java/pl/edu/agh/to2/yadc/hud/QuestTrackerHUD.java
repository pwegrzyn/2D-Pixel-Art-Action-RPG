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

        graphics.setColor(Color.YELLOW);
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        graphics.setFont(font);

        graphics.drawString("Quest Tracker:", 390, 21);

        graphics.setColor(Color.WHITE);
        font = new Font(Font.SANS_SERIF, Font.PLAIN, 9);
        graphics.setFont(font);

        int xPosInit = 397;
        int yPosInit = 35;
        int counter = 0;

        if(questLog.getActiveQuests().size() == 0) {
            graphics.drawString("Check the quest board!", xPosInit - 5, yPosInit);
        }
        
        for(Quest quest : questLog.getActiveQuests()) {
            graphics.drawString(quest.getName() , xPosInit, yPosInit + counter * 40);
            graphics.drawString(quest.getShortDescription(), xPosInit + 2, yPosInit + 10 + counter * 40);
            if(quest instanceof SlayQuest) {
                SlayQuest slayQuest = (SlayQuest) quest;
                graphics.drawString("Progress: " + slayQuest.getProgress() + " / " + slayQuest.getToKill(), xPosInit + 2, yPosInit + 20 + counter * 40);
            }
            counter++;
        }

    }

    public void bindQuestLog(QuestLog quests) {
        this.questLog = quests;
    }

}