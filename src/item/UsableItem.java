package item;

import java.io.FileNotFoundException;

public class UsableItem extends ItemEntity {
    public UsableItem(String name, int value) throws FileNotFoundException {
        super(value);
    }
}
