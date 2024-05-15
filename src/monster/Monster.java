package monster;

import entity.Entity;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import player.Player;
import world.TileMap;

import java.io.FileNotFoundException;

public class Monster extends Entity {
    public Monster(String name, double width, double height, Player player, TileMap map) throws FileNotFoundException {
        super(name, width, height, map);
        setLife(120);
        setSpeed(2);
        setMoney(5);
        setStrength(30);
        setDamage(20 * (1 + getStrength() / 100));
        setBoxEntity(boxMonster());
        getBoxEntity().setFocusTraversable(true);
        getBoxEntity().requestFocus();
        getBoxEntity().setOnMouseClicked(e -> {
            player.basicAttack(this, map);
        });
    }

    public StackPane boxMonster() {
        StackPane stackPane = new StackPane();
        Rectangle rectangle = new Rectangle(getWidth(), getHeight());
        rectangle.setFill(Color.RED);
        rectangle.setOpacity(0.5);
        stackPane.getChildren().add(rectangle);
        return stackPane;
    }


}