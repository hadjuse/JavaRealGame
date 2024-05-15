package pnj;

import entity.Entity;
import inventory.Inventory;
import item.ItemEntity;
import item.ItemPotion;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import player.Player;
import world.TileMap;

import java.io.FileNotFoundException;

public class PotionSeller extends Entity {
    private Inventory inventory;
    private StackPane sellerPane;

    public PotionSeller(String name, double width, double height, TileMap map, Player player, Stage stage) throws FileNotFoundException {
        super(name, width, height, map);
        setMoney(100000);
        setInventory(new Inventory(5));
        getInventory().addItemPotion(new ItemPotion("POTION_HEAL", player, map, 1, stage));
        getInventory().addItemPotion(new ItemPotion("POTION_STRENGTH", player, map, 1, stage));
        getInventory().addItemPotion(new ItemPotion("POTION_DAMAGE", player, map, 1, stage));
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
