package org.TradingSystem.model;
import java.util.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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

    private int accountNum;
    private String name;
    private int customerId;
    private TradingAccountDao tradingAccountDao;
    private TradingAccount tradingAccount;
    private Customer customer;

    public ViewBlockedAccounts(Customer customer){
        this.customer = customer;
        System.out.println(customer);
        tradingAccountDao = new TradingAccountDao();
        setVisible(true);
        setTitle("Blocked Accounts View Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(10,10);
        setSize(1000,800);
        setResizable(false);
        container = new JPanel();
        customerName = new JLabel(customer.getLastName()+","+customer.getLastName());
        customerLabel = new JLabel("Customer Name: ");

        backButton = new JButton("BACK");
        applyButton = new JButton("APPLY");
        blockedAccountsTable = new JTable();

        blockedAccountsTableModel = new DefaultTableModel();
        blockedAccountsTableModel.addColumn("Account Number");
        blockedAccountsTableModel.addColumn("Balance");
        blockedAccountsTable.setModel(blockedAccountsTableModel);
        blockedAccountsTable.setPreferredScrollableViewportSize(new Dimension(300, 300));


        //List<TradingAccount> allBlockedAccounts = TradingAccount.getAllBlocked(customerId);
        List<TradingAccount> allblocked = TradingAccount.getAllBlocked();

        for (TradingAccount blockedAccount : allblocked) {
            Object[] rowData = new Object[]{
                    blockedAccount.getAccountNumber(),
                    blockedAccount.getBalance()
            };
            blockedAccountsTableModel.addRow(rowData);
        }

        container.setLayout(new BorderLayout());
        container.setPreferredSize(new Dimension(1000,800));
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(customerLabel);
        leftPanel.add(customerName);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(backButton);
        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);
        container.add(topPanel, BorderLayout.NORTH);


        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(applyButton,BorderLayout.EAST);


        JScrollPane scrollPane = new JScrollPane(blockedAccountsTable);
        scrollPane.setPreferredSize(new Dimension(300,300));

        container.add(scrollPane, BorderLayout.CENTER);
        container.add(bottomPanel, BorderLayout.SOUTH);
        container.add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.WEST);
        container.add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.EAST);
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
