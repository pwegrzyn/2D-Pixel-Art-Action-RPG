package pl.edu.agh.to2.yadc.item;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.to2.yadc.entity.Player;

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

    public boolean removeItemById(String id) {
        Item toRemove = null;
        for(Item item : this.items) {
            if(item.getId().equals(id)) {
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

    public boolean removeItem(Item item) {
        if (item != null) {
            this.items.remove(item);
            this.usedSpace -= item.getWeight();
            return true;
        } else {
            return false;
        }
    }

    public int useHealthPotion(Player player) {
        int res = 0;
        Consumable found = null;
        for(Item item : this.items) {
            if(item instanceof HealthPotion) {
                if(found == null) found = (Consumable)item;
                res++;
            }
        }
        if(res == 0) return -1;
        else {
            found.consume(player);
            return res - 1;
        }
    }

    public int useManaPotion(Player player) {
        int res = 0;
        Consumable found = null;
        for (Item item : this.items) {
            if (item instanceof ManaPotion) {
                if (found == null)
                    found = (Consumable) item;
                res++;
            }
        }
        if (res == 0)
            return -1;
        else {
            found.consume(player);
            return res - 1;
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

    public int getUsedSpace() {
        return this.usedSpace;
    }

    public List<Item> getItems() {
        return this.items;
    }

}