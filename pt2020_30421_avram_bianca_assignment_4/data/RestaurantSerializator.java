package data;

import business.MenuItem;
import business.Order;
import business.Restaurant;

import java.io.*;
import java.util.ArrayList;

public class RestaurantSerializator {
    public static Restaurant restoreRestaurantData(String filename){
        Restaurant restaurant = new Restaurant();
        File file = new File(filename);
        FileInputStream streamfile = null;
        ObjectInputStream in = null;
        int menusize = 0;
        int ordernumber = 0;
        try{
            streamfile = new FileInputStream(file);
            in = new ObjectInputStream(streamfile);
            menusize = in.readInt();
        }
        catch (IOException ex){
            return null;
        }
        for(int i = 0 ; i < menusize ; i++){
            MenuItem m = null;
            try{
                m = (MenuItem) in.readObject();
            }
            catch (ClassNotFoundException | IOException ex){
                return null;
            }
            restaurant.adminCreateMenuItem(m);
        }
        try{
            ordernumber = in.readInt();
            ArrayList<MenuItem> items = new ArrayList<>();
            for(int i = 0 ; i < ordernumber ; i++){
                Order o = (Order)in.readObject();
                int count = in.readInt();
                for(int j = 0 ; j < count ; j++){
                    MenuItem m = (MenuItem)in.readObject();
                    items.add(m);
                }
                restaurant.waiterCreateNewOrder(o,items);
            }
            in.close();
            streamfile.close();
        }
        catch (IOException | ClassNotFoundException ex){
            return restaurant;
        }
        return restaurant;
    }
}
