package world;

import entity.Entity;
import item.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import monster.Monster;
import monster.MonsterEnum;
import player.Player;
import pnj.PnjQuest;
import pnj.PotionSeller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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
            "level1.csv",
            "level2.csv",
            "level3.csv",
            "level4.csv",
            "level5.csv",
            "backroom.csv"
    };

    private List<List<String>> map;
    private String pathToCsv;
    private Player player;
    private Monster monster;
    private PotionSeller potionSeller;
    private ItemEntity itemPotion;
    private List<Shape> bounds = new ArrayList<Shape>();
    private List<ItemGeneral> itemGenerals = new ArrayList<ItemGeneral>();
    private List<Monster> monsters = new ArrayList<Monster>();
    private List<QuestItem> questItems = new ArrayList<QuestItem>();
    private MonsterEnum monsterEnum;
    private PnjQuest quest;
    public TileMap(Stage stage) throws FileNotFoundException {
        //level1(Levels[0], stage);
        setPathToCsv("backroom.csv");
        setMap(new ArrayList<>());
        genMap(getMap());
        showMap(getMap(), this);
        setPlayer(new Player("hadjuse", this, getItemEntities(), getEntities(), stage, 14, 1));
        backRoom(getLevels()[5], stage);

    }

    public void genMap(List<List<String>> map) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(getPathToCsv())))) {
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
        this.removeEntity(entity);
        GridPane.setRowIndex(entity.getBoxEntity(), i);
        GridPane.setColumnIndex(entity.getBoxEntity(), j);
        if (entity instanceof Player player) {
            player.getHitBox().setTranslateX(0);
            player.getHitBox().setTranslateY(0);
        }
        this.getChildren().add(entity.getBoxEntity());
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
        this.removeItemEntity(itemEntity);
        GridPane.setRowIndex(itemEntity.getItemStackPane(), i);
        GridPane.setColumnIndex(itemEntity.getItemStackPane(), j);
        itemEntity.getHitBox().setTranslateX(0);
        itemEntity.getHitBox().setTranslateY(0);
        this.getChildren().add(itemEntity.getItemStackPane());
    }

    public void level1(String string, Stage stage) throws FileNotFoundException {
        this.setOnKeyPressed(null);
        for (ItemEntity item : itemGenerals) {
            removeItemEntity(item);
        }
        itemEntities.clear();
        entities.clear();
        getChildren().clear();
        setPathToCsv(string);
        setMap(new ArrayList<>());
        this.setVgap(-1);
        this.setHgap(-1);
        genMap(getMap());
        showMap(getMap(), this);

        ItemEntity potionHeal = new ItemGeneral("POTION_HEAL", this, getPlayer(), entities, stage);
        //ItemEntity potionSpeed = new ItemGeneral("POTION_SPEED", this, getPlayer(), entities, stage);
        //ItemEntity potionStrength = new ItemGeneral("POTION_STRENGTH", this, getPlayer(), entities, stage);
        //QuestItem newQuestItem = new QuestItem("GG", this);
        itemEntities.add(potionHeal);
        //itemEntities.add(potionSpeed);
        //itemEntities.add(potionStrength);

        //itemEntities.add(newQuestItem);

        //player = new Player("Hadjuse", this, itemEntities, entities);

        moveEntity(getPlayer(), 14, 1);
        ButtonBackRoom(stage);
    }

    public void Level2(String pathToCsv, Stage stage) throws FileNotFoundException {
        generation(pathToCsv);


        // Add three monsters to the map
        Monster monster1 = new Monster(MonsterEnum.MONSTER_1, player, this, stage, 5, 5, entities);
        Monster monster2 = new Monster(MonsterEnum.MONSTER_2, player, this, stage, 10, 10, entities);
        Monster monster3 = new Monster(MonsterEnum.MONSTER_3, player, this, stage, 10, 12, entities);


        // Add item to monster list
        ItemGeneral potion = new ItemGeneral("KILL", this, getPlayer(), entities, stage);
        itemEntities.add(potion);
        monster1.getInventory().addItemPotion(potion, 1);
        moveEntity(getPlayer(), 14, 1);
        ButtonBackRoom(stage);
    }

    public void level3(String pathToCsv, Stage stage) throws FileNotFoundException {
        generation(pathToCsv);
        itemEntities.clear();
        entities.clear();
        ItemGeneral potion1 = new ItemGeneral("TELEPORTATION", this, getPlayer(), entities, stage);
        ItemGeneral potion2 = new ItemGeneral("POTION_HEAL", this, getPlayer(), entities, stage);
        itemEntities.add(potion1);
        itemEntities.add(potion2);
        placeItemEntity(potion1, 1, 1);
        placeItemEntity(potion2, 1, 2);
        moveEntity(getPlayer(), 14, 1);
        ButtonBackRoom(stage);
    }

    private void generation(String pathToCsv) throws FileNotFoundException {
        this.setOnKeyPressed(null);
        for (ItemEntity item : itemGenerals) {
            removeItemEntity(item);
        }
        getChildren().clear(); // Clear the existing map
        setPathToCsv(pathToCsv);
        setMap(new ArrayList<>());
        genMap(getMap());
        showMap(getMap(), this);
    }

    public void backRoom(String pathToCsv, Stage stage) throws FileNotFoundException {
        this.setOnKeyPressed(null);
        itemEntities.clear();
        entities.clear();
        //System.out.println(getPlayer().getMoney());
        for (ItemEntity item : itemGenerals) {
            removeItemEntity(item);
        }
        this.setVgap(-1);
        this.setHgap(-1);
        getChildren().clear(); // Clear the existing map
        setPathToCsv(pathToCsv);
        setMap(new ArrayList<>());
        genMap(getMap());
        showMap(getMap(), this);
        moveEntity(getPlayer(), 14, 1);
        setPotionSeller(new PotionSeller("PotionSeller", 35, 35, this, getPlayer(), entities));
        PotionSeller potionSeller2 = new PotionSeller("PotionSeller2", 35, 35, this, getPlayer(), entities);
        getPotionSeller().getInventory().addItemPotion(new ItemGeneral("POTION_HEAL", this, getPlayer(), entities, stage), 1);
        setQuest(new PnjQuest("pnjQuest1", 30, 50, this, getPlayer(), 8,1, entities, stage));
        setQuest(new PnjQuest("pnjQuest2", 30, 50, this, getPlayer(), 8,2, entities, stage));
        placeEntity(getPotionSeller(), 7, 1);
        placeEntity(potionSeller2, 7, 2);
        entities.add(getPotionSeller());
        //moveEntity(getPlayer(), 14, 7);
        //getPlayer().getInventory().addItemPotion(new ItemGeneral("KILL", this, getPlayer()), 1);
        ItemGeneral item1 = new ItemGeneral("ITEM1", this, getPlayer(), entities, stage);
        ItemGeneral item2 = new ItemGeneral("ITEM2", this, getPlayer(), entities, stage);
        itemEntities.add(item1);
        itemEntities.add(item2);
        placeItemEntity(item1, 14, 3);
        placeItemEntity(item2, 14, 4);
        ButtonLevel1(stage);
        ButtonLevel2(stage);
        ButtonLevel3(stage);
    }

    private void ButtonLevel3(Stage stage) {
        Button changeMapButton = new Button("3");
        changeMapButton.setOnAction(event -> {
            try {
                //player.getInventory().clear();
                itemEntities.clear();
                entities.clear();
                this.level3(Levels[2], stage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        GridPane.setColumnIndex(changeMapButton, 10);
        GridPane.setRowIndex(changeMapButton, 0);
        this.getChildren().add(changeMapButton);
    }

    private void ButtonLevel2(Stage stage) {
        Button changeMapButton = new Button("2");
        changeMapButton.setOnAction(event -> {
            try {
                //player.getInventory().clear();
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
                //player.getInventory().clear();
                itemEntities.clear();
                entities.clear();
                this.backRoom(Levels[5], stage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        GridPane.setColumnIndex(changeMapButton, 0);
        GridPane.setRowIndex(changeMapButton, 0);
        this.getChildren().add(changeMapButton);
    }

    public PotionSeller getPotionSeller() {
        return potionSeller;
    }

    public void setPotionSeller(PotionSeller potionSeller) {
        this.potionSeller = potionSeller;
    }

    public MonsterEnum getMonsterEnum() {
        return monsterEnum;
    }

    public void setMonsterEnum(MonsterEnum monsterEnum) {
        this.monsterEnum = monsterEnum;
    }

    public PnjQuest getQuest() {
        return quest;
    }

    public void setQuest(PnjQuest quest) {
        this.quest = quest;
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

    public List<ItemGeneral> getItemPotions() {
        return itemGenerals;
    }

    public void setItemPotions(List<ItemGeneral> itemGenerals) {
        this.itemGenerals = itemGenerals;
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
}