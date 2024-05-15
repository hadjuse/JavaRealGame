package item;

import javafx.scene.layout.StackPane;

import java.io.FileNotFoundException;

public abstract class ItemEntity {
    private double valueMoney;
    private StackPane itemStackPane;
    private int quantity;

    public double getValueMoney() {
        return valueMoney;
    }

    public void setValueMoney(double valueMoney) {
        this.valueMoney = valueMoney;
    }

    public StackPane getItemStackPane() {
        return itemStackPane;
    }

    public void setItemStackPane(StackPane itemStackPane) {
        this.itemStackPane = itemStackPane;
    }

    public abstract StackPane renderItem() throws FileNotFoundException;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
    public void removeQuantity(int quantity) {
        this.quantity -= quantity;
    }
}
