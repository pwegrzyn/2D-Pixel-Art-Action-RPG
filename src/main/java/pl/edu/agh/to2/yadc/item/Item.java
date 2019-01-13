package pl.edu.agh.to2.yadc.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import pl.edu.agh.to2.yadc.entity.Stats;

public class Item {

    protected String id;
    protected ItemQuality quality;
    protected int weight;
    protected final int MAX_DEGRADATION_STATUS = 100;
    private static Random randomIdGenerator = new Random();
    protected String description;
    
    protected Map<Stats, Integer> buffedStats;

    public Item(int weight) {
        this.id = generateId();
        this.weight = weight;
        
        Random rand = new Random();
        Map<Stats, Integer> buffedStats = new HashMap<>();
        buffedStats.put(Stats.BASE_HP, rand.nextInt(10));
        buffedStats.put(Stats.BASE_MANA, rand.nextInt(10));
        buffedStats.put(Stats.INT, rand.nextInt(10));
        buffedStats.put(Stats.MAG_DMG, rand.nextInt(10));
        buffedStats.put(Stats.PHY_DMG, rand.nextInt(10));
        buffedStats.put(Stats.SPEED, rand.nextInt(10));
        buffedStats.put(Stats.STAM, rand.nextInt(10));
        buffedStats.put(Stats.STR, rand.nextInt(10));
        
        this.buffedStats = buffedStats;
    }
    
    public Map<Stats, Integer> getBuffedStats(){
    	return this.buffedStats;
    }
    
    public void setBuffedStats(Map<Stats, Integer> buffedStats) {
    	this.buffedStats = buffedStats;
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