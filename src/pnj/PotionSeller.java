package pnj;

import entity.Entity;
import inventory.Inventory;
import javafx.scene.layout.StackPane;

public class PotionSeller extends Entity {
    private Inventory inventory;
    private StackPane sellerPane;
    public PotionSeller(String name, double width, double height) {
        super(name, width, height);
        setMoney(100000);
    }

    @Override
    public void basicAttack(Entity entity) {

    }

    @Override
    public void actionAfterDeath() {

    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public StackPane getSellerPane() {
        return sellerPane;
    }

    public void setSellerPane(StackPane sellerPane) {
        this.sellerPane = sellerPane;
    }
    public StackPane renderSeller() {
        StackPane stackPane = new StackPane();
        return stackPane;
    }
}
