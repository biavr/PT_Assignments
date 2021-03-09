package start;

import business.Restaurant;
import data.RestaurantSerializator;
import presentation.AdministratorGUI;
import presentation.ChefGUI;
import presentation.WaiterGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class FirstFrame extends JFrame {
    public FirstFrame(String title){
        super(title);
    }
    public static void main(String args[]){
        String filename;
        filename = args[0];
        //filename = "restaurant.ser";
        Restaurant restaurant;
        restaurant = RestaurantSerializator.restoreRestaurantData(filename);
        if(restaurant == null){
            restaurant = new Restaurant();
            JOptionPane.showMessageDialog(null,"Could not restore data! File empty or inexistent");
        }
        FirstFrame frame = new FirstFrame("Restaurant");
        JPanel welcomeTextPanel = new JPanel();
        JPanel selectTextPanel = new JPanel();
        Font font = new Font("Helvetica",1,14);
        JLabel welcome = new JLabel("Welcome to restaurant management!");
        welcome.setFont(new Font("Helvetica",Font.BOLD,20));
        JLabel selectUser = new JLabel("Please select your type of user.");
        selectUser.setFont(font);
        welcomeTextPanel.add(welcome);
        selectTextPanel.add(selectUser);
        frame.add(welcomeTextPanel);
        frame.add(selectTextPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton openAdminGUI = new JButton("Administrator");
        openAdminGUI.setFont(font);
        JButton openWaiterGUI = new JButton("Waiter");
        openWaiterGUI.setFont(font);
        JButton openChefGUI = new JButton("Chef");
        openChefGUI.setFont(font);
        Restaurant finalRestaurant1 = restaurant;
        class openAdminFrameActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                AdministratorGUI admin = new AdministratorGUI(finalRestaurant1);
                admin.initializeAdminFrame(finalRestaurant1,filename);
            }
        }
        openAdminGUI.addActionListener(new openAdminFrameActionListener());
        Restaurant finalRestaurant = restaurant;
        class openWaiterFrameActionListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                WaiterGUI waiter = new WaiterGUI(finalRestaurant);
                waiter.initializeWaiterFrame(finalRestaurant,filename);
            }
        }
        openWaiterGUI.addActionListener(new openWaiterFrameActionListener());
        class openChefFrameActionListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                ChefGUI chef = new ChefGUI();
                chef.initializeChefFrame();
            }
        }
        openChefGUI.addActionListener(new openChefFrameActionListener());

        buttonPanel.add(openAdminGUI);
        buttonPanel.add(openWaiterGUI);
        buttonPanel.add(openChefGUI);
        frame.add(buttonPanel);
        frame.setLayout(new FlowLayout());
        frame.setSize(420,170);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
