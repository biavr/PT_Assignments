package presentation;

import business.MenuItem;
import business.Order;
import business.Restaurant;
import data.FileWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WaiterGUI extends JFrame {
    Order order;
    ArrayList<MenuItem> orderedItems;
    Order orderForBill = null;
    public WaiterGUI(Restaurant restaurant){
        super("Waiter");
    }
    public void initializeWaiterFrame(Restaurant restaurant, String filename){
        WaiterGUI waiter = new WaiterGUI(restaurant);
        Font font = new Font("Helvetica",1,14);
        //add components to frame
        JLabel adminTextLabel = new JLabel("Waiter");
        adminTextLabel.setFont(new Font("Helvetica",Font.BOLD,20));
        adminTextLabel.setBounds(310,30,120,40);

        JButton createOrder = new JButton("Create order");
        createOrder.setBounds(50,70,120,30);
        JButton computeBill = new JButton("ComputeBill");
        computeBill.setBounds(50,110,120,30);

        JLabel itemsLabel = new JLabel("Items in order: ");
        itemsLabel.setBounds(230,300,130,30);
        itemsLabel.setFont(font);
        itemsLabel.setVisible(false);

        JComboBox<String> itemscb = orderContentComboBox();
        itemscb.setBounds(380,300,120,30);
        itemscb.setVisible(false);

        JTable table = createOrdersTable(Restaurant.getOrders());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(230,70,270,200);
        table.setFillsViewportHeight(true);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                //make label and combo box visible
                itemscb.setVisible(true);
                itemsLabel.setVisible(true);
                int id = Integer.parseInt((String)table.getValueAt(table.getSelectedRow(), 0));
                for (Map.Entry m: Restaurant.getOrders().entrySet()) {
                    Order o = (Order) m.getKey();
                    if(o.getId() == id){
                        orderForBill = o;
                        refreshComboBox(itemscb,Restaurant.getOrders().get(o));
                    }
                }
            }
        });

        JButton closeButton = new JButton("Close");
        closeButton.setFont(font);
        closeButton.setBounds(300,380,100,30);

        JLabel menuContentLabel = new JLabel("Available items:");
        menuContentLabel.setFont(font);
        menuContentLabel.setVisible(false);
        menuContentLabel.setBounds(50,230,120,30);

        JComboBox<String> itemSelectioncb = menuContentComboBox(Restaurant.getMenu());
        itemSelectioncb.setBounds(50,260,150,30);
        itemSelectioncb.setVisible(false);

        JButton addItemtoOrder = new JButton("Add to order");
        addItemtoOrder.setFont(font);
        addItemtoOrder.setBounds(50,300,150,30);
        addItemtoOrder.setVisible(false);

        JButton submitOrder = new JButton("Submit order");
        submitOrder.setFont(font);
        submitOrder.setBounds(50,340,150,30);
        submitOrder.setVisible(false);

        waiter.add(adminTextLabel);
        waiter.add(createOrder);
        waiter.add(computeBill);
        waiter.add(scrollPane);
        waiter.add(closeButton);
        waiter.add(itemscb);
        waiter.add(itemsLabel);
        waiter.add(menuContentLabel);
        waiter.add(itemSelectioncb);
        waiter.add(addItemtoOrder);
        waiter.add(submitOrder);

        waiter.setLayout(null);
        waiter.setSize(600,500);
        waiter.setVisible(true);
        waiter.setLocationRelativeTo(null);
        waiter.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        class closeActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    FileWriter.writeFile(restaurant,filename);
                }
                catch (IOException ex){
                    JOptionPane.showMessageDialog(null,"Could not store data: "+ex.getMessage());

                }
                System.exit(0);
            }
        }
        closeButton.addActionListener(new closeActionListener());

        class createOrderActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                itemSelectioncb.setVisible(true);
                menuContentLabel.setVisible(true);
                addItemtoOrder.setVisible(true);
                submitOrder.setVisible(true);
                order = new Order();
                orderedItems = new ArrayList<>();
            }
        }
        createOrder.addActionListener(new createOrderActionListener());

        class addItemtoOrderActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String name = itemSelectioncb.getSelectedItem().toString();
                    MenuItem item = restaurant.findItemByName(name);
                    orderedItems.add(item);
                    JOptionPane.showMessageDialog(null,"Item successfully inserted to order");

                }
                catch (NullPointerException ex){
                    JOptionPane.showMessageDialog(null,"Select from the list an item to add to order");
                }
            }
        }
        addItemtoOrder.addActionListener(new addItemtoOrderActionListener());

        class submitActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                if(orderedItems.size() == 0){
                    JOptionPane.showMessageDialog(null,"Error! You attempt to insert an empty order");
                }
                else{
                    restaurant.waiterCreateNewOrder(order,orderedItems);
                    refreshTable(table,Restaurant.getOrders());
                    JOptionPane.showMessageDialog(null,"Ordered successfully placed");
                }
                menuContentLabel.setVisible(false);
                itemSelectioncb.setVisible(false);
                addItemtoOrder.setVisible(false);
                submitOrder.setVisible(false);

            }
        }
        submitOrder.addActionListener(new submitActionListener());

        class generateBillActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    restaurant.waiterGenerateBill(orderForBill);
                    JOptionPane.showMessageDialog(null,"Bill successfully generated");

                }
                catch (IOException ex){
                    JOptionPane.showMessageDialog(null,"Error! Could not create bill");
                }
                catch (NullPointerException ex){
                    JOptionPane.showMessageDialog(null,"Select from table the order you want to create bill for");
                }
            }
        }
        computeBill.addActionListener(new generateBillActionListener());
    }

    public JTable createOrdersTable(HashMap<Order, ArrayList<MenuItem>> orders){
        String[] columnNames = {"Id","Date"};
        String[][] data = new String[orders.size()][3];
        int i = 0;
        for (Map.Entry m: orders.entrySet()) {
            Order o = (Order) m.getKey();
            data[i][0] = Integer.toString(o.getId());
            data[i][1] = o.getDate();
            i++;
        }
        DefaultTableModel tableModel = new DefaultTableModel(data,columnNames);
        JTable table = new JTable(tableModel);
        table.setRowSelectionAllowed(true);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        return table;
    }

    public void refreshTable(JTable table, HashMap<Order, ArrayList<MenuItem>> orders){
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
        String[] data = new String[2];
        for (Map.Entry m: orders.entrySet()) {
            Order o = (Order) m.getKey();
            data[0] = Integer.toString(o.getId());
            data[1] = o.getDate();
            tableModel.addRow(data);
        }
    }

    public JComboBox<String> orderContentComboBox(){
        JComboBox<String> cb = new JComboBox<>();
        cb.addItem("None");
        return cb;
    }

    public JComboBox<String> menuContentComboBox(ArrayList<MenuItem> menu){
        JComboBox<String> cb = new JComboBox<>();
        cb.addItem("None");
        for (MenuItem m:menu) {
            cb.addItem(m.getName());
        }
        return cb;
    }

    public void refreshComboBox(JComboBox cb,ArrayList<MenuItem> list){
        cb.removeAllItems();
        for (MenuItem m:list) {
            cb.addItem(m.getName());
        }
    }
}
