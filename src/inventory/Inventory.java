package inventory;

import item.ConsumableItem;
import item.ItemPotion;
import item.UsableItem;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<ConsumableItem> consumableItemList;
    private List<UsableItem> usableItemList;
    private List<ItemPotion> itemPotionList;

    public List<ConsumableItem> getConsumableItemList() {
        return consumableItemList;
    }
    public void setConsumableItemList(List<ConsumableItem> consumableItemList) {
        this.consumableItemList = consumableItemList;
    }
    public List<UsableItem> getUsableItemList() {
        return usableItemList;
    }
    public void setUsableItemList(List<UsableItem> usableItemList) {
        this.usableItemList = usableItemList;
    }
    public List<ItemPotion> getItemPotionList() {
        return itemPotionList;
    }
    public void setItemPotionList(List<ItemPotion> itemPotionList) {
        this.itemPotionList = itemPotionList;
    }
    public void addConsumableItem(ConsumableItem consumableItem) {
        consumableItemList.add(consumableItem);
    }
    public void addUsableItem(UsableItem usableItem) {
        usableItemList.add(usableItem);
    }
    public void addItemPotion(ItemPotion itemPotion) {
        itemPotionList.add(itemPotion);
    }
    public void removeConsumableItem(ConsumableItem consumableItem) {
        consumableItemList.remove(consumableItem);
    }
    public void removeUsableItem(UsableItem usableItem) {
        usableItemList.remove(usableItem);
    }
    public void removeItemPotion(ItemPotion itemPotion) {
        itemPotionList.remove(itemPotion);
    }
    public void clear() {
        consumableItemList.clear();
        usableItemList.clear();
        itemPotionList.clear();
    }
    public int getConsumableItemCount() {
        return consumableItemList.size();
    }
    public int getUsableItemCount() {
        return usableItemList.size();
    }
    public int getItemPotionCount() {
        return itemPotionList.size();
    }
    public ConsumableItem getConsumableItem(int index) {
        return consumableItemList.get(index);
    }
    public UsableItem getUsableItem(int index) {
        return usableItemList.get(index);
    }
    public ItemPotion getItemPotion(int index) {
        return itemPotionList.get(index);
    }

    public Inventory(int quantity) throws FileNotFoundException {
        setConsumableItemList(new ArrayList<>(quantity));
        setUsableItemList(new ArrayList<>(quantity));
        setItemPotionList(new ArrayList<>(quantity));
    }
}
