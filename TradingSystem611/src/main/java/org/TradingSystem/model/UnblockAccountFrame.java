package org.TradingSystem.model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UnblockAccountFrame extends JFrame {
    private final JPanel container;
    private final JLabel personIdLabel;
    private final JLabel personNameLabel;
    private final JTable accountsTable;
    private final JButton unBlockButton;
    private final JButton backButton;
    private final JButton refreshButton;
    private final String[] columns = {"Account Number", "Customer ID", "Account Last Name", "Account First Name", "Account Type"};
    private final DefaultTableModel model;
    private final ArrayList<JCheckBox> checkBoxes;

    private final PeopleDao peopleDao;

    private final Manager manager;

    public UnblockAccountFrame(Manager manager) {
        peopleDao = new PeopleDao();
        this.manager = manager;
        setTitle("Unblock Accounts");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        container = new JPanel();
        container.setLayout(new BorderLayout());

        // Person information panel
        JPanel personInfoPanel = new JPanel();
        personInfoPanel.setLayout(new GridLayout(2, 1));
        personIdLabel = new JLabel("Customer ID: ");
        personNameLabel = new JLabel("Customer Name: ");
        personInfoPanel.add(personIdLabel);
        personInfoPanel.add(personNameLabel);
        container.add(personInfoPanel, BorderLayout.WEST);

        // Accounts table panel
        JPanel accountsPanel = new JPanel();
        accountsPanel.setLayout(new BorderLayout());
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };
        accountsTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(accountsTable);
        accountsPanel.add(scrollPane, BorderLayout.CENTER);
        container.add(accountsPanel, BorderLayout.CENTER);

        // Block button panel
        JPanel blockButtonPanel = new JPanel();
        unBlockButton = new JButton("Unblock");
        unBlockButton.setEnabled(false); // disable the block button by default
        blockButtonPanel.add(unBlockButton);
        container.add(blockButtonPanel, BorderLayout.SOUTH);


        // Refresh button panel
        JPanel refreshButtonPanel = new JPanel();
        refreshButton = new JButton("Refresh");
        refreshButtonPanel.add(refreshButton);
        container.add(refreshButtonPanel, BorderLayout.EAST);

        // Back button panel
        JPanel backButtonPanel = new JPanel();
        backButton = new JButton("Back");
        backButtonPanel.add(backButton);
        container.add(backButtonPanel, BorderLayout.WEST);

        // Create checkboxes for each account
        checkBoxes = new ArrayList<>();

        // Populate the table with active accounts
        List<TradingAccount> blockedAccounts = TradingAccount.getAllBlocked();
        for (TradingAccount account : blockedAccounts) {
            JCheckBox checkBox = new JCheckBox();
            checkBox.addActionListener(ev -> {
                unBlockButton.setEnabled(checkBoxes.stream().anyMatch(JCheckBox::isSelected));

            });
            checkBoxes.add(checkBox);
            Customer customer = peopleDao.getCustomer(account.getPersonId());
            Object[] row = {
                    account.getAccountNumber(),
                    customer.getID(),
                    customer.getLastName(),
                    customer.getFirstName(),
                    account.getType(),
            };
            model.addRow(row);
        }
        add(container);
        addActionListeners();
        setVisible(true);
    }

    private void addActionListeners() {
        // Add action listener for the refresh button
        refreshButton.addActionListener(e -> {
            // Clear the current contents of the table
            model.setRowCount(0);

            // Create checkboxes for each account
            checkBoxes.clear();

            // Populate the table with all accounts
            List<TradingAccount> accounts = TradingAccount.getAllBlocked();
            for (TradingAccount account : accounts) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.addActionListener(ev -> {
                    unBlockButton.setEnabled(checkBoxes.stream().anyMatch(JCheckBox::isSelected));
                });
                checkBoxes.add(checkBox);
                Customer customer = peopleDao.getCustomer(account.getPersonId());
                Object[] row = {
                        account.getAccountNumber(),
                        customer.getID(),
                        customer.getLastName(),
                        customer.getFirstName(),
                        account.getType(),
                };
                model.addRow(row);
//                if (!account.isBlocked()) {
//                    model.addRow(row);
//                } else {
//                    // Set the row as disabled if the account is blocked
//                    model.addRow(new Object[]{row[0], row[1], row[2], row[3], row[4]});
//                }
            }
        });
        backButton.addActionListener(e -> {
            ManageAccountFrame manageAccountFrame = new ManageAccountFrame(manager);
            manageAccountFrame.setVisible(true); // make the new ManageAccountFrame visible
            dispose(); // dispose the current frame
        });

        // Add action listener for the block button
        // Add action listener for the block button
        unBlockButton.addActionListener(e -> {
            int[] selectedRows = accountsTable.getSelectedRows();
            for (int i = 0; i < selectedRows.length; i++) {
                int accountNumber = (int) accountsTable.getValueAt(selectedRows[i], 0);
                TradingAccount account = TradingAccount.getAccount(accountNumber);
                manager.unblockAccount(account);
                TradingAccount.update(account);
            }
            refreshButton.doClick(); // refresh the table
        });

        unBlockButton.setEnabled(false);

        // Enable the block and unblock buttons if any accounts are selected
        accountsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                unBlockButton.setEnabled(accountsTable.getSelectedRowCount() > 0);
            }
        });

    }

//    public static void main(String[] args) {
//        Manager manager = new Manager(1, "John", "Doe", "username", "password", "1990-01-01", "123-45-6789");
//        unB = new BlockAccountFrame(manager);
//        blockUnblockAccountFrame.setVisible(true);
//    }
}

