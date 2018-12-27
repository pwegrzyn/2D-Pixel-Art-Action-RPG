package pl.edu.agh.to2.yadc.quest;

public abstract class Quest {

    protected String name;
    protected String description;
    protected String shortDescription;
    protected boolean isCompleted;
    protected boolean wasAccepted;
    protected long goldReward;
    protected int expReward;
    protected QuestLog questLog;

    public void accept() {
        wasAccepted = true;
        isCompleted = false;
    }

    public void decline() {
        wasAccepted = false;
        isCompleted = false;
    }

    abstract void complete();

    public String getDescription() {
        if(description == null) {
            return "-No description-";
        } else {
            return description;
        }
    }

    public String getShortDescription() {
        if (shortDescription == null) {
            return "---";
        } else {
            return shortDescription;
        }
    }

    public String getName() {
        if (name == null) {
            return "Unknown Quest";
        } else {
            return name;
        }
    }

    public long getGoldReward() {
        return goldReward;
    }

    public int getExpReward() {
        return expReward;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean wasAccepted() {
        return wasAccepted;
    }

    public void setQuestLog(QuestLog log) {
        questLog = log;
    }

}