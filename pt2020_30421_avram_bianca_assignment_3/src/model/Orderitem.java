package model;

/**
 * This class represents an order that is placed at a moment of time
 * @author Bianca-Veronica Avram
 * @version 1.0
 */
public class Orderitem {
    private int id;
    private int productid;
    private int quantity;
    private int orderid;

    public Orderitem(int id, int productid, int quantity, int orderid){
        this.id = id;
        this.productid = productid;
        this.quantity = quantity;
        this.orderid = orderid;
    }

    /**
     * This method gets the id of the order
     * @return The id of the order
     */
    public int getId() {
        return id;
    }

    /**
     * This method gets the id of the product that is being ordered
     * @return The id of the product that is being ordered
     */
    public int getProductid() {
        return productid;
    }

    /**
     * This method gets the quantity of product that is being ordered
     * @return The quantity of product that is being ordered
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * This method gets the id of the order that incorporates this order item
     * @return The id of the order
     */
    public int getOrderid() {
        return orderid;
    }

    /**
     * This method sets the id of the order
     * @param id The id that is set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method sets the quantity of the product that is being ordered
     * @param quantity Quantity set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * This method sets the id of the order that incorporates this order item
     * @param orderid
     */
    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    /**
     * This method sets the id of the product that is being ordered
     * @param productid
     */
    public void setProductid(int productid) {
        this.productid = productid;
    }
}
