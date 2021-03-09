package presentation;

import business.MenuItem;
import business.Observer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChefGUI extends JFrame implements Observer {
    ArrayList<MenuItem> queue = new ArrayList<>();
    //this contains the queue with all the dishes the chef has to cook
    public ChefGUI(){
        super("Chef");
    }

    public void initializeChefFrame(){
        //add components to frame
        this.setLayout(new FlowLayout());
        this.setSize(200,300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void update(ArrayList<MenuItem> m) {
        for (MenuItem x:m) {
            queue.add(x);
        }
    }
}
