package pl.edu.agh.to2.yadc.item;

import java.util.ArrayList;
import java.util.List;

public class Backpack {

    private int baseCapacity;
    private int usedSpace;
    private List<Item> items;

    public Backpack(int capacity) {
        this.items = new ArrayList<>();
        this.usedSpace = 0;
        this.baseCapacity = capacity;
    }

    public boolean addItem(Item item) {
        if(usedSpace + item.getWeight() < baseCapacity) {
            this.items.add(item);
            this.usedSpace += item.getWeight();
            return true;
        }
        return false;
    }

    public boolean removeItemById(long id) {
        Item toRemove = null;
        for(Item item : this.items) {
            if(item.getId() == id) {
                toRemove = item;
            }
        }
        if(toRemove != null) {
            this.items.remove(toRemove);
            this.usedSpace -= toRemove.getWeight();
            return true;
        } else {
            return false;
        }
    }

    public int getBaseCapacity() {
        return this.baseCapacity;
    }

    public boolean setBaseCapacity(int newCapacity) {
        if (newCapacity < this.usedSpace) {
            return false;
        } else {
            this.baseCapacity = newCapacity;
            return true;
        }
    }

    public int getFreeSpace() {
        return this.baseCapacity - this.usedSpace;
    }

}