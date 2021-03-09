package start;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import bll.*;
import presentation.*;
import model.*;

/**
 * This is the main class of the application and it contains only the main method that is run
 * @author Bianca-Veronica Avram
 * @version 1.0
 */
public class Main {
    protected static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    public static int clientid = 0; //this will be the same with the id of the order corresponding to a client
    public static int productid = 0;
    public static int orderitemid = 0;
    public static int clientReportNumber = 0;
    public static int productReportNumber = 0;
    public static int orderReportNumber = 0;
    public static int billNumber = 0;

    /**
     * This method is used to run the application
     * @param args The name of the input file
     */
    public static void main(String args[]){
        try{
            View view = new View(args[1]);
            //View view = new View("D:\\Bee IntelliJ\\PT2020_30421_Bianca_Avram_Assignment_3\\src\\main\\resources\\in.txt");
            Controller controller = new Controller();
            ArrayList<Command> list = controller.buildCommandList(view.buildCommandList());
            for (Command cmd:list) {
                if(cmd != null){
                    switch (cmd.getCommandName()){
                        case "report client":
                            clientReportNumber++;
                            view.reportClient(controller,clientReportNumber);
                            break;
                        case "report product":
                            productReportNumber++;
                            view.reportProduct(controller,productReportNumber);
                            break;
                        case "report order":
                            orderReportNumber++;
                            view.reportOrder(controller,orderReportNumber);
                            break;
                        case "insert client":
                            clientid++;
                            Client client = new Client(clientid,cmd.getClientName(),cmd.getClientAddress());
                            ClientBLL.insertClient(client);
                            /*if(ClientBLL.insertClient(client) != 1){
                                //System.out.println("Client " + cmd.getClientName() + " could not be inserted");
                            }*/
                            break;
                        case "insert product":
                            Product exists = ProductBLL.findProductByName(cmd.getProductName());
                            if(exists != null){
                                ProductBLL.updateProductPrice(exists.getId(),cmd.getProductPrice());
                                //System.out.println("Product " + cmd.getProductName() + " already exists. Price updated successfully");
                            }
                            else{
                                productid++;
                                Product product = new Product(productid,cmd.getProductName(),cmd.getProductQuantity(),cmd.getProductPrice());
                                ProductBLL.insertProduct(product);
                                /*if(ProductBLL.insertProduct(product) != 1){
                                    System.out.println("Product " + cmd.getProductName() + " could not be inserted");
                                }*/
                            }
                            break;
                        case"delete client":
                            ClientBLL.deleteClient(ClientBLL.findClientByName(cmd.getClientName()).getId());
                            /*if(ClientBLL.deleteClient(ClientBLL.findClientByName(cmd.getClientName()).getId()) != 1){
                                System.out.println("Client " + cmd.getClientName() + " could not be deleted");
                            }
                            else{
                                System.out.println("Client " + cmd.getClientName() + " does not exist");
                            }*/
                            break;
                        case "delete product":
                            ProductBLL.deleteProduct(cmd.getProductName());
                            /*if(ProductBLL.deleteProduct(cmd.getProductName()) != 1){
                                System.out.println("Product " + cmd.getProductName() + " could not be deleted");
                            }
                            else{
                                System.out.println("Product " + cmd.getProductName() + " does not exist");
                            }*/
                            break;
                        case "order":
                            Product p = ProductBLL.findProductByName(cmd.getProductName());
                            int productid = p.getId();
                            int clientid = ClientBLL.findClientByName(cmd.getClientName()).getId();
                            if(OrderBLL.findOrderById(clientid) == null){
                                //create order for the current client
                                Order o = new Order(clientid,clientid,0);
                                OrderBLL.insert(o);
                            }
                            if(p.getQuantity() < cmd.getProductQuantity()){
                                view.outOfStockPdf(cmd,p.getQuantity());
                            }
                            else{
                                orderitemid++;
                                Orderitem orderitem = new Orderitem(orderitemid,productid,cmd.getProductQuantity(),clientid);
                                /*if(OrderitemBLL.insert(orderitem) != 1){
                                   System.out.println("Order could not be placed");
                                }*/
                                OrderitemBLL.insert(orderitem);
                                //update the total of the client's order
                                float currentTotal = OrderBLL.findOrderByClientid(clientid).getTotal();
                                float price = ProductBLL.findProductByName(cmd.getProductName()).getPrice();
                                float cost = price*cmd.getProductQuantity();
                                OrderBLL.update(clientid,currentTotal+cost);
                                //update product quantity
                                ProductBLL.updateProductQuantity(p.getId(),p.getQuantity()-cmd.getProductQuantity());
                                billNumber++;
                                view.bill(cmd,cost,billNumber);
                            }
                            break;
                    }
                }
            }
        }
        catch(FileNotFoundException e){
            LOGGER.log(Level.WARNING,"Main:open file " + e.getMessage());
        }
    }
}
