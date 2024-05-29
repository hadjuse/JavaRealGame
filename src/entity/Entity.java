package entity;

import inventory.Inventory;
import item.ItemEntity;
import item.ItemGeneral;
import item.QuestItem;
import item.UsableItem;
import itemObservable.ItemObservable;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import obs.Observable;
import obs.Observer;
import world.TileMap;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements Observable {
    private double life;
    private double strength;
    private double money;
    private double speed;
    private double damage;
    private StackPane boxEntity;
    private Inventory inventory;
    private double width;
    private double height;
    private String name;
    private Rectangle bounds;
    private QuestItem questItem;
    private boolean collision;
    private Alert dialog;
    private boolean open;
    private List<Observer> observers;
    private boolean attack;
    private boolean takingItem;
    private double XSpawn;
    private double YSpawn;
    private int gridI;
    private int gridJ;
    private TileMap map;
    public Entity(String name, double width, double height, TileMap map) throws FileNotFoundException {
        setMoney(0);
        setWidth(width);
        setHeight(height);
        //setBoxEntity(getBoxEntity());
        setName(name);
        setObservers(new ArrayList<>());
        setMap(map);
    }

    public boolean isDead() {
        return getLife() <= 0;
    }
    public double getLife() {
        return life;
    }

    public void setLife(double life) {
        this.life = life;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public StackPane getBoxEntity() {
        return boxEntity;
    }

    public void setBoxEntity(StackPane boxEntity) {
        this.boxEntity = boxEntity;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void addLife(double life) {
        setLife(getLife() + life);
    }

    public void addStrength(double strength) {
        setStrength(getStrength() + strength);
    }

    public void addSpeed(double speed) {
        setSpeed(getSpeed() + speed);
    }

    public void addDamage(double damage) {
        setDamage(getDamage() + damage);
    }

    public void addMoney(double money, Entity entity) {
        //System.out.printf("%s Earn %f%n", getName(), money);
        entity.setMoney(getMoney() + money);
    }

    public void loseLife(double life) {
        setLife(getLife() - life);
    }

    public void loseStrength(double strength) {
        setStrength(getStrength() - strength);
    }

    public void loseSpeed(double speed) {
        setSpeed(getSpeed() - speed);
    }

    public void loseDamage(double damage) {
        setDamage(getDamage() - damage);
    }

    public void loseMoney(double money) {
        System.out.println("You Lose " + money);
        setMoney(getMoney() - money);
    }


    public void giveItem(Entity entity, ItemEntity item) {
        int quantityInventory = entity.getInventory().getQuantity();
        if (item instanceof ItemGeneral potion) {
            entity.getInventory().addItemPotion(potion, quantityInventory);
            getInventory().removeItemPotion(potion);
        } else if (item instanceof UsableItem usable) {
            entity.getInventory().addUsableItem(usable, quantityInventory);
            getInventory().removeUsableItem(usable);
        } else if (item instanceof QuestItem questItem) {
            entity.getInventory().addQuestItem(questItem, quantityInventory);
            getInventory().removeConsumableItem(questItem);
        }
    }

    public void takeItem(ItemEntity item) {
        if (item instanceof ItemGeneral potion) {
            getInventory().addItemPotion(potion, getInventory().getQuantity());
        } else if (item instanceof UsableItem usable) {
            getInventory().addUsableItem(usable, getInventory().getQuantity());
        } else if (item instanceof QuestItem questItem) {
            getInventory().addQuestItem(questItem, getInventory().getQuantity());
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void UsePotion() {
        ItemGeneral itemGeneral = getInventory().getItemPotion(0);
        if (itemGeneral != null) {
            itemGeneral.applyEffectPotion(this);
            getInventory().removeItemPotion(itemGeneral);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public QuestItem getQuestItem() {
        return questItem;
    }

    public void setQuestItem(QuestItem questItem) {
        this.questItem = questItem;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public void displayDialog(String dialog) {
        setDialog(new Alert(Alert.AlertType.INFORMATION));
        getDialog().setHeaderText(String.format("%s dialog", getName()));
        getDialog().setContentText(dialog);
        getDialog().showAndWait();
    }

    public Alert getDialog() {
        return dialog;
    }

    public void setDialog(Alert dialog) {
        this.dialog = dialog;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public List<Observer> getObservers() {
        return observers;
    }

    public void setObservers(List<Observer> observers) {
        this.observers = observers;
    }
    @Override
    public void addObserver(Observer obs) {
        getObservers().add(obs);
    }

    @Override
    public void removeObserver(Observer obs) {
        getObservers().remove(obs);
    }

    @Override
    public void notifyObserver() {
        for (Observer obs : getObservers()) {
            obs.update(this);
        }
    }


    public boolean isTakingItem() {
        return takingItem;
    }

    public void setTakingItem(boolean takingItem) {
        this.takingItem = takingItem;
    }

    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public double getXSpawn() {
        return XSpawn;
    }

    public void setXSpawn(double XSpawn) {
        this.XSpawn = XSpawn;
    }

    public double getYSpawn() {
        return YSpawn;
    }

    public void setYSpawn(double YSpawn) {
        this.YSpawn = YSpawn;
    }
    public void displayInventory() {
        getInventory().getItemPotionList().forEach(item -> System.out.println(item.getName()));
    }

    public int getGridI() {
        return gridI;
    }

    public void setGridI(int gridI) {
        this.gridI = gridI;
    }

    public int getGridJ() {
        return gridJ;
    }

    public void setGridJ(int gridJ) {
        this.gridJ = gridJ;
    }

    public TileMap getMap() {
        return map;
    }

    public void setMap(TileMap map) {
        this.map = map;
    }
    public void usePotion(ItemObservable itemObservable){
    }
}