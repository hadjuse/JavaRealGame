package pnj;

import entity.Entity;
import player.Player;
import world.TileMap;

import java.io.FileNotFoundException;

public class PnjQuest extends Entity {
    private TileMap map;
    private Player player;

    // TODO QUEST PNJ that allow interaction.
    public PnjQuest(String name, double width, double height, TileMap map, Player player) throws FileNotFoundException {
        super(name, width, height, map);
        setMap(map);
        setPlayer(player);
        setLife(120);
        setMoney(400);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public TileMap getMap() {
        return map;
    }

    public void setMap(TileMap map) {
        this.map = map;
    }
}
