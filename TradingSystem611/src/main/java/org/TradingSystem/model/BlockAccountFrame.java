package org.TradingSystem.model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BlockAccountFrame extends JFrame {

    private final JPanel container;
    private final JLabel personIdLabel;
    private final JLabel personNameLabel;
    private final JTable accountsTable;
    private final JButton blockButton;
    private final JButton backButton;
    private final JButton refreshButton;
    private final String[] columns = {"Account Number", "Customer ID", "Account Last Name", "Account First Name", "Account Type"};
    private final DefaultTableModel model;
    private final ArrayList<JCheckBox> checkBoxes;

    private final PeopleDao peopleDao;


    private final Manager manager;

    public BlockAccountFrame(Manager manager) {
        peopleDao = new PeopleDao();
        this.manager = manager;
        setTitle("Block Accounts");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        container = new JPanel();
        container.setLayout(new BorderLayout());



        // Person information panel
        JPanel personInfoPanel = new JPanel();
        personInfoPanel.setLayout(new GridLayout(2, 1));
        personIdLabel = new JLabel("Manager ID: " +manager.getID());
        personNameLabel = new JLabel("Manager Name: "+manager.getFirstName()+ manager.getLastName());
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
        blockButton = new JButton("BLOCK");
        blockButton.setEnabled(false); // disable the block button by default
        blockButtonPanel.add(blockButton);
        container.add(blockButtonPanel, BorderLayout.SOUTH);



        // Refresh button panel
        JPanel refreshButtonPanel = new JPanel();
        refreshButton = new JButton("REFRESH");
        refreshButtonPanel.add(refreshButton);
        container.add(refreshButtonPanel, BorderLayout.NORTH);

        // Back button panel
        JPanel backButtonPanel = new JPanel();
        backButton = new JButton("BACK");
        backButtonPanel.add(backButton);
        container.add(backButtonPanel, BorderLayout.EAST);

        // Create checkboxes for each account
        checkBoxes = new ArrayList<>();

        // Populate the table with active accounts
        List<TradingAccount> activeAccounts = TradingAccount.getAllActive();
        for (TradingAccount account : activeAccounts) {
            JCheckBox checkBox = new JCheckBox();
            checkBox.addActionListener(ev -> {
                blockButton.setEnabled(checkBoxes.stream().anyMatch(JCheckBox::isSelected));

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
            List<TradingAccount> accounts = TradingAccount.getAllActive();
            for (TradingAccount account : accounts) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.addActionListener(ev -> {
                    blockButton.setEnabled(checkBoxes.stream().anyMatch(JCheckBox::isSelected));
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
        });



        // Add action listener for the back button
        backButton.addActionListener(e -> {
            ManageAccountFrame manageAccountFrame = new ManageAccountFrame(manager);
            manageAccountFrame.setVisible(true); // make the new ManageAccountFrame visible
            dispose(); // dispose the current frame
        });

        // Add action listener for the block button
        // Add action listener for the block button
        blockButton.addActionListener(e -> {
            int[] selectedRows = accountsTable.getSelectedRows();
            for (int i = 0; i < selectedRows.length; i++) {
                int accountNumber = (int) accountsTable.getValueAt(selectedRows[i], 0);
                TradingAccount account = TradingAccount.getAccount(accountNumber);
                manager.blockAccount(account);
                TradingAccount.update(account);
            }
            refreshButton.doClick(); // refresh the table
        });

// Add action listener for the unblock button



        // Disable the block and unblock buttons if no accounts are selected
        blockButton.setEnabled(false);

        // Enable the block and unblock buttons if any accounts are selected
        accountsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                blockButton.setEnabled(accountsTable.getSelectedRowCount() > 0);
            }
        });
    }

//    public static void main(String[] args) {
//        Manager manager = new Manager(1, "John", "Doe", "username", "password", "1990-01-01", "123-45-6789");
//        BlockAccountFrame blockUnblockAccountFrame = new BlockAccountFrame(manager);
//        blockUnblockAccountFrame.setVisible(true);
//    }
}

