package model;

/**
 * This class represents a client as an object that has an id, a name and an address.
 * @author Bianca-Veronica Avram
 * @version 1.0
 */
public class Client {
    private int id;
    private String name;
    private String address;

    public Client(int id, String name, String address){
        this.id = id;
        this.name = name;
        this.address = address;
    }

    /**
     * This method obtains the id of the client
     * @return the id of the client
     */
    public int getId() {
        return id;
    }

    /**
     * This method gets the address of the client
     * @return the address of the client
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method gets the name of the client
     * @return the name of the client
     */
    public String getName() {
        return name;
    }

    /**
     * This method sets the address of the client
     * @param address The address that is set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This method sets the id of the client
     * @param id The id that is set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method sets the name of the client
     * @param name The name that is set
     */
    public void setName(String name) {
        this.name = name;
    }
}
