package pnj;

import entity.Entity;
import inventory.Inventory;
import item.ItemPotion;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import player.Player;
import world.TileMap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PotionSeller extends Entity {

    // TODO configure transaction and place him in the beginning Map
    public PotionSeller(String name, double width, double height, TileMap map, Player player) throws FileNotFoundException {
        super(name, width, height, map);
        setMoney(100000);
        setBoxEntity(renderSeller());
        setInventory(new Inventory(5));
        getInventory().addItemPotion(new ItemPotion("POTION_HEAL", map, player), 1);
        getInventory().addItemPotion(new ItemPotion("POTION_STRENGTH", map, player), 1);
       // getInventory().addItemPotion(new ItemPotion("POTION_DAMAGE", map,player), 1);
        getInventory().addItemPotion(new ItemPotion("POTION_SPEED", map, player), 1);
        getBoxEntity().setOnMouseClicked(_ -> {
            // Show the mini window with the potions
            showPotionWindow(player);
        });
    }

    private void showPotionWindow(Player player) {
        // Create a new stage for the mini window
        Stage potionStage = new Stage();

        // Set the title and size of the stage
        potionStage.setTitle("Potion Seller");
        potionStage.setWidth(720);
        potionStage.setHeight(200);

        // Create a grid pane for the potions
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add the potions to the grid pane
        getInventory().getItemPotionList().forEach(potion -> {
            try {
                // Create an ImageView for the potion
                ImageView potionImageView = new ImageView(new Image(new FileInputStream(potion.getSpritePath()[potion.getItemEnum().ordinal()])));

                // Create a Label for the name of the potion
                Label potionNameLabel = new Label(potion.getName());

                // Create a Label for the price of the potion
                Label potionPriceLabel = new Label(String.valueOf(potion.getValueMoney()));

                // Create a Button for the potion
                Button potionButton = new Button();
                potionButton.setGraphic(potionImageView);
                potionButton.setOnAction(event -> handlePotionPurchase(player, potion));

                // Add the Button, the name Label, and the price Label to the grid pane
                gridPane.add(potionButton, gridPane.getColumnCount(), 0);
                gridPane.add(potionNameLabel, gridPane.getColumnCount(), 1);
                gridPane.add(potionPriceLabel, gridPane.getColumnCount(), 2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        potionStage.setX(0);
        potionStage.setY(0);
        // Set the scene of the stage
        potionStage.setScene(new Scene(gridPane));

        // Show the stage
        potionStage.show();
    }


    private void handlePotionPurchase(Player player, ItemPotion potion) {
        // Check if the player has enough money to buy the potion
        if (player.getMoney() < potion.getValueMoney()) {
            System.out.println("Not enough money to buy the potion.");
            return;
        }
        boolean potionInInventory = player.getInventory().getItemPotionList().contains(potion);
        boolean enoughSpace = player.getInventory().getItemPotionList().size() < getInventory().getQuantity();
        // Deduct the price of the potion from the player's money
        if (!potionInInventory && enoughSpace) {
            player.setMoney(player.getMoney() - potion.getValueMoney());
            player.getInventory().addItemPotion(potion, 1);

            // Remove the potion from the seller's inventory
            getInventory().getItemPotionList().remove(potion);

            System.out.printf("%s bought potion %s%n", player.getName(), potion.getName());
        } else {
            System.out.println("Cannot add Item potion not enough space");
        }

    }

    public StackPane renderSeller() {
        StackPane stackPane = new StackPane();
        Image image = new Image("images/pnj/doc_idle_anim_f0.png");
        setBounds(new Rectangle(getWidth(), getHeight()));
        getBounds().setFill(new ImagePattern(image));
        getBounds().setOpacity(1);
        stackPane.getChildren().addAll(getBounds());
        return stackPane;
    }

}
