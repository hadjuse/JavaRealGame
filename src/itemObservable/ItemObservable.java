package itemObservable;

import entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import obs.Observable;
import obs.Observer;
import world.TileMap;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemObservable implements Observable{
    private List<Observer> observers;
    private int quantity;
    private double valueMoney;
    private double life;
    private double strength;
    private double money;
    private StackPane itemStackPane;
    private Rectangle bounds;
    private final String directory = String.format("/images/potion/");
    private boolean collision;
    private int i;
    private int j;
    private String[] skins = new String[]{
            String.format("%sflask_big_blue.png", directory),
            String.format("%sflask_big_green.png", directory),
            String.format("%sflask_big_red.png", directory),
            String.format("%sflask_big_yellow.png", directory),
            String.format("%sflask_blue.png", directory),
            String.format("%sflask_green.png", directory),
            String.format("%sflask_red.png", directory),
            String.format("%sflask_yellow.png", directory),
    };
    private String[] Levels = new String[]{
            "level1.csv",
            "level2.csv",
            "level3.csv",
            "backroom.csv"
    };
    public ItemObservable(TileMap map, int i, int j) {
        setBounds(new Rectangle(50, 50));
        setItemStackPane(new StackPane());
        setObservers(new ArrayList<>());
        setQuantity(1);
        setCollision(false);
        setI(i);
        setJ(j);
        //map.placeItemEntity(this, getI(), getJ());
    }

    public StackPane renderSkin(int i) {
        StackPane stackPane = new StackPane();
        Image image = new Image(skins[i]);
        setBounds(new Rectangle(50, 50));
        getBounds().setFill(new ImagePattern(image));
        stackPane.getChildren().add(getBounds());
        return stackPane;
    }
    public List<Observer> getObservers() {
        return observers;
    }

    public void setObservers(List<Observer> observers) {
        this.observers = observers;
    }

    @Override
    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObserver() {
        for (Observer obs : observers) {
            obs.update(this);
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getValueMoney() {
        return valueMoney;
    }

    public void setValueMoney(double valueMoney) {
        this.valueMoney = valueMoney;
    }

    public String[] getSkins() {
        return skins;
    }

    public void setSkins(String[] skins) {
        this.skins = skins;
    }

    public String[] getLevels() {
        return Levels;
    }

    public void setLevels(String[] levels) {
        Levels = levels;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public StackPane getItemStackPane() {
        return itemStackPane;
    }

    public void setItemStackPane(StackPane itemStackPane) {
        this.itemStackPane = itemStackPane;
    }

    public double getLife() {
        return life;
    }

    public void setLife(double life) {
        this.life = life;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }
    public abstract void applyEffect();

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
}
