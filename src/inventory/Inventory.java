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
    private int quantity;
    public Inventory(int quantity) throws FileNotFoundException {
        setConsumableItemList(new ArrayList<>(quantity));
        setUsableItemList(new ArrayList<>(quantity));
        setItemPotionList(new ArrayList<>(quantity));
        setQuantity(quantity);
    }

    public List<QuestItem> getQuestItemList() {
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

    public void addQuestItem(QuestItem questItem, int quantity) {
        // Check if the quantity limit is exceeded
        if (questItemList.size() < quantity) {
            // If not, add the item to the list
            questItemList.add(questItem);
        } else {
            // If the limit is exceeded, you can print a message or handle it in a different way
            System.out.println("Cannot add quest item. The limit is exceeded.");
        }
    }

    public void addUsableItem(UsableItem usableItem, int quantity) {
        if (usableItemList.size() < quantity) {
            usableItemList.add(usableItem);
        } else {
            System.out.println("Cannot add usable item. The limit is exceeded.");
        }
    }

    public void addItemPotion(ItemPotion itemPotion, int quantity) {
        if (itemPotionList.size() < quantity) {
            itemPotionList.add(itemPotion);
        } else {
            System.out.println("Cannot add usable item. The limit is exceeded.");
        }
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
