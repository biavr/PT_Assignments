package bll;

import dao.OrderitemDAO;
import model.Orderitem;

import java.util.ArrayList;
/**
 * This class invokes the methods from the corresponding dao classes
 * @author Bianca-Veronica Avram
 * @version 1.0
 */
public class OrderitemBLL {
    /**
     * This method calls findOrderitem from OrderitemDAO
     * @param id
     * @return
     */
    public static Orderitem findOrderitem(int id){
        Orderitem o = OrderitemDAO.findById(id);
        return o;
    }

    /**
     * This method calls findallOrderItems from OrderitemDAO
     * @param orderid
     * @return A list of all orderitems in this table
     */
    public static ArrayList<Orderitem> findAllOrderItems(int orderid){
        return OrderitemDAO.findAllOrderItems(orderid);
    }

    /**
     * This method calls insert from OrderitemDAO
     * @param o
     * @return 1 if success, -1 otherwise
     */
    public static int insert(Orderitem o){
        return OrderitemDAO.insert(o);
    }

    /**
     * This method calls delete from OrderitemDAO
     * @param id
     * @return 1 if success, -1 otherwise
     */
    public static int delete(int id){
        return OrderitemDAO.delete(id);
    }
}
