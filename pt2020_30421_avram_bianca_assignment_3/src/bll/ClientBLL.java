package bll;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import dao.ClientDAO;
import model.Client;

/**
 * This class invokes the methods from the corresponding dao classes
 * @author Bianca-Veronica Avram
 * @version 1.0
 */
public class ClientBLL {
    /**
     * This method invokes findById from ClientDAO
     * @param id
     * @return positive integer if success, -1 otherwise
     */
    public static Client findClientById(int id){
        Client c = ClientDAO.findById(id);
        if(c == null){
            throw new NoSuchElementException("The client with id = " + id + " was not found!");
        }
        return c;
    }

    /**
     * This method invokes findByName from ClientDAO
     * @param name
     * @return positive integer if success, -1 otherwise
     */
    public static Client findClientByName(String name){
        Client c = ClientDAO.findByName(name);
        if(c == null){
            throw new NoSuchElementException("The client with name = " + name + " was not found!");
        }
        return c;
    }

    /**
     * This method invokes findAllClients from ClientDAO
     * @return A list of all clients in client table
     */
    public static ArrayList<Client> findAllClients(){
        return ClientDAO.findAllClients();
    }

    /**
     * This method invokes insertClient from ClientDAO
     * @param client
     * @return 1 if success, -1 otherwise
     */
    public static int insertClient(Client client){
        return ClientDAO.insert(client);
    }

    /**
     * This method invokes deleteClient from ClientDAO
     * @param id
     * @return 1 if success, -1 otherwise
     */
    public static int deleteClient(int id){
        return ClientDAO.delete(id);
    }
}
