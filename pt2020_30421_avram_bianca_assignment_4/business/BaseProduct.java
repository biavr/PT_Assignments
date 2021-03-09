package business;

import java.io.Serializable;

public class BaseProduct implements MenuItem, Serializable {
    private String name;
    private float price;

    public BaseProduct(String name, float price){
        this.name = name;
        this.price = price;
    }

    public float getPrice() {
        return price;
    }
    @Override
    public String getName() {
        return name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float computePrice() {
        return price;
    }

    @Override
    public String toString(){
        return name+": "+Float.toString(price);
    }
}
