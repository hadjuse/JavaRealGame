import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import world.TileMap;

public class Main extends Application {
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
        TileMap tileMap = new TileMap(Levels[0], stage);
        Scene scene = new Scene(tileMap, 793, 780);
        stage.sizeToScene();
        stage.setScene(scene);
        stage.centerOnScreen();
        //stage.setFullScreen(true);
        stage.setX(1280 / 2);
        stage.setY(0);
        stage.show();

    }
}