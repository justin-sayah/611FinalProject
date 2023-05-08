package org.TradingSystem.views;

import org.TradingSystem.model.Customer;
import org.TradingSystem.model.TradingAccount;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewBlockedAccounts extends JFrame implements ActionListener {

    private final JPanel container;
    private final JLabel customerName;
    private final JLabel customerLabel;
    private final JButton backButton;
    private final JButton applyButton;

    private final JTable blockedAccountsTable;


    private DefaultTableModel blockedAccountsTableModel;

    private final JLabel accountID;
    private final JLabel accountLabel;
    private final JPanel customerInfoPanel;
    private Customer customer;
    private JLabel blockAccountLabel;

    public ViewBlockedAccounts(Customer customer){
        this.customer = customer;
        System.out.println(customer);
        setVisible(true);
        setTitle("Blocked Accounts View Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(10,10);
        setSize(1000,800);
        setResizable(false);
        container = new JPanel();
        customerName = new JLabel(customer.getLastName()+","+customer.getFirstName());
        customerLabel = new JLabel("Customer Name: ");
        accountLabel = new JLabel("Customer ID: ");
        accountID = new JLabel(String.valueOf(customer.getID()));

        blockAccountLabel = new JLabel("BLOCK ACCOUNT CENTER",JLabel.CENTER);
        blockAccountLabel.setFont(new Font("Verdana", Font.PLAIN, 40));
        blockAccountLabel.setPreferredSize(new Dimension(1000,150));
        blockAccountLabel.setForeground(Color.red);
        blockAccountLabel.setOpaque(true);
        blockAccountLabel.setBackground(Color.blue);

        customerInfoPanel = new JPanel(new GridLayout(1,4));


        customerInfoPanel.add(accountLabel);
        customerInfoPanel.add(accountID);
        customerInfoPanel.add(customerLabel);
        customerInfoPanel.add(customerName);
        customerInfoPanel.setPreferredSize(new Dimension(600,20));


        backButton = new JButton("BACK");
        applyButton = new JButton("APPLY");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(applyButton);
        buttonPanel.add(backButton);
        JPanel buttonAll = new JPanel(new BorderLayout());
        buttonAll.add(buttonPanel,BorderLayout.NORTH);

        blockedAccountsTable = new JTable();

        blockedAccountsTableModel = new DefaultTableModel();
        blockedAccountsTableModel.addColumn("Account Number");
        blockedAccountsTableModel.addColumn("Balance");
        blockedAccountsTableModel.addColumn("RealizedPL");
        blockedAccountsTableModel.addColumn("UnrealizedPL");
        blockedAccountsTable.setModel(blockedAccountsTableModel);
        blockedAccountsTable.setPreferredScrollableViewportSize(new Dimension(300, 300));


        //List<TradingAccount> allBlockedAccounts = TradingAccount.getAllBlocked(customerId);
        List<TradingAccount> allblocked = TradingAccount.getAllBlocked();

        for (TradingAccount blockedAccount : allblocked) {
            Object[] rowData = new Object[]{
                    blockedAccount.getAccountNumber(),
                    blockedAccount.getBalance(),
                    blockedAccount.getRealizedProfitLoss(),
                    blockedAccount.getUnrealizedProfitLoss()
            };
            blockedAccountsTableModel.addRow(rowData);
        }


        container.setPreferredSize(new Dimension(1000,800));




        JPanel center = new JPanel(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(blockedAccountsTable);
        scrollPane.setPreferredSize(new Dimension(500,500));

        center.add(scrollPane, BorderLayout.CENTER);
        center.add(buttonAll,BorderLayout.EAST);
        container.add(blockAccountLabel);
        container.add(customerInfoPanel, BorderLayout.WEST);
        container.add(center,BorderLayout.CENTER);

        add(container);
        pack();

        setVisible(true);
        actionEvent();


    }


    public void actionEvent(){
        applyButton.addActionListener(this);
        backButton.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == backButton){
            new CustomerHomePage(customer);
            this.setVisible(false);
        }else if(e.getSource() == applyButton){
            int selectedRow = blockedAccountsTable.getSelectedRow();
            if (selectedRow != -1) {
                // Retrieve the selected row data
                Object accountNumber = blockedAccountsTable.getValueAt(selectedRow,0);
                Object balance = blockedAccountsTable.getValueAt(selectedRow, 1);
                new BlockedAccountPage(TradingAccount.getAccount((Integer) accountNumber));
                this.setVisible(false);
            }else {
                JOptionPane.showMessageDialog(this, "You have not selected any account.");

            }
        }
    }
}
