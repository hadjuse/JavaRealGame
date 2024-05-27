package pnj;

import entity.Entity;
import inventory.Inventory;
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

import java.io.FileNotFoundException;
import java.util.List;

public class PnjQuest extends Entity {
    private TileMap map;
    private Player player;
    private int i;
    private int j;
    private List<Entity> entities;
    private Stage stage;
    // TODO QUEST PNJ that allow interaction.
    public PnjQuest(String name, double width, double height, TileMap map, Player player, int i, int j, List<Entity> entities, Stage stage) throws FileNotFoundException {
        super(name, width, height, map);
        setStage(stage);
        setEntities(entities);
        setMap(map);
        setPlayer(player);
        setLife(120);
        setMoney(400);
        setI(i);
        setJ(j);
        setInventory(new Inventory(5));
        setBoxEntity(renderPNJ());
        getMap().moveEntity(this, getI(), getJ());
        getEntities().add(this);
            getInventory().addItemPotion(new ItemGeneral("POTION_HEAL", map, player, getEntities()), 1);
        getInventory().addItemPotion(new ItemGeneral("POTION_STRENGTH", map, player, getEntities()), 1);
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
            //potionButton.setOnAction(event -> handlePotionPurchase(player, potion, potionStage));

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

    public StackPane renderPNJ() {
        StackPane stackPane = new StackPane();
        Image image = new Image("images/pnj/wizzard_m_run_anim_f0.png");
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

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
