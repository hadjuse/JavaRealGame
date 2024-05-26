package pnj;

import entity.Entity;
import inventory.Inventory;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import player.Player;
import world.TileMap;

import java.io.FileNotFoundException;
import java.util.List;

public class PnjQuest extends Entity {
    private TileMap map;
    private Player player;
    private int i;
    private int j;
    private List<Entity> entities;
    private Stage stage;
    // TODO QUEST PNJ that allow interaction.
    public PnjQuest(String name, double width, double height, TileMap map, Player player, int i, int j, List<Entity> entities, Stage stage) throws FileNotFoundException {
        super(name, width, height, map);
        setStage(stage);
        setEntities(entities);
        setMap(map);
        setPlayer(player);
        setLife(120);
        setMoney(400);
        setI(i);
        setJ(j);
        setInventory(new Inventory(5));
        setBoxEntity(renderPNJ());
        getMap().moveEntity(this, getI(), getJ());
        getEntities().add(this);
    }
    public StackPane renderPNJ() {
        StackPane stackPane = new StackPane();
        Image image = new Image("images/pnj/wizzard_m_run_anim_f0.png");
        setBounds(new Rectangle(getWidth(), getHeight()));
        getBounds().setFill(new ImagePattern(image));
        getBounds().setOpacity(1);
        stackPane.getChildren().addAll(getBounds());
        return stackPane;
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

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
