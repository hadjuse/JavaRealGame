package item;

public class ItemEntity {
    private double valueMoney;
    public ItemEntity(double value){
        setValueMoney(value);
    }

    public double getValueMoney() {
        return valueMoney;
    }

    public void setValueMoney(double valueMoney) {
        this.valueMoney = valueMoney;
    }
}
