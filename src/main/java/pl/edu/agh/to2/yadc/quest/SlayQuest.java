package pl.edu.agh.to2.yadc.quest;

public class SlayQuest extends Quest {

    private String monsterType;
    private int toKill;
    private int alreadyKilled;
    
    public SlayQuest(String monsterType, int howManyToSlay, long goldReward, int expReward) {
        this.isCompleted = false;
        this.wasAccepted = false;
        this.toKill = howManyToSlay;
        this.alreadyKilled = 0;
        this.goldReward = goldReward;
        this.expReward = expReward;
        this.description = "Contract: " + monsterType +
            "\n I was told to slay " + toKill + " " + monsterType + "s." +
            "\n I will be rewarded handsomly if I manage to succeed.\n";
    }

    public boolean progress() {
        if(this.isCompleted || !this.wasAccepted)
            return false;
        this.alreadyKilled++;
        if(this.alreadyKilled == this.toKill) {
            complete();
            return true;
        }
        return false;
    }

    public int getProgress() {
        return this.alreadyKilled;
    }

    public String getMonsterType() {
        return this.monsterType;
    }
    
    @Override
    void complete() {
        this.isCompleted = true;
        System.out.println("Another contract fulfilled");
    }

}