package pnj;

import entity.Entity;
import world.TileMap;

import java.io.FileNotFoundException;

public class pnjObserver1 extends Entity {
    public pnjObserver1(String name, double width, double height, TileMap map) throws FileNotFoundException {
        super(name, width, height, map);
    }
}
