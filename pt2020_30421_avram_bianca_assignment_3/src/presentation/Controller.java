package presentation;

import java.util.ArrayList;
import java.util.stream.Stream;

import bll.*;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import model.Client;
import model.Order;
import model.Orderitem;
import model.Product;

/**
 * This is the class that provides the adapted input to the application and created the needed resources for generating the output
 * @author Bianca-Veronica Avram
 * @version 1.0
 */
public class Controller {
    /**
     *This method generates a list of Command objects based on the input String list
     * @param list
     * @return
     */
    public ArrayList<Command> buildCommandList(ArrayList<String> list){
        ArrayList<Command> commands = new ArrayList<Command>();
        for (String str:list) {
            Command c = Command.buildCommand(str);
            commands.add(c);
        }
        return commands;
    }

    /**
     *This method creates the table of client records
     * @return
     */
    public PdfPTable createClientTable(){
        PdfPTable table = new PdfPTable(3);
        addTableHeaderClient(table);
        ArrayList<Client> rs = ClientBLL.findAllClients();
        for (Client c:rs) {
                addClientRecord(table,c);
        }
        return table;
    }

    /**
     *This method created the header of a client table
     * @param table This is the table for which the header is created
     */
    public void addTableHeaderClient(PdfPTable table) {
        Stream.of("ClientID", "Name", "Address")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    /**
     *This method inserts a client record into the table
     * @param table This is the table in which the record in inserted
     * @param c This is the client that is inserted in the table as a record
     */
    public void addClientRecord(PdfPTable table, Client c) {
        table.addCell(Integer.toString(c.getId()));
        table.addCell(c.getName());
        table.addCell(c.getAddress());
    }

    /**
     *This method creates the table of client records
     * @return
     */
    public PdfPTable createProductTable(){
        PdfPTable table = new PdfPTable(4);
        addTableHeaderProduct(table);
        ArrayList<Product> products = ProductBLL.findAllClients();
        for (Product p:products) {
            addProductRecord(table,p);
        }
        return table;
    }

    /**
     *This method created the header of a product table
     * @param table This is the table for which the header is created
     */
    public void addTableHeaderProduct(PdfPTable table) {
        Stream.of("ProductID", "Name", "Quantity", "Price")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    /**
     *This method inserts a product record into the table
     * @param table This is the table in which the record in inserted
     * @param p This is the client that is inserted in the table as a record
     */
    public void addProductRecord(PdfPTable table, Product p) {
        table.addCell(Integer.toString(p.getId()));
        table.addCell(p.getName());
        table.addCell(Integer.toString(p.getQuantity()));
        table.addCell(Float.toString(p.getPrice()));
    }

    /**
     * This method creates the table of order records
     * @return
     */
    public PdfPTable createOrderTable(){
        PdfPTable table = new PdfPTable(5);
        addTableHeaderOrder(table);
        ArrayList<Order> orders = OrderBLL.findAllOrders();
        for (Order o:orders) {
            ArrayList<Orderitem> items = OrderitemBLL.findAllOrderItems(o.getId());
            for (Orderitem i:items) {
                int productid = i.getProductid();
                int quantity = i.getQuantity();
                Product p = ProductBLL.findProuctById(productid);
                p.setQuantity(quantity);
                Client c = ClientBLL.findClientById(o.getClientid());
                addOrderRecord(table,p,c);
            }
        }
        return table;
    }

    /**
     *This method created the header of a order table
     * @param table This is the table for which the header is created
     */
    public void addTableHeaderOrder(PdfPTable table) {
        Stream.of("Client", "Product", "Quantity", "Price", "Total")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    /**
     * This method inserts an order record into the table
     * @param table The table in which records are added
     * @param p Product containing information to add in the table
     * @param c Client containing information to add in the table
     */
    public void addOrderRecord(PdfPTable table, Product p, Client c) {
        table.addCell(c.getName());
        table.addCell(p.getName());
        table.addCell(Integer.toString(p.getQuantity()));
        table.addCell(Float.toString(p.getPrice()));
        table.addCell(Float.toString(p.getPrice()*p.getQuantity()));
    }
}
