package model;

/**
 * This class represents a product
 * @author Bianca-Veronica Avram
 * @version 1.0
 */
public class Product {
    private int id;
    private String name;
    private int quantity;
    private float price;

    public Product(int id, String name, int quantity, float price){
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * This method gets the id of the product
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * This method gets the name of the product
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * This method gets the quantity of the product
     * @return
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * This method gets the price of the product
     * @return
     */
    public float getPrice() {
        return price;
    }

    /**
     * This method sets the id of the product
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method sets the name of the product
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method sets the quantity of the product
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * This method sets the price of the product
     * @param price
     */
    public void setPrice(float price) {
        this.price = price;
    }
}
