package org.TradingSystem.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ViewAccountsFrame extends JFrame implements ActionListener {


    private JTable accountTable;
    private final JPanel container;
    private JScrollPane scrollPane;
    private TradingAccountDao tDao;
    private final JButton back;
    private final JButton viewPositions;
    private final JButton viewTransactions;
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
        accountTable = new JTable(data,columnNames);
        accountTable.setRowHeight(40);
        accountTable.setPreferredScrollableViewportSize(new Dimension(300,300));
        scrollPane = new JScrollPane(accountTable);
        container.setLayout(new BorderLayout());
        back = new JButton("Back");
        viewPositions = new JButton("View Positions");
        viewTransactions = new JButton("View Transactions");

        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();

    }

    private void setLocationAndSize(){
        back.setBounds(450,500,100,40);
        viewPositions.setBounds(350,400,300,40);
        viewTransactions.setBounds(350,450,300,40);
    }

    private void addComponentsToContainer(){
        container.add(back);
        container.add(viewPositions);
        container.add(viewTransactions);
        container.add(scrollPane, BorderLayout.CENTER);
        add(container);
    }

    private void addActionEvent(){
        back.addActionListener(this);
        viewPositions.addActionListener(this);
        viewTransactions.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == back){
            dispose();
        }else if(e.getSource() == viewPositions){
            int row = accountTable.getSelectedRow();
            if(row < 0){
                JOptionPane.showMessageDialog(this, "Please choose a row!");
            }else {
                TradingAccount account = list.get(row);
                // View Positions of selected account
                new ViewPositionsFrame(account);
            }
        }else if(e.getSource() == viewPositions){
            int row = accountTable.getSelectedRow();
            if(row < 0){
                JOptionPane.showMessageDialog(this, "Please choose a row!");
            }else {
                TradingAccount account = list.get(row);
                // View Positions of selected account
                new TransactionHistoryFrame(account);
            }
        }
    }
}
