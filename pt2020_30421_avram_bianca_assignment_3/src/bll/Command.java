package bll;

/**
 * This class contains information about a command that is taken from the user, including the name of the command and the parameters needed for proper execution
 * @author Bianca-Veronica Avram
 * @version 1.0
 */
public class Command {
    private String commandName;
    private String clientName;
    private String productName;
    private String clientAddress;
    private int productQuantity;
    private float productPrice;

    public Command(String commandName,String clientName,String productName,String clientAddress,int productQuantity,float productPrice){
        this.commandName = commandName;
        this.clientName = clientName;
        this.productName = productName;
        this.clientAddress = clientAddress;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
    }

    /**
     * This method gets the product price
     * @return
     */
    public float getProductPrice() {
        return productPrice;
    }
    /**
     * This method gets the product quantity
     * @return
     */
    public int getProductQuantity() {
        return productQuantity;
    }
    /**
     * This method gets the client address
     * @return
     */
    public String getClientAddress() {
        return clientAddress;
    }
    /**
     * This method gets the client name
     * @return
     */
    public String getClientName() {
        return clientName;
    }
    /**
     * This method gets the command name
     * @return
     */
    public String getCommandName() {
        return commandName;
    }
    /**
     * This method gets the product name
     * @return
     */
    public String getProductName() {
        return productName;
    }

    /**
     * This method splits the string given as parameter into parts to build the corresponding Command object
     * @param cmd The command as a String
     * @return The command as Command object
     */
    public static Command buildCommand(String cmd){
        Command c = null;
        String parts[] = cmd.split("[:,]");
        parts[0] = parts[0].toLowerCase();
        switch (parts[0]){
            case "report client":
            case "report order":
            case "report product":
                c = new Command(parts[0],null,null,null,0,0);
                break;
            case "insert client":
                c = new Command(parts[0],parts[1].trim(),null,parts[2].trim(),0,0);
                break;
            case "insert product":
                c = new Command(parts[0],null,parts[1].trim(),null,Integer.parseInt(parts[2].trim()),Float.parseFloat(parts[3].trim()));
                break;
            case "delete client":
                c = new Command(parts[0],parts[1].trim(),null,null,0,0);
                break;
            case "delete product":
                c = new Command(parts[0],null,parts[1].trim(),null,0,0);
                break;
            case "order":
                c = new Command(parts[0],parts[1].trim(),parts[2].trim(),null,Integer.parseInt(parts[3].trim()),0);
                break;
        }
        return c;
    }
}
