package pl.edu.agh.to2.yadc.item;

import java.util.Random;

public class Item {

    protected long id;
    protected ItemQuality quality;
    protected int weight;
    protected final int MAX_DEGRADATION_STATUS = 100;
    private static Random randomIdGenerator = new Random();

    public Item(int weight) {
        this.id = randomIdGenerator.nextLong();
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    public long getId() {
        return this.id;
    }

    public ItemQuality getQuality() {
        return this.quality;
    }

}