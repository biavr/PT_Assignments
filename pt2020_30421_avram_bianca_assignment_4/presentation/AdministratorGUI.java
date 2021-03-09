package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import business.BaseProduct;
import business.CompositeProduct;
import business.MenuItem;
import business.Restaurant;
import data.FileWriter;

public class AdministratorGUI extends JFrame {
    MenuItem selected;
    public AdministratorGUI(Restaurant restaurant){
        super("Administrator");
    }

    public void initializeAdminFrame(Restaurant restaurant, String filename){
        //first add components
        AdministratorGUI admin = new AdministratorGUI(restaurant);
        Font font = new Font("Helvetica",1,14);
        JLabel adminTextLabel = new JLabel("Administrator");
        adminTextLabel.setFont(new Font("Helvetica",Font.BOLD,20));
        adminTextLabel.setBounds(380,30,170,40);

        JButton createMenuItem = new JButton("Create menu item");
        createMenuItem.setFont(font);
        createMenuItem.setBounds(50,100,200,30);

        JButton editMenuItem = new JButton("Edit menu item");
        editMenuItem.setFont(font);
        editMenuItem.setBounds(50,140,200,30);

        JButton deleteMenuItem = new JButton("Delete menu item");
        deleteMenuItem.setFont(font);
        deleteMenuItem.setBounds(50,180,200,30);

        JButton viewMenuItems = new JButton("View menu items");
        viewMenuItems.setFont(font);
        viewMenuItems.setBounds(50,220,200,30);

        JLabel nameLabel = new JLabel("Item name:");
        nameLabel.setFont(new Font("Helvetica",Font.BOLD,10));
        //nameLabel.setBounds(50,260,200,10);
        nameLabel.setBounds(0,0,200,10);

        JTextField productName = new JTextField();
        productName.setFont(font);
        productName.setBounds(0,20,200,20);

        JLabel priceLabel = new JLabel("Item price:");
        priceLabel.setFont(new Font("Helvetica",Font.BOLD,10));
        priceLabel.setBounds(0,50,200,10);

        JTextField productPrice = new JTextField();
        productPrice.setFont(font);
        productPrice.setBounds(0,70,200,20);

        JLabel item1label = new JLabel("Item 1:");
        item1label.setFont(font);
        item1label.setBounds(0,100,200,15);

        JComboBox<String> item1 = createMenuComboBox(Restaurant.getMenu());
        item1.setFont(font);
        item1.setBounds(0,120,200,20);

        JLabel item2label = new JLabel("Item 2:");
        item2label.setFont(font);
        item2label.setBounds(0,150,200,15);

        JComboBox<String> item2 = createMenuComboBox(Restaurant.getMenu());
        item2.setFont(font);
        item2.setBounds(00,170,200,20);

        JLabel item3label = new JLabel("Item 3:");
        item3label.setFont(font);
        item3label.setBounds(0,200,200,15);

        JComboBox<String> item3 = createMenuComboBox(Restaurant.getMenu());
        item3.setFont(font);
        item3.setBounds(0,220,200,20);

        JLabel item4label = new JLabel("Item 4:");
        item4label.setFont(font);
        item4label.setBounds(0,250,200,15);

        JComboBox<String> item4 = createMenuComboBox(Restaurant.getMenu());
        item4.setFont(font);
        item4.setBounds(0,270,200,20);

        JButton okCreateButton = new JButton("OK");
        okCreateButton.setFont(font);
        okCreateButton.setBounds(100,580,100,20);
        okCreateButton.setVisible(false);

        JButton okEditButton = new JButton("OK");
        okEditButton.setFont(font);
        okEditButton.setBounds(100,560,100,20);
        okEditButton.setVisible(false);

        JTable table = createMenuTable(Restaurant.getMenu());
        table.setVisible(false);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(300,100,500,390);
        table.setFillsViewportHeight(true);

        JButton closeButton = new JButton("Close");
        closeButton.setFont(font);
        closeButton.setBounds(500,500,100,30);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(50,260,200,400);
        panel.add(nameLabel);
        panel.add(productName);
        panel.add(priceLabel);
        panel.add(productPrice);
        panel.add(item1label);
        panel.add(item1);
        panel.add(item2label);
        panel.add(item2);
        panel.add(item3label);
        panel.add(item3);
        panel.add(item4label);
        panel.add(item4);
        panel.setVisible(false);

        admin.add(panel);
        admin.add(adminTextLabel);
        admin.add(createMenuItem);
        admin.add(editMenuItem);
        admin.add(deleteMenuItem);
        admin.add(viewMenuItems);
        admin.add(scrollPane);
        admin.add(okCreateButton);
        admin.add(okEditButton);
        admin.add(closeButton);
        admin.setLayout(null);
        admin.setSize(850,700);
        admin.setLocationRelativeTo(null);
        admin.setVisible(true);
        admin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        class createMenuItemActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(true);
                okCreateButton.setVisible(true);
            }
        }
        createMenuItem.addActionListener(new createMenuItemActionListener());

        class okCreateButtonActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(true);
                try{
                    String name = productName.getText().trim();
                    if(!restaurant.findItemByName(name).getName().toString().equals("NULL")){
                        throw new IllegalArgumentException();
                    }
                    if(item1.getSelectedItem().equals("None") && item2.getSelectedItem().equals("None") && item3.getSelectedItem().equals("None") && item4.getSelectedItem().equals("None")) {
                        //BaseProduct insert
                        float price = Float.parseFloat(productPrice.getText().trim());
                        BaseProduct product = new BaseProduct(name,price);
                        restaurant.adminCreateMenuItem(product);
                    }
                    else{
                        //CompositeProduct insert
                        MenuItem c1 = restaurant.findItemByName((String)item1.getSelectedItem());
                        MenuItem c2 = restaurant.findItemByName((String)item2.getSelectedItem());
                        MenuItem c3 = restaurant.findItemByName((String)item3.getSelectedItem());
                        MenuItem c4 = restaurant.findItemByName((String)item4.getSelectedItem());
                        CompositeProduct m = new CompositeProduct(name);
                        if(!c1.getName().equals("NULL"))
                            m.addProduct(c1);
                        if(!c2.getName().equals("NULL"))
                            m.addProduct(c2);
                        if(!c3.getName().equals("NULL"))
                            m.addProduct(c3);
                        if(!c4.getName().equals("NULL"))
                            m.addProduct(c4);
                        restaurant.adminCreateMenuItem(m);
                    }
                    JOptionPane.showMessageDialog(null,"Product successfully inserted");
                    refreshAllComboBoxes(item1,item2,item3,item4,Restaurant.getMenu());
                    productName.setText("");
                    productPrice.setText("");
                    panel.setVisible(false);
                    okCreateButton.setVisible(false);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,"The price must be a real number!");
                }
                catch(NullPointerException ex){
                    JOptionPane.showMessageDialog(null,"Please introduce a valid name!" );ex.printStackTrace();
                }
                catch(IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(null,"Product with this name already exists!");
                }
            }
        }
        okCreateButton.addActionListener(new okCreateButtonActionListener());

        class viewTableActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.isVisible())
                    table.setVisible(false);
                else {
                    table.setVisible(true);
                    refreshTable(table,Restaurant.getMenu());
                }
            }
        }
        viewMenuItems.addActionListener(new viewTableActionListener());

        class closeActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    FileWriter.writeFile(restaurant, filename);
                }
                catch (IOException ex){
                    JOptionPane.showMessageDialog(null,"Could not store data: "+ex.getMessage());
                }
                finally {
                    System.exit(0);
                }
            }
        }
        closeButton.addActionListener(new closeActionListener());

        class editItemActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                int col = 0;
                try{
                    String name = table.getModel().getValueAt(row,col).toString();
                    selected = restaurant.findItemByName(name);
                    panel.setVisible(true);
                    okEditButton.setVisible(true);
                    productName.setText(name);
                    productPrice.setText(Float.toString(selected.computePrice()));
                    if(selected instanceof CompositeProduct) {
                        CompositeProduct c = (CompositeProduct) selected;
                        try{
                            item1.setSelectedItem(c.getProducts().get(0).getName());
                            item2.setSelectedItem(c.getProducts().get(1).getName());
                            item3.setSelectedItem(c.getProducts().get(2).getName());
                            item4.setSelectedItem(c.getProducts().get(3).getName());
                        }
                        catch (IndexOutOfBoundsException ex){
                            //nothing...just move on
                        }
                    }
                    else {
                        item1.setSelectedItem("None");
                        item2.setSelectedItem("None");
                        item3.setSelectedItem("None");
                        item4.setSelectedItem("None");
                    }
                }
                catch (ArrayIndexOutOfBoundsException ex){
                    JOptionPane.showMessageDialog(null,"Select in the table the item you want to edit");
                }
            }
        }
        editMenuItem.addActionListener(new editItemActionListener());

        class okEditButtonActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = productName.getText().trim();
                if(item1.getSelectedItem().equals("None") && item2.getSelectedItem().equals("None") && item3.getSelectedItem().equals("None") && item4.getSelectedItem().equals("None")) {
                    float price = Float.parseFloat(productPrice.getText().trim());
                    MenuItem product = new BaseProduct(name,price);
                    restaurant.adminEditMenuItem(selected,product);
                }
                else{
                    MenuItem c1 = restaurant.findItemByName((String)item1.getSelectedItem());
                    MenuItem c2 = restaurant.findItemByName((String)item2.getSelectedItem());
                    MenuItem c3 = restaurant.findItemByName((String)item3.getSelectedItem());
                    MenuItem c4 = restaurant.findItemByName((String)item4.getSelectedItem());
                    CompositeProduct m = new CompositeProduct(name);
                    if(c1 != null)
                        m.addProduct(c1);
                    if(c2 != null)
                        m.addProduct(c2);
                    if(c3 != null)
                        m.addProduct(c3);
                    if(c4 != null)
                        m.addProduct(c4);
                    restaurant.adminEditMenuItem(selected,m);
                }
                JOptionPane.showMessageDialog(null,"Product successfully edited");
                refreshAllComboBoxes(item1,item2,item3,item4,Restaurant.getMenu());
                productName.setText("");
                productPrice.setText("");
                panel.setVisible(false);
                okEditButton.setVisible(false);
                table.setVisible(false);
            }
        }
        okEditButton.addActionListener(new okEditButtonActionListener());
        class deleteItemActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                int col = 0;
                try {
                    String name = table.getModel().getValueAt(row, col).toString();
                    restaurant.adminDeleteMenuItem(name);
                    refreshAllComboBoxes(item1,item2,item3,item4,Restaurant.getMenu());
                    table.setVisible(false);
                    JOptionPane.showMessageDialog(null,"Product successfully deleted");
                }
                catch (ArrayIndexOutOfBoundsException ex){
                    JOptionPane.showMessageDialog(null,"Select in the table the item you want to delete");
                }
            }
        }
        deleteMenuItem.addActionListener(new deleteItemActionListener());
    }

    public JComboBox createMenuComboBox(ArrayList<MenuItem> menu){
        JComboBox<String> combo = new JComboBox<>();
        if(menu == null || menu.size() == 0){
            combo.addItem("None");
        }
        else{
            combo.addItem("None");
            for (MenuItem m : menu) {
                combo.addItem(m.getName());
            }
        }
        return combo;
    }

    public void refreshMenu(JComboBox<String> cb, ArrayList<MenuItem> menu){
        cb.removeAllItems();
        if(menu == null || menu.size() == 0){
            cb.addItem("None");
        }
        else{
            cb.addItem("None");
            for (MenuItem m : menu) {
                cb.addItem(m.getName());
            }
        }
    }

    public void refreshAllComboBoxes(JComboBox<String> cb1,JComboBox<String> cb2,JComboBox<String> cb3,JComboBox<String> cb4, ArrayList<MenuItem> menu){
        refreshMenu(cb1,menu);
        refreshMenu(cb2,menu);
        refreshMenu(cb3,menu);
        refreshMenu(cb4,menu);
    }

    public JTable createMenuTable(ArrayList<MenuItem> list){
        String[] columnNames = {"Name",
                "Price",
                "Item1",
                "Item2",
                "Item3",
                "Item4"};
        String[][] data = new String[list.size()][6];
        int i = 0;
        try{
            for (MenuItem m:list) {
                data[i][0] = m.getName();
                data[i][1] = Float.toString(m.computePrice());
                int j = 2;
                for (MenuItem c:((CompositeProduct) m).getProducts()) {
                    data[i][j] = c.getName();
                    j++;
                }
                i++;
            }
        }
        catch (Exception ex){
            data[i][2] = data[i][3] = data[i][4] = data[i][5] = " ";
        }
        finally {
            DefaultTableModel tableModel = new DefaultTableModel(data,columnNames);
            JTable table = new JTable(tableModel);
            table.setRowSelectionAllowed(true);
            table.setPreferredScrollableViewportSize(new Dimension(500, 70));
            table.setFillsViewportHeight(true);
            return table;
        }
    }

    public void refreshTable(JTable table,ArrayList<MenuItem> list){
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
        String data[] = new String[6];
        for (MenuItem m:list) {
            data[0] = m.getName();
            data[1] = Float.toString(m.computePrice());
            try{
                int j = 2;
                for (MenuItem c:((CompositeProduct) m).getProducts()) {
                    data[j] = c.getName();
                    j++;
                }
            }
            catch (ClassCastException ex){
                data[2]=" ";
                data[3]=" ";
                data[4]=" ";
                data[5]=" ";
            }
            finally {
                tableModel.addRow(data);
            }
        }
    }
}
