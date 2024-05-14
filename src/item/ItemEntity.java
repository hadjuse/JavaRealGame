package item;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ItemEntity {
    private double valueMoney;
    public double getValueMoney() {
        return valueMoney;
    }
    public void setValueMoney(double valueMoney) {
        this.valueMoney = valueMoney;
    }
}
