package monster;

import entity.Entity;
import inventory.Inventory;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import obs.Observable;
import obs.Observer;
import player.PlayerObservable;
import world.TileMap;

import java.io.FileNotFoundException;

public abstract class MonsterEntity extends Entity{
    private MonsterEnum monsterEnum;
    public MonsterEntity(MonsterEnum monsterEnum, TileMap map, int i, int j) throws FileNotFoundException {
        super(monsterEnum.name(), monsterEnum.getWidthFactor()*25, monsterEnum.getHeightFactor()*25, map);
        setLife(monsterEnum.getBaseLife());
        setStrength(monsterEnum.getBaseDamage());
        setMoney(10);
        setMonsterEnum(monsterEnum);
        setGridI(i);
        setGridJ(j);
        setMap(map);
        setBoxEntity(renderMonster());
        getMap().moveEntity(this, getGridI(), getGridJ());
        setInventory(new Inventory(5));
    }

    public StackPane renderMonster(){
        StackPane stackPane = new StackPane();
        setBounds(new Rectangle(getWidth(), getHeight()));
        getBounds().setFill(getMonsterEnum().getMonsterColor());
        getBounds().setOpacity(0.5);
        stackPane.getChildren().add(getBounds());
        return stackPane;
    }

    public MonsterEnum getMonsterEnum() {
        return monsterEnum;
    }

    public void setMonsterEnum(MonsterEnum monsterEnum) {
        this.monsterEnum = monsterEnum;
    }
}
