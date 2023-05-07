package org.TradingSystem.model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CustomerInformationFrame extends JFrame implements ActionListener {
    // The table showing information
    private final JTable customerTable;
    private final JPanel container;
    private final JScrollPane scrollPane;
    //private final JLabel customerIDLabel;
    //private final JTextField customerIDTextField;
    private final JButton viewAllActiveAccounts;
    private final JButton viewAccountsOver10KProfit;
    private final JButton sendMessage;
    private final JButton back;
    private Manager manager;
    private PeopleDao peopleDao;
    private TradingAccountDao tDao;
    public CustomerInformationFrame(Manager manager){
        setTitle("Customer Information Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(10,10);
        setSize(1000,600);
        setVisible(true);
        setResizable(false);
        container = new JPanel();
        peopleDao = new PeopleDao();
        tDao = new TradingAccountDao();
        this.manager = manager;
        Object[] columnNames = {"ID", "FirstName","LastName","Username", "Dob", "SSN"};
        List<Customer> list = peopleDao.getAllCustomers();
        Object[][] data = new Object[list.size()][6];
        for(int i = 0; i < list.size(); i++){
            data[i][0] = list.get(i).getID();
            data[i][1] = list.get(i).getFirstName();
            data[i][2] = list.get(i).getLastName();
            data[i][3] = list.get(i).getUsername();
            data[i][4] = list.get(i).getDob();
            data[i][5] = list.get(i).getSSN();
        }
        customerTable = new JTable(data,columnNames);
        customerTable.setRowHeight(30);
        customerTable.setPreferredScrollableViewportSize(new Dimension(300,300));
        scrollPane = new JScrollPane(customerTable);
        container.setLayout(new BorderLayout());
        viewAllActiveAccounts = new JButton("View All Active Accounts");
        viewAccountsOver10KProfit = new JButton("View Accounts Over 10K Profit");
        back = new JButton("Back");
        sendMessage = new JButton("Send Message");
        //customerIDLabel = new JLabel("Customer ID");
        //customerIDTextField = new JTextField();


        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    private void setLocationAndSize(){
        //customerIDLabel.setBounds(100,500,50,40);
        //customerIDTextField.setBounds(170,500,100,40);
        viewAllActiveAccounts.setBounds(300,450,300,40);
        viewAccountsOver10KProfit.setBounds(300,500,300,40);
        sendMessage.setBounds(300,400,300,40);
        back.setBounds(800,500,100,40);
    }

    private void addComponentsToContainer(){
        container.add(viewAllActiveAccounts);
        container.add(viewAccountsOver10KProfit);
        container.add(back);
        container.add(sendMessage);
        container.add(scrollPane, BorderLayout.CENTER);
        add(container);
    }

    private void addActionEvent(){
        viewAllActiveAccounts.addActionListener(this);
        viewAccountsOver10KProfit.addActionListener(this);
        back.addActionListener(this);
        sendMessage.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == back){
            new ManagerFrame(manager);
            dispose();
        }else if(e.getSource() == viewAllActiveAccounts){
            // Select a row and view all active accounts of the customer
            int row = customerTable.getSelectedRow();
            List<Customer> list = peopleDao.getAllCustomers();
            if(row < 0){
                JOptionPane.showMessageDialog(this, "Please choose a row!");
            }else {
                Customer customer = list.get(row);
                List<TradingAccount> list1 = tDao.getAllActive(customer.getID());
                new ViewAccountsFrame(customer, list1);
            }
        }else if(e.getSource() == viewAccountsOver10KProfit){
            // Select a row and view the accounts over 10K profit of the customer
            int row = customerTable.getSelectedRow();
            List<Customer> list = peopleDao.getAllCustomers();
            if(row < 0){
                JOptionPane.showMessageDialog(this, "Please choose a row!");
            }else {
                Customer customer = list.get(row);
                List<TradingAccount> list1 = tDao.getAllActive(customer.getID());
                // Add accounts of over 10K profit
                List<TradingAccount> list2 = new ArrayList<>();
                for(TradingAccount account: list1){
                    if(account.getRealizedProfitLoss() >= 10000){
                        list2.add(account);
                    }
                }
                new ViewAccountsFrame(customer, list2);
            }
        }else if(e.getSource() == sendMessage){
            // Select a row and send message to the customer
            int row = customerTable.getSelectedRow();
            List<Customer> list = peopleDao.getAllCustomers();
            if(row < 0){
                JOptionPane.showMessageDialog(this, "Please choose a row!");
            }else {
                Customer customer = list.get(row);
                // Send message
                new SendMessageFrame(manager,customer);
                dispose();
            }
        }
    }
}