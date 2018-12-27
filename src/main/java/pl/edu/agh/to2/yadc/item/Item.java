package pl.edu.agh.to2.yadc.item;

import java.util.Random;

public class Item {

    protected String id;
    protected ItemQuality quality;
    protected int weight;
    protected final int MAX_DEGRADATION_STATUS = 100;
    private static Random randomIdGenerator = new Random();
    protected String description;

    public Item(int weight) {
        this.id = generateId();
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    public String getId() {
        return this.id;
    }

    public ItemQuality getQuality() {
        return this.quality;
    }

    public String getDescription() {
        return description;
    }

    private String generateId() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 3;
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (randomIdGenerator.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

}