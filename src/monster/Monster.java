package monster;

import entity.Entity;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import player.Player;
import world.TileMap;

import java.io.FileNotFoundException;
import java.util.List;

public class Monster extends Entity {
    private Rectangle boundsMonster;

    public Rectangle getBoundsMonster() {
        return boundsMonster;
    }
    public void setBoundsMonster(Rectangle boundsMonster) {
        this.boundsMonster = boundsMonster;
    }

    public Monster(String name, double width, double height, Player player, TileMap map) throws FileNotFoundException {
        super(name, width, height, map);
        setLife(120);
        setSpeed(2);
        setMoney(5);
        setStrength(30);
        setBoxEntity(boxMonster());
        setDamage(20 * (1 + getStrength() / 100));
        getBoxEntity().setOnMouseClicked( mouseEvent -> {
            if (getBoxEntity().contains(mouseEvent.getX(), mouseEvent.getY())) {
                player.basicAttack(this, map);
            }
        });
    }

    public StackPane boxMonster() {
        StackPane stackPane = new StackPane();
        setBoundsMonster(new Rectangle(getWidth(), getHeight()));
        getBoundsMonster().setFill(Color.RED);
        getBoundsMonster().setOpacity(0.5);
        stackPane.getChildren().add(boundsMonster);
        return stackPane;
    }


}