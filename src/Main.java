import item.ItemEntity;
import item.ItemPotion;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import monster.Monster;
import player.Player;
import world.TileMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {
    List<ItemEntity> itemEntities;
    private final String[] Levels = new String[]{
            String.format("%s/src/world/Level/level1.csv", System.getProperty("user.dir")),
            String.format("%s/src/world/Level/level2.csv", System.getProperty("user.dir")),
            String.format("%s/src/world/Level/level3.csv", System.getProperty("user.dir")),
    };
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Cube War");
        TileMap level1 = new TileMap(Levels[0], stage);
        TileMap level2 = new TileMap(Levels[1], stage);
        //TileMap level3 = new TileMap(Levels[2], stage);
        Scene scene = new Scene(level1, 793, 780);
        stage.sizeToScene();
        stage.setScene(scene);
        stage.centerOnScreen();
        //stage.setFullScreen(true);
        stage.setX(1280 / 2);
        stage.setY(0);

        stage.show();

    }
}