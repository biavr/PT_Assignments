package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Orderitem;
/**
 * This class is the link to the database and implements static methods which execute queries on the corresponding tables from the database
 * @author Bianca-Veronica Avram
 * @version 1.0
 */
public class OrderitemDAO {
    protected static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO ordermanagement.orderitem (id, productid, quantity, orderid)"
            + "VALUES (?,?,?,?)";
    private static final String findStatementString = "SELECT * FROM ordermanagement.orderitem WHERE id = ?";
    private static final String findByOrderIdStatementString = "SELECT * FROM ordermanagement.orderitem WHERE orderid = ?";
    private static final String deleteStatementString = "DELETE FROM ordermanagement.orderitem WHERE id = ?";

    /**
     * This method searches for an order item by id
     * @param id
     * @return An Orderitem object containing the searched information
     */
    public static Orderitem findById(int id){
        Orderitem toReturn = null;
        Connection dbcon = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try{
            findStatement = dbcon.prepareStatement(findStatementString,Statement.RETURN_GENERATED_KEYS);
            findStatement.setInt(1, id);
            rs = findStatement.executeQuery();
            rs.next();

            int productid = rs.getInt("productid");
            int quantity = rs.getInt("quantity");
            int orderid = rs.getInt("orderid");
            toReturn = new Orderitem(id,productid,quantity,orderid);
        }
        catch(SQLException ex){
            LOGGER.log(Level.WARNING,"OrderitemDAO:findById " + ex.getMessage());
        }
        finally{
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbcon);
        }
        return toReturn;
    }
    /**
     * This method searches for an order item by orderid
     * @param orderid
     * @return An Orderitem object containing the searched information
     */
    public static ArrayList<Orderitem> findAllOrderItems(int orderid){
        ArrayList<Orderitem> list = new ArrayList<>();
        Connection dbcon = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try{
            findStatement = dbcon.prepareStatement(findByOrderIdStatementString,Statement.RETURN_GENERATED_KEYS);
            findStatement.setInt(1, orderid);
            rs = findStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int productid = rs.getInt("productid");
                int quantity = rs.getInt("quantity");
                Orderitem o = new Orderitem(id,productid,quantity,orderid);
                list.add(o);
            }
        }
        catch(SQLException ex){
            LOGGER.log(Level.WARNING,"OrderitemDAO:findAllOrderItems " + ex.getMessage());
        }
        finally{
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbcon);
        }
        return list;
    }

    /**
     * This method executest the query "INSERT INTO ordermanagement.orderitem (id, productid, quantity, orderid) VALUES (?,?,?,?)"
     * @param orderitem The object containing the information to be inserted
     * @return 1 if success, -1 otherwise
     */
    public static int insert(Orderitem orderitem){
        Connection con = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedid = -1;
        try{
            insertStatement = con.prepareStatement(insertStatementString,Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, orderitem.getId());
            insertStatement.setInt(2,orderitem.getProductid());
            insertStatement.setInt(3,orderitem.getQuantity());
            insertStatement.setInt(4,orderitem.getOrderid());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if(rs.next()){
                insertedid = rs.getInt(1);
            }
        }
        catch(SQLException ex){
            LOGGER.log(Level.WARNING, "OrderitemDAO:insert " + ex.getMessage());
        }
        finally{
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(con);
        }
        return insertedid;
    }
    /**
     * This method executest the query "DELETE FROM ordermanagement.orderitem WHERE id = ?"
     * @param id The id of the record to be deleted
     * @return 1 if success, -1 otherwise
     */
    public static int delete(int id){
        Connection con = ConnectionFactory.getConnection();
        int rowsaffected = -1;
        PreparedStatement deleteStatement = null;
        try{
            deleteStatement = con.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1,id);
            rowsaffected = deleteStatement.executeUpdate();
        }
        catch(SQLException e){
            LOGGER.log(Level.WARNING, "OrderitemDAO:delete " + e.getMessage());
        }
        finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(con);
        }
        return rowsaffected;
    }
}
