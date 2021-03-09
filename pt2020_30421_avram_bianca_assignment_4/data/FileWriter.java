package data;

import business.MenuItem;
import business.Order;
import business.Restaurant;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;

public class FileWriter {
    public static void writeFile(Restaurant restaurant, String filename) throws IOException{
        File file = new File(filename);
        file.createNewFile(); // if file already exists will do nothing
        FileOutputStream streamfile = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(streamfile);
        if(Restaurant.getMenu().size() != 0){
            out.writeInt(Restaurant.getMenu().size());
            for (MenuItem m:Restaurant.getMenu()) {
                out.writeObject(m);
            }
            if(Restaurant.getOrders().size() != 0){
                out.writeInt(Restaurant.getOrders().size());
                for (Map.Entry en:Restaurant.getOrders().entrySet()) {
                    Order o = (Order)en.getKey();
                    out.writeObject(o);
                    int nr = Restaurant.getOrders().get(en.getKey()).size();
                    out.writeInt(nr);
                    for(MenuItem m : Restaurant.getOrders().get(en.getKey())){
                        out.writeObject(m);
                    }
                }
            }
        }
        out.close();
        streamfile.close();
    }
}
