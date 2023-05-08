package org.TradingSystem.views;

import org.TradingSystem.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewAccountsFrame extends JFrame implements ActionListener {


    private JTable accountTable;
    private final JPanel container;
    private JScrollPane scrollPane;
    private final JButton back;
    private final JButton viewPositions;
    private final JButton viewTransactions;
    private List<TradingAccount> list;
    private Manager manager;
    private JLabel customerAccountLabel;
    private JPanel customerInfoPanel;
    private  JPanel topPanel;
    private  JPanel bottomPanel;
    private  JPanel buttonPanel;

    public ViewAccountsFrame(Manager manager, Customer customer, List<TradingAccount> list) {
        this.manager = manager;
        setTitle("Customer Accounts");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(10,10);
        setSize(1000,800);
        setVisible(true);
        setResizable(false);
        container = new JPanel();
        container.setPreferredSize(new Dimension(1000,800));


        topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(1000,170));
        customerInfoPanel = new JPanel(new GridLayout(1,4));
        customerInfoPanel.setPreferredSize(new Dimension(1000,20));
        customerAccountLabel = new JLabel("ACCOUNTS CENTER",JLabel.CENTER);
        customerAccountLabel.setFont(new Font("Verdana", Font.PLAIN, 50));
        customerAccountLabel.setPreferredSize(new Dimension(1000,150));
        customerAccountLabel.setForeground(Color.red);
        customerAccountLabel.setOpaque(true);
        customerAccountLabel.setBackground(Color.blue);

        buttonPanel = new JPanel(new GridLayout(3,1));
        bottomPanel = new JPanel(new GridLayout(1,2));
        bottomPanel.setPreferredSize(new Dimension(1000,550));


        Object[] columnNames = {"Account Number", "CustomerID","Type","Balance", "RealizedPL"};
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
        back = new JButton("BACK");
        back.setFont(new Font("Verdana", Font.PLAIN, 20));
        viewPositions = new JButton("VIEW POSITIONS");
        viewPositions.setFont(new Font("Verdana", Font.PLAIN, 20));
        viewTransactions = new JButton("VIEW TRANSACTIONS");
        viewTransactions.setFont(new Font("Verdana", Font.PLAIN, 20));

        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();

    }

    private void setLocationAndSize(){

        topPanel.add(customerAccountLabel);
        buttonPanel.add(viewPositions);
        buttonPanel.add(viewTransactions);
        buttonPanel.add(back);

        bottomPanel.add(buttonPanel);
        bottomPanel.add(scrollPane);
    }

    private void addComponentsToContainer(){
        container.add(topPanel);
        container.add(bottomPanel);
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
            new CustomerInformationFrame(manager);
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
        }else if(e.getSource() == viewTransactions){
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
