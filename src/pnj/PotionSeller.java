package pnj;

import entity.Entity;
import inventory.Inventory;
import item.ItemEntity;
import item.ItemGeneral;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
import java.util.List;

public class PotionSeller extends Entity {
    private Player player;
    private TileMap map;
    private List<Entity> entities;
    // TODO configure transaction and place him in the beginning Map
    public PotionSeller(String name, double width, double height, TileMap map, Player player, List<Entity> entities) throws FileNotFoundException {
        super(name, width, height, map);
        setMoney(100000);
        setPlayer(player);
        setMap(map);
        setEntities(entities);
        setBoxEntity(renderSeller());
        setInventory(new Inventory(5));
        getInventory().addItemPotion(new ItemGeneral("POTION_HEAL", map, player, getEntities()), 1);
        getInventory().addItemPotion(new ItemGeneral("POTION_STRENGTH", map, player, getEntities()), 1);
        getInventory().addItemPotion(new ItemGeneral("POTION_SPEED", map, player, getEntities()), 1);
        getInventory().addItemPotion(new ItemGeneral("POTION_WHO_OPEN_DOOR", map, player, getEntities()), 1);
        getBoxEntity().setOnMouseClicked(event -> {
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

        // Create a GridPane to contain the potion buttons and labels
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add the potions to the GridPane
        int rowIndex = 0;
        for (ItemGeneral potion : getInventory().getItemPotionList()) {
            // Create an ImageView for the potion
            ImageView potionImageView = new ImageView(new Image(getClass().getResourceAsStream(potion.getSpritePath()[potion.getItemEnum().ordinal()])));

            // Create a Label for the name of the potion
            Label potionNameLabel = new Label(potion.getName());

            // Create a Label for the price of the potion
            Label potionPriceLabel = new Label(String.valueOf(potion.getValueMoney()));

            // Create a Button for the potion
            Button potionButton = new Button();
            potionButton.setGraphic(potionImageView);
            potionButton.setOnAction(event -> handlePotionPurchase(player, potion, potionStage));

            // Add the Button, the name Label, and the price Label to the GridPane
            gridPane.add(potionButton, 0, rowIndex);
            gridPane.add(potionNameLabel, 1, rowIndex);
            gridPane.add(potionPriceLabel, 2, rowIndex);

            rowIndex++;
        }

        // If there are more potions than can fit in the window, add the GridPane to a ScrollPane
        if (rowIndex * 30 > potionStage.getHeight()) { // assuming each row is 30 pixels tall
            ScrollPane scrollPane = new ScrollPane(gridPane);
            scrollPane.setFitToWidth(true);
            potionStage.setScene(new Scene(scrollPane));
        } else {
            potionStage.setScene(new Scene(gridPane));
        }

        // Show the stage
        potionStage.show();
    }


    private void handlePotionPurchase(Player player, ItemGeneral potion, Stage stage) {
        // Check if the player has enough money to buy the potion
        if (player.getMoney() < potion.getValueMoney()) {
            System.out.println("Not enough money to buy the potion.");
            stage.close();
            return;
        }
        boolean potionInInventory = player.getInventory().getItemPotionList().contains(potion);
        boolean enoughSpace = player.getInventory().getItemPotionList().size() <= getPlayer().getInventory().getQuantity();
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
        stage.close();
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public TileMap getMap() {
        return map;
    }

    public void setMap(TileMap map) {
        this.map = map;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }
}
