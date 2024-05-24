package pnj;

import entity.Entity;
import player.Player;
import world.TileMap;

import java.io.FileNotFoundException;

public class PnjQuest extends Entity {
    // TODO QUEST PNJ that allow interaction.
    public PnjQuest(String name, double width, double height, TileMap map, Player player) throws FileNotFoundException {
        super(name, width, height, map);
        setLife(120);
        setMoney(400);
    }

}
