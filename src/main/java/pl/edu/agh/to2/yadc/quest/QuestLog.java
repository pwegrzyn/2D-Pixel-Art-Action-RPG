package pl.edu.agh.to2.yadc.quest;

import java.util.ArrayList;
import java.util.List;

public class QuestLog {

    private final int MAX_NUMBER_QUESTS = 25;
    private int currentNumberOfQuests;
    private List<Quest> inProgressQuests;
    private List<Quest> completedQuests;
    private List<Quest> abandonedQuests;

    public QuestLog() {
        this.currentNumberOfQuests = 0;
        this.inProgressQuests = new ArrayList<>();
        this.completedQuests = new ArrayList<>();
        this.abandonedQuests = new ArrayList<>();
    }

    public boolean addQuest(Quest quest) {
        if(this.currentNumberOfQuests + 1 <= this.MAX_NUMBER_QUESTS) {
            this.inProgressQuests.add(quest);
            this.currentNumberOfQuests++;
            return true;
        }
        return false;
    }

}