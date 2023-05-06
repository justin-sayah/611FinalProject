package org.TradingSystem.model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DeleteAccountFrame extends JFrame {

    private final Manager manager;
    private final JPanel container;
    private final JLabel personIdLabel;
    private final JLabel personNameLabel;
    private final JTable accountsTable;
    private final JButton deleteButton;
    private final JButton backButton;
    private final JButton refreshButton;
    private final String[] columns = {"Account Number","Customer ID","Account Last Name", "Account First Name", "Account Type"};
    private final DefaultTableModel model;
    private final ArrayList<JCheckBox> checkBoxes;
    private final PeopleDao peopleDao;

    public DeleteAccountFrame(Manager manager) {
        peopleDao = new PeopleDao();
        this.manager = manager;
        setTitle("Delete Accounts");
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

        // Delete button panel
        JPanel deleteButtonPanel = new JPanel();
        deleteButton = new JButton("Delete");
        deleteButton.setEnabled(false); // disable the delete button by default
        deleteButtonPanel.add(deleteButton);
        container.add(deleteButtonPanel, BorderLayout.SOUTH);

        // Refresh button panel
        JPanel refreshButtonPanel = new JPanel();
        refreshButton = new JButton("Refresh");
        refreshButtonPanel.add(refreshButton);
        container.add(refreshButtonPanel, BorderLayout.NORTH);

        // Back button panel
        JPanel backButtonPanel = new JPanel();
        backButton = new JButton("Back");
        backButtonPanel.add(backButton);
        container.add(backButtonPanel, BorderLayout.EAST);

        // Create checkboxes for each account
        checkBoxes = new ArrayList<>();



        List<TradingAccount> activeAccounts =TradingAccount.getAllActive();

        for (TradingAccount account : activeAccounts) {
            JCheckBox checkBox = new JCheckBox();
            checkBox.addActionListener(ev -> {
                deleteButton.setEnabled(checkBoxes.stream().anyMatch(JCheckBox::isSelected));
            });
            checkBoxes.add(checkBox);
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
            // Add action listener for the refresh button
            refreshButton.addActionListener(e -> {
                // Clear the current contents of the table
                model.setRowCount(0);

                // Create checkboxes for each account
                checkBoxes.clear();
                List<TradingAccount> activeAccounts =TradingAccount.getAllActive();

                for (TradingAccount account : activeAccounts) {
                    JCheckBox checkBox = new JCheckBox();
                    checkBox.addActionListener(ev -> {
                        deleteButton.setEnabled(checkBoxes.stream().anyMatch(JCheckBox::isSelected));
                    });
                    checkBoxes.add(checkBox);
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
            });

            // Add action listener for the back button
            backButton.addActionListener(e -> {
                System.out.println("Back button clicked!");
                ManageAccountFrame manageAccountFrame = new ManageAccountFrame(manager);
                manageAccountFrame.setVisible(true); // make the new ManageAccountFrame visible
                dispose(); // dispose the current frame
            });

            // Add action listener for the delete button
        deleteButton.addActionListener(e -> {

            int[] selectedRows = accountsTable.getSelectedRows();
            for (int i = 0; i < selectedRows.length; i++) {
                int accountNumber = (int) accountsTable.getValueAt(selectedRows[i], 0);
                int customerId = (int) accountsTable.getValueAt(selectedRows[i],1);
                System.out.println(accountNumber);
                if (deleteAccount(accountNumber,customerId)) {
                    System.out.println("Account " + accountNumber + " deleted successfully!");

                } else {
                    System.out.println("Failed to delete account " + accountNumber);
                }
            }
        });

        // Disable the delete button if no accounts are selected
            deleteButton.setEnabled(false);

            // Enable the delete button if any accounts are selected
            accountsTable.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    deleteButton.setEnabled(accountsTable.getSelectedRowCount() > 0);
                }
            });
        }

    public boolean deleteAccount(int accountNumber,int customerId){
        TradingAccount account = TradingAccount.getAccount(accountNumber);
        if (account != null) {
            TradingAccount.delete(account);
            return true;
        } else {
            return false;
        }
    }







        }




