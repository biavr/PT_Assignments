package model;

/**
 * This class represents an order that corresponds to a client
 * @author Bianca-Veronica Avram
 * @version 1.0
 */
public class Order {
    private int id;
    private int clientid;
    private float total;

    public Order(int id, int clientid, float total){
        this.id = id;
        this.clientid = clientid;
        this.total = total;
    }

    /**
     * This method gets the id of the order
     * @return The id of the order
     */
    public int getId() {
        return id;
    }

    /**
     * This method gets the id of the client
     * @return The id of the client
     */
    public int getClientid() {
        return clientid;
    }

    /**
     * This method gets the total cost of the order
     * @return The total cost of the order
     */
    public float getTotal() {
        return total;
    }

    /**
     * This method sets the id of the order
     * @param id The id that is set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method sets the id of the client
     * @param clientid The client id that is set
     */
    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    /**
     * This method sets the total cost of the order
     * @param total The total cost that is set
     */
    public void setTotal(float total) {
        this.total = total;
    }
}
