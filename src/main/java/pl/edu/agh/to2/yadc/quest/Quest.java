package pl.edu.agh.to2.yadc.quest;

public abstract class Quest {

    protected String description;
    protected boolean isCompleted;
    protected boolean wasAccepted;
    protected long goldReward;
    protected int expReward;

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

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean wasAccepted() {
        return wasAccepted;
    }

}