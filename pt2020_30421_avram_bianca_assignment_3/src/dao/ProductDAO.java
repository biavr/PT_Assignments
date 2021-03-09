package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Product;

/**
 * This class is the link to the database and implements static methods which execute queries on the corresponding tables from the database
 * @author Bianca-Veronica Avram
 * @version 1.0
 */
public class ProductDAO {
    protected static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO ordermanagement.product (id, name, quantity, price)"
            + " VALUES (?,?,?,?)";
    private static final String findByNameStatementString = "SELECT * FROM ordermanagement.product WHERE name = ?";
    private static final String findByIdStatementString = "SELECT * FROM ordermanagement.product WHERE id = ?";
    private static final String findAlltatementString = "SELECT * FROM ordermanagement.product";
    private static final String deleteStatementString = "DELETE FROM ordermanagement.product WHERE name = ?";
    private static final String updatePriceStatementString = "UPDATE ordermanagement.product SET price = ? WHERE id = ?";
    private static final String updateQuantityStatementString = "UPDATE ordermanagement.product SET quantity = ? WHERE id = ?";

    /**
     * This method executes the statement "SELECT * FROM ordermanagement.product WHERE name = ?"
     * @param productname Tha name of the product being searched for
     * @return An object of type Product that contains the information stored in the database
     */
    public static Product findByName(String productname){
        Product returnproduct = null;

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try{
            findStatement = con.prepareStatement(findByNameStatementString,Statement.RETURN_GENERATED_KEYS);
            findStatement.setString(1,productname);
            rs = findStatement.executeQuery();
            rs.next();
            if(rs == null)
                return null;

            int id = rs.getInt("id");
            String name = rs.getString("name");
            int quantity = rs.getInt("quantity");
            float price = rs.getFloat("price");
            returnproduct = new Product(id,name,quantity,price);
        }
        catch(SQLException ex){
            LOGGER.log(Level.WARNING,"ProductDAO:findByName " + ex.getMessage());
        }
        finally{
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(con);
        }
        return returnproduct;
    }
    /**
     * This method executes the statement "SELECT * FROM ordermanagement.product WHERE id = ?"
     * @param productid Tha id of the product being searched for
     * @return An object of type Product that contains the information stored in the database
     */
    public static Product findById(int productid){
        Product toReturn = null;
        Connection dbcon = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try{
            findStatement = dbcon.prepareStatement(findByIdStatementString,Statement.RETURN_GENERATED_KEYS);
            findStatement.setInt(1, productid);
            rs = findStatement.executeQuery();
            rs.next();
            if(rs == null)
                return null;

            String name = rs.getString("name");
            int quantity = rs.getInt("quantity");
            float price = rs.getFloat("price");
            toReturn = new Product(productid, name, quantity, price);
        }
        catch(SQLException ex){
            LOGGER.log(Level.WARNING,"ProductDAO:findById " + ex.getMessage());
        }
        finally{
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbcon);
        }
        return toReturn;
    }
    /**
     * This method executes the statement "SELECT * FROM ordermanagement.product"
     * @return A list of objects of type Product that contain the information stored in the database
     */
    public static ArrayList<Product> findAllProducts(){
        Connection dbcon = ConnectionFactory.getConnection();
        ArrayList<Product> products = new ArrayList<>();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try{
            findStatement = dbcon.prepareStatement(findAlltatementString,Statement.RETURN_GENERATED_KEYS);
            rs = findStatement.executeQuery();
            while(rs.next()){
                int productid = rs.getInt("id");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                float price = rs.getFloat("price");
                Product p = new Product(productid,name,quantity,price);
                products.add(p);
            }
        }
        catch(SQLException ex){
            LOGGER.log(Level.WARNING,"ProductDAO:findAllProducts " + ex.getMessage());
        }
        finally{
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbcon);
        }
        return products;
    }

    /**
     * This method executes the statement "INSERT INTO ordermanagement.product (id, name, quantity, price) VALUES (?,?,?,?)"
     * @param product The product containing information to be stored
     * @return 1 if success, -1 otherwise
     */
    public static int insert(Product product){
        Connection con = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedid = -1;
        try{
            insertStatement = con.prepareStatement(insertStatementString,Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1,product.getId());
            insertStatement.setString(2,product.getName());
            insertStatement.setInt(3,product.getQuantity());
            insertStatement.setFloat(4,product.getPrice());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if(rs.next()){
                insertedid = rs.getInt(1);
            }
        }
        catch(SQLException e){
            LOGGER.log(Level.WARNING, "ProductDAO:insert " + e.getMessage());
        }
        return insertedid;
    }

    /**
     * This method executes the statement "DELETE FROM ordermanagement.product WHERE name = ?"
     * @param productname The name of the product to delete
     * @return 1 if success, -1 otherwise
     */
    public static int delete(String productname){
        Connection con = ConnectionFactory.getConnection();
        int rowsaffected = 0;
        PreparedStatement deleteStatement = null;
        try{
            deleteStatement = con.prepareStatement(deleteStatementString,Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setString(1,productname);
            rowsaffected = deleteStatement.executeUpdate();
        }
        catch(SQLException e){
            LOGGER.log(Level.WARNING, "ProductDAO:delete " + e.getMessage());
        }
        finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(con);
        }
        return rowsaffected;
    }

    /**
     * This method executes the query "UPDATE ordermanagement.product SET price = ? WHERE id = ?"
     * @param productid The id if the record to be updated
     * @param newprice The updated information
     * @return 1 if success, -1 otherwise
     */
    public static int updatePrice(int productid, float newprice){
        Connection con = ConnectionFactory.getConnection();
        int rowsaffected = -1;
        PreparedStatement updateStatement = null;
        try{
            updateStatement = con.prepareStatement(updatePriceStatementString,Statement.RETURN_GENERATED_KEYS);
            updateStatement.setFloat(1,newprice);
            updateStatement.setInt(2,productid);
            rowsaffected = updateStatement.executeUpdate();
        }
        catch(SQLException e){
            LOGGER.log(Level.WARNING, "ProductDAO:update price " + e.getMessage());
        }
        finally{
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(con);
        }
        return rowsaffected;
    }
    /**
     * This method executes the query "UPDATE ordermanagement.product SET quantity = ? WHERE id = ?"
     * @param productid The id if the record to be updated
     * @param newquantity The updated information
     * @return 1 if success, -1 otherwise
     */
    public static int updateQuantity(int productid, int newquantity){
        Connection con = ConnectionFactory.getConnection();
        int rowsaffected = -1;
        PreparedStatement updateStatement = null;
        try{
            if(newquantity == 0){
                return delete(findById(productid).getName());
            }
            updateStatement = con.prepareStatement(updateQuantityStatementString,Statement.RETURN_GENERATED_KEYS);
            updateStatement.setInt(1,newquantity);
            updateStatement.setInt(2,productid);
            rowsaffected = updateStatement.executeUpdate();
        }
        catch(SQLException e){
            LOGGER.log(Level.WARNING, "ProductDAO:update quantity " + e.getMessage());
        }
        finally{
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(con);
        }
        return rowsaffected;
    }
}
