package item;

import java.io.FileNotFoundException;

public class ConsumableItem extends ItemEntity {
    public ConsumableItem(String name, int value) throws FileNotFoundException {
        super(value);
    }
}
