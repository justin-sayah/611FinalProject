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
    private final String[] columns = {"Account Number", "Customer Name"};
    private final DefaultTableModel model;
    private final ArrayList<JCheckBox> checkBoxes;

    public DeleteAccountFrame(Manager manager) {
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
                List<TradingAccount> activeAccounts = manager.getAllActiveAccounts(manager.getID());

                for (TradingAccount account : activeAccounts) {
                    JCheckBox checkBox = new JCheckBox();
                    checkBox.addActionListener(ev -> {
                        deleteButton.setEnabled(checkBoxes.stream().anyMatch(JCheckBox::isSelected));
                    });
                    checkBoxes.add(checkBox);

                    Object[] row = {
                            account.getAccountNumber(),
                            Manager.getCustomer(account.getPersonId()).getLastName() + " " + Manager.getCustomer(account.getPersonId()).getFirstName(),
                            // account.getAccountType()
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
            List<Integer> selectedAccountNumbers = new ArrayList<>();
            for (int i = 0; i < checkBoxes.size(); i++) {
                JCheckBox checkBox = checkBoxes.get(i);
                if (checkBox.isSelected()) {
                    String accountNumberString = (String) model.getValueAt(i, 0);
                    int accountNumber = Integer.parseInt(accountNumberString);
                    selectedAccountNumbers.add(accountNumber);
                }
            }

            for (int accountNumber : selectedAccountNumbers) {
                TradingAccount account = TradingAccountDao.getInstance().getAccount(accountNumber, getPersonId());

                if (account != null) {
                    manager.deleteAccount(account);
                    // Remove the deleted account from the table's model
                    for (int i = 0; i < model.getRowCount(); i++) {
                        String accountNumberString = (String) model.getValueAt(i, 0);
                        int tableAccountNumber = Integer.parseInt(accountNumberString);
                        if (tableAccountNumber == accountNumber) {
                            model.removeRow(i);
                            checkBoxes.remove(i);
                            break;
                        }
                    }
                }
            }

            // Disable the delete button
            deleteButton.setEnabled(false);
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






        private int getPersonId() {
                return manager.getID();
            }

            private int getManagerId() {
                return getPersonId();
            }

            public static void main(String[] args) {
                // Create a new Manager
                Manager manager = new Manager(1, "John", "Doe", "username", "password", "1990-01-01", "123-45-6789");
                DeleteAccountFrame deleteAccountFrame = new DeleteAccountFrame(manager);

            }
        }




