package org.TradingSystem.views;

import org.TradingSystem.model.Customer;
import org.TradingSystem.model.TradingAccount;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CustomerHomePage extends JFrame implements ActionListener {

    private final JPanel container;
    private final JLabel customerNameLabel;
    private final JLabel customerHomeLabel;

    private final JButton blockedButton;

    private final JButton logoutButton;

    private final JButton applyButton;
    private final JTable accountList;
    private DefaultTableModel accountTableModel;
    private final JButton requestButton;
    private final JButton messageButton;
    //figure out how to get customerID
    private Customer customer;
    private MessagePopup messagePopup;

    public CustomerHomePage(Customer customer) {
        this.customer = customer;
        //initialize the tradingAccountDao instance

        //set up the JFrame
        setTitle("Customer Main Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(10,10);
        setSize(1000, 800);
        setResizable(false);
        container = new JPanel();
        container.setPreferredSize(new Dimension(1000,800));
        customerHomeLabel = new JLabel("CUSTOMER CENTER",JLabel.CENTER);
        customerHomeLabel.setFont(new Font("Verdana", Font.PLAIN, 50));
        customerHomeLabel.setForeground(Color.red);
        customerHomeLabel.setOpaque(true);
        customerHomeLabel.setBackground(Color.blue);
        customerHomeLabel.setPreferredSize(new Dimension(1000,180));
        messageButton = new JButton("VIEW MESSAGE");
        //set up label, message and button
        customerNameLabel = new JLabel("Welcome Back! " + customer.getLastName() + ", " + customer.getFirstName(),JLabel.LEFT);
        customerNameLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        customerNameLabel.setForeground(Color.BLACK);
        customerNameLabel.setOpaque(true);
        customerNameLabel.setBackground(Color.WHITE);
        customerNameLabel.setPreferredSize(new Dimension(1000,20));
        //messageTextArea.setPreferredSize(new Dimension(200,50));
        logoutButton = new JButton("LOGOUT");
        logoutButton.setFont(new Font("Verdana", Font.PLAIN, 25));
        applyButton = new JButton("APPLY");
        applyButton.setFont(new Font("Verdana", Font.PLAIN, 25));
        blockedButton = new JButton("BLOCKED");
        blockedButton.setFont(new Font("Verdana", Font.PLAIN, 25));

        requestButton = new JButton("REQUEST ACCOUNT");

        requestButton.setFont(new Font("Verdana", Font.PLAIN, 25));

        messageButton.setFont(new Font("Verdana", Font.PLAIN, 25));
        //set up account table
        accountList = new JTable();
        accountList.setFont(new Font("Verdana", Font.PLAIN, 15));
        accountList.setGridColor(Color.ORANGE);
        accountTableModel = new DefaultTableModel();

        accountTableModel.addColumn("Account Number");
        accountTableModel.addColumn("Balance");
        accountTableModel.addColumn("UnrealizedPL");
        accountTableModel.addColumn("RealizedPL");
        accountList.setModel(accountTableModel);
        accountList.setPreferredScrollableViewportSize(new Dimension(600, 300));


        //fetch the data from the dao and populate the table
        List<TradingAccount> allActiveAccounts = TradingAccount.getAllActive(customer.getID());
        for (TradingAccount account : allActiveAccounts) {
            Object[] rowData = new Object[]{
                    account.getAccountNumber(),
                    account.getBalance(),
                    account.getUnrealizedProfitLoss(),
                    account.getRealizedProfitLoss(),
            };
            accountTableModel.addRow(rowData);
        }

        JScrollPane scrollPane = new JScrollPane(accountList);
        scrollPane.setPreferredSize(new Dimension(650, 800));
        scrollPane.setFont(new Font("Verdana", Font.BOLD, 15));
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(customerHomeLabel,BorderLayout.NORTH);
        topPanel.add(customerNameLabel,BorderLayout.WEST);
        topPanel.setPreferredSize(new Dimension(1000,200));
        JPanel buttonPanel = new JPanel(new GridLayout(5,1));
        buttonPanel.setPreferredSize(new Dimension(350, 800));
        buttonPanel.add(messageButton);
        buttonPanel.add(requestButton);
        buttonPanel.add(blockedButton);
        buttonPanel.add(applyButton);
        buttonPanel.add(logoutButton);


        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.setPreferredSize(new Dimension(1000, 600));
        centerPanel.add(buttonPanel);
        centerPanel.add(scrollPane);
        centerPanel.setLocation(200,0);
        container.add(topPanel);
        container.add(centerPanel);




        add(container);
        pack();

        setVisible(true);
        addActionEvent();
    }

    private void addActionEvent() {
        logoutButton.addActionListener(this);
        applyButton.addActionListener(this);
        blockedButton.addActionListener(this);
        requestButton.addActionListener(this);
        messageButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutButton) {
            new LoginFrame();
            this.setVisible(false);
        } else if (e.getSource() == applyButton) {
            int selectedRow = accountList.getSelectedRow();
            if (selectedRow != -1) {
                // Retrieve the selected row data
                Object accountNumber = accountList.getValueAt(selectedRow, 0);
                Object balance = accountList.getValueAt(selectedRow, 1);

                // Perform actions with the selected data
                System.out.println("Selected Account Number: " + accountNumber);
                System.out.println("Selected Balance: " + balance);
                dispose();
                new CustomerAccountView(TradingAccount.getAccount((Integer) accountNumber));
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "You have not selected any account.");
            }
        } else if (e.getSource() == blockedButton) {
            new ViewBlockedAccounts(customer);
            this.setVisible(false);
        } else if (e.getSource() == requestButton) {
            TradingAccount.addPendingAccount(customer.getID(), "tradingAccount");
        }else if(e.getSource() == messageButton){

            messagePopup = new MessagePopup(this,customer); // Create the deposit popup window if it's not already created

            messagePopup.setVisible(true); // Show the deposit popup window

        }
    }
}



