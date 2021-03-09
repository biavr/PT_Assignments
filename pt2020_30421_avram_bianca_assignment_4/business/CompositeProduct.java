package business;

import java.io.Serializable;
import java.util.ArrayList;

public class CompositeProduct implements MenuItem, Serializable {
    private String name;
    private ArrayList<MenuItem> products;

    public CompositeProduct(String name){
        this.name = name;
        products = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<MenuItem> getProducts() {
        return products;
    }

    public void addProduct(MenuItem m){
        products.add(m);
    }

    public float computePrice() {
        float price = 0;
        for (MenuItem p:products) {
            price+=p.computePrice();
        }
        return price;
    }

    @Override
    public String toString(){
        return name+": "+ computePrice();
    }
}
