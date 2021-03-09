package bll;

import model.Order;
import dao.OrderDAO;

import java.util.ArrayList;
/**
 * This class invokes the methods from the corresponding dao classes
 * @author Bianca-Veronica Avram
 * @version 1.0
 */
public class OrderBLL {
    /**
     * This method calls findById from OrderDAO
     * @param id
     * @return
     */
    public static Order findOrderById(int id){
        Order o = OrderDAO.findById(id);
        return o;
    }

    /**
     * This method calls findBClientiId from OrderDAO
     * @param clientid
     * @return
     */
    public static Order findOrderByClientid(int clientid){
        Order o = OrderDAO.findByClientid(clientid);
        return o;
    }

    /**
     * This method calls insert from OrderDAO
     * @param order
     * @return
     */
    public static int insert(Order order){
        return OrderDAO.insert(order);
    }

    /**
     * This method calls delete from OrderDAO
     * @param clientid
     * @return
     */
    public static int delete(int clientid){
        return OrderDAO.delete(clientid);
    }

    /**
     *
     * @param clientid
     * @param newtotal
     * @return
     */
    public static int update(int clientid, float newtotal){
        return  OrderDAO.updateTotal(clientid,newtotal);
    }

    /**
     *
     * @return
     */
    public static ArrayList<Order> findAllOrders(){
        return OrderDAO.findAllOrders();
    }
}
