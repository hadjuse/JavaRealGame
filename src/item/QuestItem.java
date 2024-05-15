package item;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import player.Player;
import world.TileMap;

import java.io.FileNotFoundException;

public class QuestItem extends ItemEntity {
    private Rectangle hitBox;
    private String name;
    public QuestItem(String name,Player player, TileMap map) throws FileNotFoundException {
        setName(name);
        setItemStackPane(renderItem());
        map.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.E) {
                Shape intersect = Shape.intersect(getHitBox(), player.getHitBox());
                if (intersect.getBoundsInLocal().getWidth() > 0 && intersect.getBoundsInLocal().getHeight() > 0) {
                    System.out.println("%s take the %s".formatted(player.getName(), getName()));
                    player.getInventory().addQuestItem(this);
                    map.removeItemEntity(this);
                }
            }
        });
    }

    @Override
    public StackPane renderItem() throws FileNotFoundException {
        StackPane stackPane = new StackPane();
        setHitBox(new Rectangle(25, 25));

        getHitBox().setFill(Color.YELLOW);
        stackPane.getChildren().add(getHitBox());
        return stackPane;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
