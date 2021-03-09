package business;

import java.io.FileWriter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Restaurant implements IRestaurantProcessing {
    private static HashMap<Order, ArrayList<MenuItem>> orders;
    private static ArrayList<MenuItem> menu;
    public Observer chef;
    public Restaurant(){
        orders = new HashMap<>();
        menu = new ArrayList<>();
    }

    public static ArrayList<MenuItem> getMenu() {
        return menu;
    }

    public static HashMap<Order, ArrayList<MenuItem>> getOrders() {
        return orders;
    }

    public ArrayList<MenuItem> getItemsOrdered(Order o){
        return orders.get(o);
    }

    public MenuItem findItemByName(String name){
        BaseProduct b = new BaseProduct("NULL",0);
        if(menu.size() == 0){
            return b;
        }
        if(name.equals("None")){
            return b;
        }
        for (MenuItem m:menu) {
            if(m.getName().equals(name)){
                return m;
            }
        }
        return b;
    }

    @Override
    public void adminCreateMenuItem(MenuItem m) {
        menu.add(m);
    }

    @Override
    public void adminDeleteMenuItem(String name) {
        MenuItem m = findItemByName(name);
        menu.remove(m);
    }

    @Override
    public void adminEditMenuItem(MenuItem old, MenuItem modified) {
        menu.set(menu.indexOf(old),modified);
    }

    @Override
    public void waiterCreateNewOrder(Order order, ArrayList<MenuItem> items) {
        orders.put(order,items);
        notifyChef(items);
    }


    @Override
    public float waiterComputeOrderPrice(Order order) {
        float price = 0;
        ArrayList<MenuItem> list = getItemsOrdered(order);
        for (MenuItem m : list) {
            price+=m.computePrice();
        }
        return price;
    }

    @Override
    public void waiterGenerateBill(Order order) throws IOException{
        File billFile = new File("bill"+order.getId()+".txt");
        FileWriter fw = new FileWriter(billFile);
        PrintWriter pw = new PrintWriter(fw);
        pw.write("Bill for order #"+order.getId()+"\nDate: "+order.getDate()+"\n");
        ArrayList<MenuItem> items = getItemsOrdered(order);
        for (MenuItem m : items) {
            pw.write(m.toString()+"\n");
        }
        pw.write("Total: "+waiterComputeOrderPrice(order));
        pw.close();
        fw.close();
    }

    @Override
    public void notifyChef( ArrayList<MenuItem> items) {
        //this will tell the chef to cook
        try{
            chef.update(items);
        }
        catch (NullPointerException ex){

        }
    }
}
