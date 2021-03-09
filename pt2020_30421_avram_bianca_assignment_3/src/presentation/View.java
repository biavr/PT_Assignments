package presentation;

import bll.Command;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * This is the class that provides the interaction with the user, taking in the input and generating the corresponding output
 * @author Bianca-Veronica Avram
 * @version 1.0
 */
public class View {
    private FileReader fr;
    protected static final Logger LOGGER = Logger.getLogger(View.class.getName());
    public static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);

    public View(String inFile) throws FileNotFoundException {
        fr = new FileReader(inFile);
    }

    /**
     * This method reads the input file and generates a list of commands
     * @return A list of commands as Strings
     */
    public ArrayList<String> buildCommandList(){
        ArrayList<String> list = new ArrayList<String>();
        BufferedReader br = new BufferedReader(fr);
        String cmd;
        try{
            while((cmd = br.readLine()) != null){
                list.add(cmd);
            }
        }
        catch(IOException e){
            LOGGER.log(Level.WARNING,"View:read from file " + e.getMessage());
        }
        return list;
    }

    /**
     * This method generates a pdf document with the "out of stock" message
     * @param cmd A command that contains information about the order that cannot be placed
     * @param available The available quantity for the ordered product
     */
    public void outOfStockPdf(Command cmd, int available){
        String title = "Out of  stock";
        String p1 = "The order for client "+cmd.getClientName()+" could not be generated";
        String p2 = "Under-stock for product " +cmd.getProductName();
        String p3 = "Required quantity: "+cmd.getProductQuantity();
        String p4 = "Available quantity: "+available;
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(cmd.getProductName()+"OutOfStock.pdf"));
            document.open();
            Paragraph t = new Paragraph(title,catFont);
            document.add(t);
            Paragraph para1 = new Paragraph(p1);
            document.add(para1);
            Paragraph para2 = new Paragraph(p2);
            document.add(para2);
            Paragraph para3 = new Paragraph(p3);
            document.add(para3);
            Paragraph para4 = new Paragraph(p4);
            document.add(para4);
            document.close();
        } catch (DocumentException e) {
            LOGGER.log(Level.WARNING,"View:create message pdf");
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING,"View:create message pdf");
        }
    }

    /**
     * This method generates a bill as a pdf document
     * @param cmd A command that contains information about the order is placed
     * @param price The price per unit of the ordered product
     * @param billnumber The numer of the bill
     */
    public void bill(Command cmd,float price,int billnumber){
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("bill"+billnumber+".pdf"));
            document.open();
            Paragraph title = new Paragraph();
            title.add(new Paragraph("Bill no."+billnumber,catFont));
            title.add(new Paragraph(" "));
            title.add(new Paragraph("Client: "+cmd.getClientName()));
            title.add(new Paragraph("Product: "+cmd.getProductName()));
            title.add(new Paragraph("Quantity: "+cmd.getProductQuantity()));
            title.add(new Paragraph("Price per unit: "+price));
            float cost = price*cmd.getProductQuantity();
            title.add(new Paragraph("Total cost: "+cost));
            document.add(title);
            document.close();
        } catch (DocumentException e) {
            LOGGER.log(Level.WARNING,"View:create bill pdf");
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING,"View:create bill pdf");
        }
    }

    /**
     * This method generates a pdf document containing a table with all the clients
     * @param controller The object that implements the methods for creating the table
     * @param number The number of the client report
     */
    public void reportClient(Controller controller,int number){
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("reportClient"+number+".pdf"));
            document.open();
            PdfPTable table = controller.createClientTable();
            document.add(table);
            document.close();
        } catch (DocumentException e) {
            LOGGER.log(Level.WARNING,"View:report client pdf");
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING,"View:report client pdf");
        }
    }

    /**
     * This method generates a pdf document containing a table with all the products
     * @param controller The object that implements the methods for creating the table
     * @param number The number of the product report
     */
    public void reportProduct(Controller controller,int number){
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("reportProduct"+number+".pdf"));
            document.open();
            PdfPTable table = controller.createProductTable();
            document.add(table);
            document.close();
        } catch (DocumentException e) {
            LOGGER.log(Level.WARNING,"View:report product pdf");
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING,"View:report product pdf");
        }
    }

    /**
     * This method generates a pdf document containing a table with all the orders
     * @param controller The object that implements the methods for creating the table
     * @param number The number of the order report
     */
    public void reportOrder(Controller controller,int number){
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("reportOrder"+number+".pdf"));
            document.open();
            PdfPTable table = controller.createOrderTable();
            document.add(table);
            document.close();
        } catch (DocumentException e) {
            LOGGER.log(Level.WARNING,"View:report order pdf");
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING,"View:report order pdf");
        }
    }
}
