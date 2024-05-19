package world;

import entity.Entity;
import item.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import monster.Monster;
import player.Player;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TileMap extends GridPane {
    public Random randomX = new Random();
    public Random randomY = new Random();
    List<ItemEntity> itemEntities = new ArrayList<>();
    List<Entity> entities = new ArrayList<Entity>();
    private String[] Levels = new String[]{
            String.format("%s/src/world/Level/level1.csv", System.getProperty("user.dir")),
            String.format("%s/src/world/Level/level2.csv", System.getProperty("user.dir")),
            String.format("%s/src/world/Level/backroom.csv", System.getProperty("user.dir")),
    };
    private List<List<String>> map;
    private String pathToCsv;
    private Player player;
    private Monster monster;
    private ItemEntity itemPotion;
    private Rectangle hitBoxWall;
    private List<Shape> bounds = new ArrayList<Shape>();
    private List<ItemPotion> itemPotions = new ArrayList<ItemPotion>();
    private List<Monster> monsters = new ArrayList<Monster>();
    private List<QuestItem> questItems = new ArrayList<QuestItem>();

    public TileMap(String pathToCsv, Stage stage) throws FileNotFoundException {
        //level1(Levels[0], stage);
        backRoom(getLevels()[2], stage);
    }

    public String[] getLevels() {
        return Levels;
    }

    public void setLevels(String[] levels) {
        Levels = levels;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public List<ItemEntity> getItemEntities() {
        return itemEntities;
    }

    public void setItemEntities(List<ItemEntity> itemEntities) {
        this.itemEntities = itemEntities;
    }

    public Rectangle getHitBoxWall() {
        return hitBoxWall;
    }

    public void setHitBoxWall(Rectangle hitBoxWall) {
        this.hitBoxWall = hitBoxWall;
    }

    public List<List<String>> getMap() {
        return map;
    }

    public void setMap(List<List<String>> map) {
        this.map = map;
    }

    public String getPathToCsv() {
        return pathToCsv;
    }

    public void setPathToCsv(String pathToCsv) {
        this.pathToCsv = pathToCsv;
    }

    public void genMap(List<List<String>> map) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(getPathToCsv()));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                map.add(Arrays.asList(values));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showMap(List<List<String>> map, TileMap tileMap) throws FileNotFoundException {
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                switch (map.get(i).get(j)) {
                    case "1":
                        placeMap(i, j, "images/tile/tile000.png");
                        break;
                    case "2":
                        placeMap(i, j, "images/tile/tile001.png");
                        break;
                    case "3":
                        Wall newWall = new Wall("Wall", 50, 50, tileMap);
                        placeEntity(newWall, i, j);
                        entities.add(newWall);
                        break;
                    case "4":
                        Spike newSpike = new Spike("Spike", 50, 50, tileMap);
                        placeEntity(newSpike, i, j);
                        entities.add(newSpike);
                        break;
                    case "q1":
                        placeMap(i, j, "images/tile/tile000.png");
                        QuestItem newQuestItem = new QuestItem("QuestItem", tileMap);
                        placeItemEntity(newQuestItem, i, j);
                        itemEntities.add(newQuestItem);
                        break;
                    case "f":
                        Wall wallFountain = new Wall("fountain", 50, 50, tileMap);
                        placeEntity(wallFountain, i, j);
                        entities.add(wallFountain);
                        break;
                }
            }
        }
    }

    private void placeMap(int i, int j, String imagePath) {
        ImageView tile = new ImageView(imagePath);
        tile.setFitWidth(50);
        tile.setFitHeight(50);
        GridPane.setRowIndex(tile, i);
        GridPane.setColumnIndex(tile, j);
        this.getChildren().add(tile);
    }

    public void placeEntity(Entity entity, int i, int j) {
        GridPane.setRowIndex(entity.getBoxEntity(), i);
        GridPane.setColumnIndex(entity.getBoxEntity(), j);
        this.getChildren().add(entity.getBoxEntity());
    }

    public void removeEntity(Entity entity) {
        this.getChildren().remove(entity.getBoxEntity());
    }

    public void moveEntity(Entity entity, int i, int j) {
        GridPane.setRowIndex(entity.getBoxEntity(), i);
        GridPane.setColumnIndex(entity.getBoxEntity(), j);
    }

    public void placeItemEntity(ItemEntity itemEntity, int i, int j) {
        GridPane.setRowIndex(itemEntity.getItemStackPane(), i);
        GridPane.setColumnIndex(itemEntity.getItemStackPane(), j);
        this.getChildren().add(itemEntity.getItemStackPane());
    }

    public void removeItemEntity(ItemEntity itemEntity) {
        this.getChildren().remove(itemEntity.getItemStackPane());
    }

    public void moveItemEntity(ItemEntity itemEntity, int i, int j) {
        GridPane.setRowIndex(itemEntity.getItemStackPane(), i);
        GridPane.setColumnIndex(itemEntity.getItemStackPane(), j);
    }

    public void placeWall(StackPane wall, int i, int j) {
        GridPane.setRowIndex(wall, i);
        GridPane.setColumnIndex(wall, j);
        this.getChildren().add(wall);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public ItemEntity getItemPotion() {
        return itemPotion;
    }

    public void setItemPotion(ItemEntity itemPotion) {
        this.itemPotion = itemPotion;
    }

    public List<Shape> getBounds() {
        return bounds;
    }

    public void setBounds(List<Shape> bounds) {
        this.bounds = bounds;
    }

    public List<ItemPotion> getItemPotions() {
        return itemPotions;
    }

    public void setItemPotions(List<ItemPotion> itemPotions) {
        this.itemPotions = itemPotions;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

    public List<QuestItem> getQuestItems() {
        return questItems;
    }

    public void setQuestItems(List<QuestItem> questItems) {
        this.questItems = questItems;
    }

    public void Level2(String pathToCsv, Stage stage) throws FileNotFoundException {
        this.setOnKeyPressed(null);
        for (ItemEntity item : itemPotions) {
            removeItemEntity(item);
        }
        getChildren().clear(); // Clear the existing map
        setPathToCsv(pathToCsv);
        setMap(new ArrayList<>());
        genMap(getMap());
        showMap(getMap(), this);

        placeEntity(getPlayer(), 14, 1);

        // Add three monsters to the map
        Monster monster1 = new Monster("Monster 1", 20, 20, player, this);
        Monster monster2 = new Monster("Monster 2", 20, 20, player, this);
        Monster monster3 = new Monster("Monster 3", 20, 20, player, this);

        // Set the position of the monsters
        placeEntity(monster1, 5, 5);
        placeEntity(monster2, 10, 10);
        placeEntity(monster3, 15, 15);

        // Add the monsters to the list of monsters
        entities.add(monster1);
        entities.add(monster2);
        entities.add(monster3);

        ButtonBackRoom(stage);
    }

    public void level1(String string, Stage stage) throws FileNotFoundException {
        this.setOnKeyPressed(null);
        for (ItemEntity item : itemPotions) {
            removeItemEntity(item);
        }
        getChildren().clear();
        setPathToCsv(string);
        setMap(new ArrayList<>());
        this.setVgap(-1);
        this.setHgap(-1);
        genMap(getMap());
        showMap(getMap(), this);

        ItemEntity potionHeal = new ItemPotion("POTION_HEAL", this);
        ItemEntity potionSpeed = new ItemPotion("POTION_SPEED", this);
        ItemEntity potionStrength = new ItemPotion("POTION_STRENGTH", this);
        ItemEntity potionDamage = new ItemPotion("POTION_DAMAGE", this);
        //QuestItem newQuestItem = new QuestItem("GG", this);
        itemEntities.add(potionHeal);
        itemEntities.add(potionSpeed);
        itemEntities.add(potionStrength);
        itemEntities.add(potionDamage);

        //itemEntities.add(newQuestItem);

        player = new Player("Hadjuse", this, itemEntities, entities);

        placeEntity(player, 14, 1);
        ButtonBackRoom(stage);
    }

    public void backRoom(String pathToCsv, Stage stage) throws FileNotFoundException {
        //this.setOnKeyPressed(null);
        for (ItemEntity item : itemPotions) {
            removeItemEntity(item);
        }
        this.setVgap(-1);
        this.setHgap(-1);
        getChildren().clear(); // Clear the existing map
        setPathToCsv(pathToCsv);
        setMap(new ArrayList<>());
        genMap(getMap());
        showMap(getMap(), this);

        setPlayer(new Player("hadjuse", this, getItemEntities(), getEntities()));
        placeEntity(getPlayer(), 14, 7);
        ButtonLevel1(stage);
        ButtonLevel2(stage);
    }

    private void ButtonLevel2(Stage stage) {
        Button changeMapButton = new Button("2");
        changeMapButton.setOnAction(event -> {
            try {
                player.getInventory().clear();
                itemEntities.clear();
                entities.clear();
                this.Level2(Levels[1], stage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        GridPane.setColumnIndex(changeMapButton, 9);
        GridPane.setRowIndex(changeMapButton, 0);
        this.getChildren().add(changeMapButton);
    }

    private void ButtonLevel1(Stage stage) {
        Button changeMapButton = new Button("1");
        changeMapButton.setOnAction(event -> {
            try {
                player.getInventory().clear();
                itemEntities.clear();
                entities.clear();
                this.level1(Levels[0], stage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        GridPane.setColumnIndex(changeMapButton, 6);
        GridPane.setRowIndex(changeMapButton, 0);
        this.getChildren().add(changeMapButton);
    }
    private void ButtonBackRoom(Stage stage) {
        Button changeMapButton = new Button("Back");
        changeMapButton.setOnAction(event -> {
            try {
                player.getInventory().clear();
                itemEntities.clear();
                entities.clear();
                this.backRoom(Levels[2], stage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        GridPane.setColumnIndex(changeMapButton, 0);
        GridPane.setRowIndex(changeMapButton, 0);
        this.getChildren().add(changeMapButton);
    }
}