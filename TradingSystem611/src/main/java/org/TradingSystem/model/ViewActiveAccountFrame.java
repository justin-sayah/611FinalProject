package org.TradingSystem.model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewActiveAccountFrame extends JFrame {

    private final JPanel container;
    private final JLabel personIdLabel;
    private final JLabel personNameLabel;
    private final JTable accountsTable;
    private final JButton backButton;
    private final String[] columns = {"Account Number", "Customer Name"};
    private final DefaultTableModel model;

    public ViewActiveAccountFrame(Manager manager) {
        setTitle("View Active Accounts");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        container = new JPanel();
        container.setLayout(new BorderLayout());

        // Person information panel
        JPanel personInfoPanel = new JPanel();
        personInfoPanel.setLayout(new GridLayout(2, 1));
        personIdLabel = new JLabel("Person ID: " + manager.getID());
        personNameLabel = new JLabel("Person Name: " + manager.getFirstName() + " " + manager.getLastName());
        personInfoPanel.add(personIdLabel);
        personInfoPanel.add(personNameLabel);
        container.add(personInfoPanel, BorderLayout.WEST);

        // Accounts table panel
        JPanel accountsPanel = new JPanel();
        accountsPanel.setLayout(new BorderLayout());
        model = new DefaultTableModel(columns, 0);
        accountsTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(accountsTable);
        accountsPanel.add(scrollPane, BorderLayout.CENTER);
        container.add(accountsPanel, BorderLayout.CENTER);

        // Back button panel
        JPanel backButtonPanel = new JPanel();
        backButton = new JButton("Back");
        backButtonPanel.add(backButton);
        container.add(backButtonPanel, BorderLayout.NORTH);

        for (TradingAccount account : manager.getAllActiveAccounts(manager.getID())) {
            Object[] row = {
                    account.getAccountNumber(),
                    Manager.getCustomer(account.getPersonId()).getLastName() + " " + Manager.getCustomer(account.getPersonId()).getFirstName(),
//                    account.getAccountType()
            };
            model.addRow(row);
        }

        add(container);

        setVisible(true);
    }


    public static void main(String[] args) {
        // Create a new Manager
        Manager manager = new Manager(1, "John", "Doe", "username", "password", "1990-01-01", "123-45-6789");
        new ViewActiveAccountFrame(manager);
    }

}

