package pl.edu.agh.to2.yadc.quest;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import pl.edu.agh.to2.yadc.config.GlobalConfig;
import pl.edu.agh.to2.yadc.entity.Entity;
import pl.edu.agh.to2.yadc.entity.Player;
import pl.edu.agh.to2.yadc.render.ImageLoader;


public class QuestBoard extends Entity {

    private List<Quest> availableQuests;
    private double spawnQuestTimer = 0;
    private double spawnQuestCooldown = 30;
    private final int MAX_AVAILABLE_QUESTS = 3;
    private final int SLAY_POOL_SIZE = 3;
    private final int SLAY_QUEST_MAX_SIZE = 25;
    private final int SLAY_QUEST_MIN_SIZE = 5;
    private int currentAvailableQuests;
    private List<List<String>> slayTypePool;
    
    public QuestBoard(int xLoc, int yLoc) {
        super(xLoc, yLoc, 20);
        this.availableQuests = new LinkedList<>();
        this.currentAvailableQuests = 0;
        this.slayTypePool = new LinkedList<>();
        this.slayTypePool.add(Arrays.asList("Ranger", "Ranger Slayer"));
        this.slayTypePool.add(Arrays.asList("Marauder", "Marauder Slayer"));
        this.slayTypePool.add(Arrays.asList("Any Mob Type", "Universal Slayer"));
        this.texture = ImageLoader.active.fetchImage("resources/questboard.png");
    }
    
    @Override
    public void advanceSelf(double delta) {
        super.advanceSelf(delta);
        spawnQuestTimer += delta;
        if(spawnQuestTimer > spawnQuestCooldown) {
            spawnQuestTimer = 0;
            if(currentAvailableQuests == MAX_AVAILABLE_QUESTS) return;
            Random rnd = new Random();
            int index = rnd.nextInt(SLAY_POOL_SIZE);
            int slayQuestSize = rnd.nextInt(SLAY_QUEST_MAX_SIZE + 1 - SLAY_QUEST_MIN_SIZE) + SLAY_QUEST_MIN_SIZE;
            SlayQuest slayQuest = new SlayQuest(slayTypePool.get(index).get(1), slayTypePool.get(index).get(0), 
                slayQuestSize, slayQuestSize * 100 + 500, slayQuestSize * 20 + 200, slayQuestSize * 2000 + 5000);
            this.availableQuests.add(slayQuest);
            currentAvailableQuests++;
        }
    }

    @Override
	public void performCollisionAction(Entity entity) {

		if(entity instanceof Player) {
            GlobalConfig.get().printToChatBox("---Available Quests:---");
            int counter = 0;
            if(availableQuests.size() == 0) {
                GlobalConfig.get().printToChatBox("None. Check back later.");
                return;
            }
            for(Quest quest : availableQuests) {
                GlobalConfig.get().printToChatBox(counter + ") " + quest.getName());
                GlobalConfig.get().printToChatBox(quest.getShortDescription());
                GlobalConfig.get().printToChatBox("Rewards: ");
                GlobalConfig.get().printToChatBox(" - Gold: " + quest.getGoldReward());
                GlobalConfig.get().printToChatBox(" - Exp: " + quest.getExpReward());
                counter++;
            }
        }
		
    }
    
    public List<Quest> getAvailableQuests() {
        return this.availableQuests;
    }

    public void remove(Quest toRemove) {
        if(this.availableQuests.remove(toRemove)) {
            this.currentAvailableQuests--;
        }
    }

}