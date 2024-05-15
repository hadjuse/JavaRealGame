package item;

import javafx.scene.layout.StackPane;

import java.io.FileNotFoundException;

public class UsableItem extends ItemEntity {
    public UsableItem(String name) throws FileNotFoundException {
    }

    @Override
    public StackPane renderItem() throws FileNotFoundException {
        return null;
    }
}
