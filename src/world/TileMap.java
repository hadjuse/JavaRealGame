package world;

import entity.Entity;
import item.ItemEntity;
import item.ItemPotion;
import item.QuestItem;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
    List<ItemEntity> itemEntities;
    private List<List<String>> map;
    private String pathToCsv;
    private Player player;
    private Monster monster;
    private ItemEntity itemPotion;
    private Rectangle hitBoxWall;
    public Rectangle getHitBoxWall() {
        return hitBoxWall;
    }
    public void setHitBoxWall(Rectangle hitBoxWall) {
        this.hitBoxWall = hitBoxWall;
    }

    private Timeline gameLoop1;
    private List<Shape> bounds = new ArrayList<Shape>();
    public TileMap(String pathToCsv, Stage stage) throws FileNotFoundException {
        setPathToCsv(pathToCsv);
        setMap(new ArrayList<>());
        this.setVgap(-1);
        this.setHgap(-1);
        genMap(getMap());
        showMap(getMap());

        player = new Player("Hadjuse", stage, this,  bounds);
        ItemEntity potionHeal = new ItemPotion("POTION_HEAL",player, this, 1);
        itemPotion = new ItemPotion("POTION_STRENGTH",player, this, 1);
        QuestItem newQuestItem = new QuestItem("GG", player, this);
        monster = new Monster("Monster", 20, 20, player, this);
        monster.getInventory().addItemPotion((ItemPotion) potionHeal);
        bounds.add(monster.getBoundsMonster());
        bounds.add(hitBoxWall);
        placeItemEntity(itemPotion, 3, 10);
        placeEntity(monster, 9, 5);
        placeItemEntity(newQuestItem, 10, 10);
        placeEntity(player, 2, 3);
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

    public void showMap(List<List<String>> map) {
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
                        placeWall(Wall(), i, j);
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

    public StackPane Wall() {
        StackPane wall = new StackPane();
        ImageView tile = new ImageView("images/tile/wall_mid.png");
        tile.setFitWidth(50);
        tile.setFitHeight(50);
        hitBoxWall = new Rectangle(tile.getFitHeight(), tile.getFitWidth());
        hitBoxWall.setFill(Color.TRANSPARENT);
        wall.getChildren().add(tile);
        wall.getChildren().add(hitBoxWall);
        return wall;
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
}