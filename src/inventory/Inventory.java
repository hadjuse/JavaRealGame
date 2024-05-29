package inventory;

import item.ItemGeneral;
import item.QuestItem;
import item.UsableItem;
import obs.Observable;
import obs.Observer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Inventory implements Observer {
    private List<QuestItem> questItemList;
    private List<UsableItem> usableItemList;
    private List<ItemGeneral> itemGeneralList;
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

    public List<ItemGeneral> getItemPotionList() {
        return itemGeneralList;
    }

    public void setItemPotionList(List<ItemGeneral> itemGeneralList) {
        this.itemGeneralList = itemGeneralList;
    }

    public void addQuestItem(QuestItem questItem, int quantity) {
        questItemList.add(questItem);
    }

    public void addUsableItem(UsableItem usableItem, int quantity) {
        usableItemList.add(usableItem);
    }

    public void addItemPotion(ItemGeneral itemGeneral, int quantity) {
        itemGeneralList.add(itemGeneral);
    }

    public void removeConsumableItem(QuestItem questItem) {
        questItemList.remove(questItem);
    }

    public void removeUsableItem(UsableItem usableItem) {
        usableItemList.remove(usableItem);
    }

    public void removeItemPotion(ItemGeneral itemGeneral) {
        itemGeneralList.remove(itemGeneral);
    }

    public void clear() {
        questItemList.clear();
        usableItemList.clear();
        itemGeneralList.clear();
    }

    public int getConsumableItemCount() {
        return questItemList.size();
    }

    public int getUsableItemCount() {
        return usableItemList.size();
    }

    public int getItemPotionCount() {
        return itemGeneralList.size();
    }

    public QuestItem getConsumableItem(int index) {
        return questItemList.get(index);
    }

    public UsableItem getUsableItem(int index) {
        return usableItemList.get(index);
    }

    public ItemGeneral getItemPotion(int index) {
        return itemGeneralList.get(index);
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "questItemList=" + questItemList +
                ", usableItemList=" + usableItemList +
                ", itemGeneralList=" + itemGeneralList +
                '}';
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public void update(Observable observable) {
        System.out.println("Inventory updated");
    }
}
