package org.TradingSystem.views;

import org.TradingSystem.database.PeopleDao;
import org.TradingSystem.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewActiveAccountFrame extends JFrame {

    private final JPanel container;
    private final JLabel personIdLabel;
    private final JLabel personNameLabel;
    private final JTable accountsTable;
    private final JButton backButton;
    private final String[] columns = {"Account Number","Customer ID","Account Last Name", "Account First Name", "Account Type"};
    private final DefaultTableModel model;

    private final Manager manager;
    private PeopleDao peopleDao;

    public ViewActiveAccountFrame(Manager manager) {
        this.manager = manager;
        peopleDao = PeopleDao.getInstance();
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
        personIdLabel = new JLabel("Manager ID: " + manager.getID());
        personNameLabel = new JLabel("Manager Name: " + manager.getFirstName()+ manager.getLastName());
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
        backButton = new JButton("BACK");
        backButtonPanel.add(backButton);
        container.add(backButtonPanel, BorderLayout.EAST);

        for (TradingAccount account : TradingAccount.getAllActive()) {
            Customer customer = peopleDao.getCustomer(account.getPersonId());
            Object[] row = {
                    account.getAccountNumber(),
                    customer.getID(),
                    customer.getLastName(),
                    customer.getFirstName(),
                    "Trading Account",
            };
            model.addRow(row);
        }

        add(container);
        addActionListeners();
        setVisible(true);
    }

    private void addActionListeners() {
        backButton.addActionListener(e -> {
            new ManageAccountFrame(manager);
            this.setVisible(false);
        });
    }



}

