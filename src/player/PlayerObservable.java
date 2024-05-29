package player;

import entity.Entity;
import inventory.Inventory;
import item.ItemGeneral;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import monster.ListMonster.Monster1;
import monster.MonsterEntity;
import obs.Observable;
import obs.Observer;
import world.TileMap;

import java.io.FileNotFoundException;

public class PlayerObservable extends Entity implements Observer{
    private boolean oneShot;
    public PlayerObservable(String name, double width, double height, TileMap map, int i, int j) throws FileNotFoundException {
        super(name, width, height, map);
        setMap(map);
        initInfoPlayer(name);
        setGridI(i);
        setGridJ(j);
        setBoxEntity(boxEntity());
        getBounds().setFocusTraversable(true);
        getMap().placeEntity(this, getGridI(), getGridJ());
        //getInventory().getItemPotionList().add(new ItemGeneral("POTION_HEAL", map, this, getMap()));
        map.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            checkCollision();
            switch (keyEvent.getCode()) {
                case Z -> {
                    if (getGridI() - 1 > 0){
                        setGridI(getGridI() - 1);
                        getMap().moveEntity(this, getGridI(), getGridJ());
                    }
                }
                case Q -> {
                    if (getGridJ() - 1 > 0){
                        setGridJ(getGridJ() - 1);
                        getMap().moveEntity(this, getGridI(), getGridJ());
                    }
                }
                case D -> {
                    if (getGridJ() + 1 < 15){
                        setGridJ(getGridJ() + 1);
                        getMap().moveEntity(this, getGridI(), getGridJ());
                    }
                }
                case S -> {
                    if (getGridI() + 1 < 15) {
                        setGridI(getGridI() + 1);
                        getMap().moveEntity(this, getGridI(), getGridJ());
                    }
                }
                case P -> {
                    displayInventory();
                }
            }
        });

    }

    public boolean isOneShot() {
        return oneShot;
    }

    public void setOneShot(boolean oneShot) {
        this.oneShot = oneShot;
    }
    private void initInfoPlayer(String name) throws FileNotFoundException {
        setName(name);
        setLife(100);
        setSpeed(1.2);
        setStrength(20);
        //setDamage(20 * (1 + getStrength() / 100));
        setBoxEntity(boxEntity());
        setXSpawn(0);
        setYSpawn(0);
        setInventory(new Inventory(5));
        setMoney(0);
        setCollision(false);
        setMoney(50);
    }
    public StackPane boxEntity() {
        StackPane stackPane = new StackPane();
        Image image = new Image("images/player/player.png");
        setBounds(new Rectangle(getWidth(), getHeight()));
        getBounds().setFill(new ImagePattern(image));
        getBounds().setOpacity(1);
        stackPane.getChildren().addAll(getBounds());
        return stackPane;
    }
    public void checkCollision() {
        getObservers().forEach(observer -> {
            if (observer instanceof MonsterEntity monsterEntity){
                Boolean collision = monsterEntity.getGridI() == getGridI() && monsterEntity.getGridJ() == getGridJ();
                if (collision){
                    System.out.println("Collision with monster");
                    monsterEntity.setCollision(true);
                    notifyObserver();
                }
            }
        });
    }

    @Override
    public void update(Observable observable) {
    }
}
