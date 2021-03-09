package business;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface IRestaurantProcessing {
    void adminCreateMenuItem(MenuItem m);
    void adminDeleteMenuItem(String name);
    void adminEditMenuItem(MenuItem old, MenuItem modified);
    void waiterCreateNewOrder(Order order, ArrayList<MenuItem> items);
    float waiterComputeOrderPrice(Order order);
    void waiterGenerateBill(Order order) throws IOException;
    void notifyChef( ArrayList<MenuItem> items);
}
