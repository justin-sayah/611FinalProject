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
    private final JLabel customerIDLabel;
    private final JTextField customerIDTextField;
    private final JButton viewAllActiveAccounts;
    private final JButton viewAccountsOver10KProfit;
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
        customerTable.setRowHeight(40);
        customerTable.setPreferredScrollableViewportSize(new Dimension(300,300));
        scrollPane = new JScrollPane(customerTable);
        container.setLayout(new BorderLayout());
        viewAllActiveAccounts = new JButton("View All Active Accounts");
        viewAccountsOver10KProfit = new JButton("View Accounts Over 10K Profit");
        back = new JButton("Back");
        customerIDLabel = new JLabel("Customer ID");
        customerIDTextField = new JTextField();


        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    private void setLocationAndSize(){
        customerIDLabel.setBounds(100,500,100,40);
        customerIDTextField.setBounds(220,500,300,40);
        viewAllActiveAccounts.setBounds(600,450,200,40);
        viewAccountsOver10KProfit.setBounds(600,510,200,40);
        back.setBounds(800,400,100,40);
    }

    private void addComponentsToContainer(){
        container.add(customerIDLabel);
        container.add(customerIDTextField);
        container.add(viewAllActiveAccounts);
        container.add(viewAccountsOver10KProfit);
        container.add(back);
        container.add(scrollPane, BorderLayout.CENTER);
        add(container);
    }

    private void addActionEvent(){
        viewAllActiveAccounts.addActionListener(this);
        viewAccountsOver10KProfit.addActionListener(this);
        back.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == back){
            dispose();
        }else if(e.getSource() == viewAllActiveAccounts){

            String customerIDText = customerIDTextField.getText();
            if(customerIDText.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please Enter a CustomerID");
            }else{
                try{
                    int customerID = Integer.parseInt(customerIDText);
                    Customer customer = peopleDao.getCustomer(customerID);
                    List<TradingAccount> list = tDao.getAllActive(customerID);
                    new ViewAccountsFrame(customer,list);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }else if(e.getSource() == viewAccountsOver10KProfit){

            String customerIDText = customerIDTextField.getText();
            if(customerIDText.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please Enter a CustomerID");
            }else{
                try{
                    int customerID = Integer.parseInt(customerIDText);
                    Customer customer = peopleDao.getCustomer(customerID);
                    List<TradingAccount> list1 = tDao.getAllActive(customerID);
                    List<TradingAccount> list = new ArrayList<>();
                    for(TradingAccount account: list1){
                        if(account.getRealizedProfitLoss() >= 10000){
                            list.add(account);
                        }
                    }
                    new ViewAccountsFrame(customer,list);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }
}