package pnj;

import entity.Entity;
import inventory.Inventory;
import item.ItemPotion;
import javafx.scene.layout.StackPane;
import player.Player;
import world.TileMap;

import java.io.FileNotFoundException;

public class PotionSeller extends Entity {
    private Inventory inventory;
    private StackPane sellerPane;

    public PotionSeller(String name, double width, double height, TileMap map, Player player) throws FileNotFoundException {
        super(name, width, height, map);
        setMoney(100000);
        setInventory(new Inventory(5));
        getInventory().addItemPotion(new ItemPotion("POTION_HEAL", map));
        getInventory().addItemPotion(new ItemPotion("POTION_STRENGTH", map));
        getInventory().addItemPotion(new ItemPotion("POTION_DAMAGE", map));
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
