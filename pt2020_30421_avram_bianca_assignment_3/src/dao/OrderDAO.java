package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Order;

/**
 * This class is the link to the database and implements static methods which execute queries on the corresponding tables from the database
 * @author Bianca-Veronica Avram
 * @version 1.0
 */
public class OrderDAO {
    protected static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO ordermanagement.order (id, clientid, total)"
            + "VALUES (?,?,?)";
    private static final String findStatementString = "SELECT * FROM ordermanagement.order WHERE id = ?";
    private static final String findAllStatementString = "SELECT * FROM ordermanagement.order";
    private static final String findByClientidStatementString = "SELECT * FROM ordermanagement.order WHERE clientid = ?";
    private static final String deleteStatementString = "DELETE FROM ordermanagement.order WHERE clientid = ?";
    private static final String updateTotalStatementString = "UPDATE ordermanagement.order SET total = ? WHERE clientid = ?";

    /**
     * This method searches for an order by id
     * @param id
     * @return An Order object representing the corresponding record from the database
     */
    public static Order findById(int id){
        Order toReturn = null;
        Connection dbcon = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try{
            findStatement = dbcon.prepareStatement(findStatementString, Statement.RETURN_GENERATED_KEYS);
            findStatement.setInt(1, id);
            rs = findStatement.executeQuery();
            rs.next();

            int orderid = rs.getInt("id");
            float total = rs.getInt("total");
            int clientid = rs.getInt("clientid");
            toReturn = new Order(orderid,clientid,total);
        }
        catch(SQLException ex){
            LOGGER.log(Level.WARNING,"OrderDAO:findById " + ex.getMessage());
        }
        finally{
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbcon);
        }
        return toReturn;
    }

    /**
     * This method searches for an order by clientid
     * @param id
     * @return An Order object representing the corresponding record from the database
     */
    public static Order findByClientid(int id){
        Order toReturn = null;
        Connection dbcon = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try{
            findStatement = dbcon.prepareStatement(findByClientidStatementString,Statement.RETURN_GENERATED_KEYS);
            findStatement.setInt(1, id);
            rs = findStatement.executeQuery();
            rs.next();

            int orderid = rs.getInt("id");
            float total = rs.getInt("total");
            int clientid = rs.getInt("clientid");
            toReturn = new Order(orderid,clientid,total);
        }
        catch(SQLException ex){
            LOGGER.log(Level.WARNING,"OrderDAO:findById " + ex.getMessage());
        }
        finally{
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbcon);
        }
        return toReturn;
    }

    /**
     * This method finds all the recorder in table order
     * @return A list containing all the records as Order objects
     */
    public static ArrayList<Order> findAllOrders(){
        Connection dbcon = ConnectionFactory.getConnection();
        ArrayList<Order> orders = new ArrayList<>();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try{
            findStatement = dbcon.prepareStatement(findAllStatementString,Statement.RETURN_GENERATED_KEYS);
            rs = findStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int clientid = rs.getInt("clientid");
                float total = rs.getFloat("total");
                Order o = new Order(id,clientid,total);
                orders.add(o);
            }
        }
        catch(SQLException ex){
            LOGGER.log(Level.WARNING,"OrderDAO:findAllOrders " + ex.getMessage());
        }
        finally{
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbcon);
        }
        return orders;
    }

    /**
     * This method executes the statement "DELETE FROM ordermanagement.order WHERE clientid = ?"
     * @param order The object that contains the values to be inserted
     * @return 1 in case of success, -1 otherwise
     */
    public static int insert(Order order){
        Connection con = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedid = -1;
        try{
            insertStatement = con.prepareStatement(insertStatementString,Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, order.getId());
            insertStatement.setInt(2,order.getClientid());
            insertStatement.setFloat(3,order.getTotal());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if(rs.next()){
                insertedid = rs.getInt(1);
            }
        }
        catch(SQLException ex){
            LOGGER.log(Level.WARNING, "OrderDAO:insert " + ex.getMessage());
        }
        finally{
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(con);
        }
        return insertedid;
    }
    /**
     * This method executes the statement "INSERT INTO ordermanagement.order (id, clientid, total) VALUES (?,?,?)"
     * @param id The id of the order that needs to be deleted
     * @return 1 in case of success, -1 otherwise
     */
    public static int delete(int id){
        Connection con = ConnectionFactory.getConnection();
        int rowsaffected = -1;
        PreparedStatement deleteStatement = null;
        try{
            deleteStatement = con.prepareStatement(deleteStatementString,Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1,id);
            rowsaffected = deleteStatement.executeUpdate();
        }
        catch(SQLException e){
            LOGGER.log(Level.WARNING, "OrderDAO:delete " + e.getMessage());
        }
        finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(con);
        }
        return rowsaffected;
    }

    /**
     * This method executes the statement "UPDATE ordermanagement.order SET total = ? WHERE clientid = ?"
     * @param id The id of the record that needs update
     * @param newtotal The updated value
     * @return 1 in case of success, -1 otherwise
     */
    public static int updateTotal(int id, float newtotal){
        Connection con = ConnectionFactory.getConnection();
        int rowsaffected = -1;
        if(newtotal == 0){
            return delete(id);
        }
        PreparedStatement updateStatement = null;
        try{
            updateStatement = con.prepareStatement(updateTotalStatementString,Statement.RETURN_GENERATED_KEYS);
            updateStatement.setFloat(1,newtotal);
            updateStatement.setInt(2,id);
            rowsaffected = updateStatement.executeUpdate();
        }
        catch (SQLException e){
            LOGGER.log(Level.WARNING, "OrderDAO:update " + e.getMessage());
        }
        finally{
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(con);
        }
        return rowsaffected;
    }
}
