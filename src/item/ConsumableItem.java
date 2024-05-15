package item;

import javafx.scene.layout.StackPane;

import java.io.FileNotFoundException;

public class ConsumableItem extends ItemEntity {
    public ConsumableItem(String name) throws FileNotFoundException {
    }

    @Override
    public StackPane renderItem() throws FileNotFoundException {
        return null;
    }
}
