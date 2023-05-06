package org.TradingSystem.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ViewAccountsFrame extends JFrame implements ActionListener {


    private JTable customerTable;
    private final JPanel container;
    private JScrollPane scrollPane;
    private TradingAccountDao tDao;
    private final JButton back;
    private Customer customer;
    private List<TradingAccount> list;

    public ViewAccountsFrame(Customer customer, List<TradingAccount> list) {
        setTitle("Customer Accounts");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(10,10);
        setSize(1000,600);
        setVisible(true);
        setResizable(false);
        container = new JPanel();
        tDao = new TradingAccountDao();
        this.customer = customer;


        Object[] columnNames = {"Account Number", "CustomerID","Type","Balance", "RealizedProfitLoss"};
        this.list = list;
        Object[][] data = new Object[list.size()][5];
        for(int i = 0; i < list.size(); i++){
            data[i][0] = list.get(i).getAccountNumber();
            data[i][1] = list.get(i).getPersonId();
            data[i][2] = list.get(i).getType();
            data[i][3] = list.get(i).getBalance();
            data[i][4] = list.get(i).getRealizedProfitLoss();
        }
        customerTable = new JTable(data,columnNames);
        customerTable.setRowHeight(40);
        customerTable.setPreferredScrollableViewportSize(new Dimension(300,300));
        scrollPane = new JScrollPane(customerTable);
        container.setLayout(new BorderLayout());
        back = new JButton("Back");

        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();

    }

    private void setLocationAndSize(){
        back.setBounds(450,400,100,40);
    }

    private void addComponentsToContainer(){
        container.add(back);
        container.add(scrollPane, BorderLayout.CENTER);
        add(container);
    }

    private void addActionEvent(){
        back.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == back){
            dispose();
        }
    }
}
