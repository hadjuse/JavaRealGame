package inventory;

import item.ItemPotion;
import item.QuestItem;
import item.UsableItem;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<QuestItem> questItemList;
    private List<UsableItem> usableItemList;
    private List<ItemPotion> itemPotionList;

    public Inventory(int quantity) throws FileNotFoundException {
        setConsumableItemList(new ArrayList<>(quantity));
        setUsableItemList(new ArrayList<>(quantity));
        setItemPotionList(new ArrayList<>(quantity));
    }

    public List<QuestItem> getConsumableItemList() {
        return questItemList;
    }

    public void setConsumableItemList(List<QuestItem> questItemList) {
        this.questItemList = questItemList;
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

    public void addQuestItem(QuestItem questItem) {
        questItemList.add(questItem);
    }

    public void addUsableItem(UsableItem usableItem) {
        usableItemList.add(usableItem);
    }

    public void addItemPotion(ItemPotion itemPotion) {
        itemPotionList.add(itemPotion);
    }

    public void removeConsumableItem(QuestItem questItem) {
        questItemList.remove(questItem);
    }

    public void removeUsableItem(UsableItem usableItem) {
        usableItemList.remove(usableItem);
    }

    public void removeItemPotion(ItemPotion itemPotion) {
        itemPotionList.remove(itemPotion);
    }

    public void clear() {
        questItemList.clear();
        usableItemList.clear();
        itemPotionList.clear();
    }

    public int getConsumableItemCount() {
        return questItemList.size();
    }

    public int getUsableItemCount() {
        return usableItemList.size();
    }

    public int getItemPotionCount() {
        return itemPotionList.size();
    }

    public QuestItem getConsumableItem(int index) {
        return questItemList.get(index);
    }

    public UsableItem getUsableItem(int index) {
        return usableItemList.get(index);
    }

    public ItemPotion getItemPotion(int index) {
        return itemPotionList.get(index);
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "questItemList=" + questItemList +
                ", usableItemList=" + usableItemList +
                ", itemPotionList=" + itemPotionList +
                '}';
    }
}
