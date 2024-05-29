package itemObservable;

import entity.Entity;
import obs.Observable;
import obs.Observer;

import java.util.List;

public abstract class ItemObservable implements Observable{
    private List<Observer> observers;
    private int quantity;
    private double valueMoney;
    private double life;
    private double strength;
    private double money;
    private final String directory = String.format("/images/potion/", System.getProperty("user.dir"));
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
}
