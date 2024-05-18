package item;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import world.TileMap;

import java.io.FileNotFoundException;

public class QuestItem extends ItemEntity {
    private String name;
    private Rectangle hitBox;

    public QuestItem(String name, TileMap map) throws FileNotFoundException {
        setName(name);
        setItemStackPane(renderItem());
    }

    @Override
    public StackPane renderItem() throws FileNotFoundException {
        StackPane stackPane = new StackPane();
        setHitBox(new Rectangle(25, 25));
        getHitBox().setFill(Color.YELLOW);
        stackPane.getChildren().add(getHitBox());
        return stackPane;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    @Override
    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }
}
