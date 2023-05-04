package org.TradingSystem.model;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CustomerHomePage extends JFrame implements ActionListener {

    private final JPanel container;
    private final JLabel customerNameLabel;
    private final JTextArea messageTextArea;
    private final JButton blockedButton;

    private final JButton logoutButton;

    private final JButton applyButton;
    private final JTable accountList;
    private DefaultTableModel accountTableModel;
    private PeopleDao peopleDao;
    private TradingAccountDao tradingAccountDao;
    //figure out how to get customerID
    Customer customer;
    private int customerId;

    public CustomerHomePage(String username, String password){
        //initialize the tradingAccountDao instance
        tradingAccountDao = new TradingAccountDao();
        peopleDao = new PeopleDao();
        customer = peopleDao.login(username,password);
        //set up the JFrame
        setTitle("Customer Main Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(10,10);
        setSize(1000,800);
        setResizable(false);

        container = new JPanel();
        container.setPreferredSize(new Dimension(1000,800));
        container.setLayout(new BorderLayout());

        //set up label, message and button
        customerNameLabel = new JLabel("Welcome Back! "+customer.getLastName() + ", " + customer.getFirstName());
        messageTextArea = new JTextArea();
        messageTextArea.setRows(5);
        messageTextArea.setColumns(50);
        //messageTextArea.setPreferredSize(new Dimension(200,50));
        logoutButton = new JButton("LOGOUT");
        applyButton = new JButton("APPLY");
        blockedButton = new JButton("BLOCKED");
//        applyButton.setBounds(750,700,40,20);
        applyButton.setPreferredSize(new Dimension(100,30));
        blockedButton.setPreferredSize(new Dimension(100,30));

        //set up account table
        accountList = new JTable();
        accountTableModel = new DefaultTableModel();
        accountTableModel.addColumn("Account Number");
        accountTableModel.addColumn("Balance");
        accountList.setModel(accountTableModel);
        accountList.setPreferredScrollableViewportSize(new Dimension(300, 300));


        //fetch the data from the dao and populate the table
        List<TradingAccount> allActiveAccounts = tradingAccountDao.getAllActive(customer.getID());
        for(TradingAccount account:allActiveAccounts){
            Object[] rowData = new Object[]{
                    account.getAccountNumber(),
                    account.getBalance(),
            };
            accountTableModel.addRow(rowData);
        }

        JPanel topPanel = new JPanel(new BorderLayout());

        topPanel.add(customerNameLabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(blockedButton);
        buttonPanel.add(logoutButton);

        topPanel.add(buttonPanel, BorderLayout.EAST);

        container.add(topPanel, BorderLayout.NORTH);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(applyButton,BorderLayout.EAST);

        //set up scroll panel for account list and add it to container
        JScrollPane scrollPane = new JScrollPane(accountList);
        scrollPane.setPreferredSize(new Dimension(300,300));
        container.add(scrollPane, BorderLayout.CENTER);
        container.add(bottomPanel, BorderLayout.SOUTH);
        container.add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.WEST);
        container.add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.EAST);
        add(container);
        pack();

        setVisible(true);
        addActionEvent();
    }

    private void addActionEvent() {
        logoutButton.addActionListener(this);
        applyButton.addActionListener(this);
        blockedButton.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==logoutButton){
            new LoginFrame();
            this.setVisible(false);
        }else if(e.getSource() == applyButton){
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
            }else{
                JOptionPane.showMessageDialog(this,"You have not selected any account.");
            }
        }else if(e.getSource() == blockedButton){
            new ViewBlockedAccounts(customer.getID());
            this.setVisible(false);
        }
    }

}
