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
        setMoney(50);
        setStrength(30);
        setBoxEntity(boxMonster());
        setDamage(20 * (1 + getStrength() / 100));

        getBoxEntity().setOnMouseClicked(mouseEvent -> {
            if (getBoxEntity().contains(mouseEvent.getX(), mouseEvent.getY())) {
                player.basicAttack(this, map);
            }
        });
    }

    public StackPane boxMonster() {
        StackPane stackPane = new StackPane();
        setBounds(new Rectangle(getWidth(), getHeight()));
        getBounds().setFill(Color.RED);
        getBounds().setOpacity(0.5);
        stackPane.getChildren().add(getBounds());
        return stackPane;
    }

}