package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Client;

/**
 * This class is the link to the database and implements static methods which execute queries on the corresponding tables from the database
 * @author Bianca-Veronica Avram
 * @version 1.0
 */
public class ClientDAO {
    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO ordermanagement.client (id, name, address)"
            + "VALUES (?,?,?)";
    private final static String findStatementString = "SELECT * FROM ordermanagement.client WHERE id = ?";
    private final static String findAllStatementString = "SELECT *FROM ordermanagement.client";
    private final static String findByNameStatementString = "SELECT * FROM ordermanagement.client WHERE name = ?";
    private final static String deleteStatementString = "DELETE FROM ordermanagement.client WHERE id = ?";

    /**
     * This method executes the query "SELECT * FROM ordermanagement.client WHERE id = ?"
     * @param clientid The id of the client that is being searched in the table
     * @return An object of type Client represented by the record from the database
     */
    public static Client findById(int clientid){
        Client toReturn = null;
        Connection dbcon = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try{
            findStatement = dbcon.prepareStatement(findStatementString,Statement.RETURN_GENERATED_KEYS);
            findStatement.setLong(1, clientid);
            rs = findStatement.executeQuery();
            rs.next();

            String name = rs.getString("name");
            String address = rs.getString("address");
            toReturn = new Client(clientid, name, address);
        }
        catch(SQLException ex){
            LOGGER.log(Level.WARNING,"ClientDAO:findById " + ex.getMessage());
        }
        finally{
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbcon);
        }
        return toReturn;
    }
    /**
     * This method executes the query "SELECT * FROM ordermanagement.client WHERE name = ?"
     * @param clientname The name of the client that is being searched in the table
     * @return An object of type Client represented by the record from the database
     */
    public static Client findByName(String clientname){
        Client toReturn = null;
        Connection dbcon = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try{
            findStatement = dbcon.prepareStatement(findByNameStatementString,Statement.RETURN_GENERATED_KEYS);
            findStatement.setString(1, clientname);
            rs = findStatement.executeQuery();
            rs.next();

            int clientid = rs.getInt("id");
            String name = rs.getString("name");
            String address = rs.getString("address");
            toReturn = new Client(clientid, name, address);
        }
        catch(SQLException ex){
            LOGGER.log(Level.WARNING,"ClientDAO:findByName " + ex.getMessage());
        }
        finally{
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbcon);
        }
        return toReturn;
    }
    /**
     * This method executes the query "SELECT * FROM ordermanagement.client"
     * @return A list of objects of type Client represented by the records from the database
     */
    public static ArrayList<Client> findAllClients(){
        Connection dbcon = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        ArrayList<Client> list = new ArrayList<>();
        try{
            findStatement = dbcon.prepareStatement(findAllStatementString,Statement.RETURN_GENERATED_KEYS);
            rs = findStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                Client c = new Client(id,name,address);
                list.add(c);
            }
        }
        catch(SQLException ex){
            LOGGER.log(Level.WARNING,"ClientDAO:findAllClients " + ex.getMessage());
        }
        finally{
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbcon);
        }
        return list;
    }

    /**
     * This method executes the query "INSERT INTO ordermanagement.client (id, name, address) VALUES (?,?,?)"
     * @param client The client that needs to be stored in the database
     * @return An positive integer representing the number of affected rows after executing the query or -1 in case of error
     */
    public static int insert(Client client){
        Connection con = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedid = -1;
        try{
            insertStatement = con.prepareStatement(insertStatementString,Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, client.getId());
            insertStatement.setString(2,client.getName());
            insertStatement.setString(3,client.getAddress());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if(rs.next()){
                insertedid = rs.getInt(1);
            }
        }
        catch(SQLException ex){
            LOGGER.log(Level.WARNING, "ClientDAO:insert " + ex.getMessage());
        }
        finally{
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(con);
        }
        return insertedid;
    }
    /**
     * This method executes the query "DELETE FROM ordermanagement.client WHERE id = ?"
     * @param clientid The id of the client that needs to be deleted from the database
     * @return 1 after executing the query successfully or -1 in case of error
     */
    public static int delete(int clientid){
        Connection con = ConnectionFactory.getConnection();
        int rowsaffected = -1;
        PreparedStatement deleteStatement = null;
        try{
            deleteStatement = con.prepareStatement(deleteStatementString,Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1,clientid);
            rowsaffected = deleteStatement.executeUpdate();
        }
        catch(SQLException e){
            LOGGER.log(Level.WARNING, "ClientDAO:delete " + e.getMessage());
        }
        finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(con);
        }
        return rowsaffected;
    }
}
