package org.TradingSystem.model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CustomerInformationFrame extends JFrame implements ActionListener {
    // The table showing information
    private final JTable customerTable;
    private final JPanel container;
    private final JScrollPane scrollPane;
    private final JLabel customerIDLabel;
    private final JTextField customerIDTextField;
    private final JButton viewAccounts;
    private final JButton back;
    private PeopleDao peopleDao;
    public CustomerInformationFrame(Manager manager){
        setTitle("Customer Main Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(10,10);
        setSize(1000,600);
        setResizable(false);
        container = new JPanel();
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
        viewAccounts = new JButton("View Accounts");
        back = new JButton("Back");
        customerIDLabel = new JLabel("Customer ID");
        customerIDTextField = new JTextField();
        peopleDao = new PeopleDao();

        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    private void setLocationAndSize(){
        customerIDLabel.setBounds(100,500,100,40);
        customerIDTextField.setBounds(220,500,300,40);
        viewAccounts.setBounds(600,500,100,40);
        back.setBounds(800,400,100,40);
    }

    private void addComponentsToContainer(){
        container.add(viewAccounts);
        container.add(back);
        container.add(scrollPane, BorderLayout.CENTER);
        add(container);
    }

    private void addActionEvent(){
        viewAccounts.addActionListener(this);
        back.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == back){
            dispose();
        }else if(e.getSource() == viewAccounts){
            // Todo
            // Select a customer and view his/her accounts
            // new ViewAccountsFrame(Customer);
        }
    }
}
