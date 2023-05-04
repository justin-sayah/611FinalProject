package org.TradingSystem.model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//public class DeleteAccountFrame extends JFrame {
//
//    private final JPanel container;
//    private final JLabel personIdLabel;
//    private final JLabel personNameLabel;
//    private final JTable accountsTable;
//    private final JButton deleteButton;
//    private final JButton backButton;
//    private final String[] columns = {"Account Number"};
//    private final DefaultTableModel model;
//    private final ArrayList<JCheckBox> checkBoxes;
//
//    public DeleteAccountFrame(String personId, String personName, ArrayList<String> accounts) {
//        setTitle("Delete Accounts");
//        setSize(800, 600);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setResizable(false);
//
//        container = new JPanel();
//        container.setLayout(new BorderLayout());
//
//        // Person information panel
//        JPanel personInfoPanel = new JPanel();
//        personInfoPanel.setLayout(new GridLayout(2, 1));
//        personIdLabel = new JLabel("Person ID: " + personId);
//        personNameLabel = new JLabel("Person Name: " + personName);
//        personInfoPanel.add(personIdLabel);
//        personInfoPanel.add(personNameLabel);
//        container.add(personInfoPanel, BorderLayout.WEST);
//
//
//        // Accounts table panel
//        JPanel accountsPanel = new JPanel();
//        accountsPanel.setLayout(new BorderLayout());
//        model = new DefaultTableModel(columns, 0);
//        accountsTable = new JTable(model);
//        JScrollPane scrollPane = new JScrollPane(accountsTable);
//        accountsPanel.add(scrollPane, BorderLayout.CENTER);
//        container.add(accountsPanel, BorderLayout.CENTER);
//
//        // Delete button panel
//        JPanel deleteButtonPanel = new JPanel();
//        deleteButton = new JButton("Delete");
//        deleteButton.setEnabled(false);
//        deleteButtonPanel.add(deleteButton);
//        container.add(deleteButtonPanel, BorderLayout.SOUTH);
//
//        // Back button panel
//        JPanel backButtonPanel = new JPanel();
//        backButton = new JButton("Back");
//        backButtonPanel.add(backButton);
//        container.add(backButtonPanel, BorderLayout.NORTH);
//
//        // Create checkboxes for each account
//        checkBoxes = new ArrayList<>();
//        for (String account : accounts) {
//            JCheckBox checkBox = new JCheckBox();
//            checkBox.addActionListener(e -> deleteButton.setEnabled(checkBox.isSelected()));
//            checkBoxes.add(checkBox);
//
//            Object[] row = {
//                    account
//            };
//            model.addRow(row);
//        }
//
//        add(container);
//
//        setVisible(true);
//    }
//
//
//    public static void main(String[] args) {
//        ArrayList<String> accounts = new ArrayList<>();
//        accounts.add("123456");
//        accounts.add("789012");
//        accounts.add("345678");
//        new DeleteAccountFrame("123456", "John Doe", accounts);
//    }
//
//}

    public class DeleteAccountFrame extends JFrame {

        private final Manager manager;
        private final JPanel container;
        private final JLabel personIdLabel;
        private final JLabel personNameLabel;
        private final JTable accountsTable;
        private final JButton deleteButton;
        private final JButton backButton;
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

            // Back button panel
            JPanel backButtonPanel = new JPanel();
            backButton = new JButton("Back");
            backButtonPanel.add(backButton);
            container.add(backButtonPanel, BorderLayout.NORTH);

            // Create checkboxes for each account
            checkBoxes = new ArrayList<>();
            List<TradingAccount> activeAccounts = manager.getAllActiveAccounts(manager.getID());

            for (TradingAccount account : activeAccounts) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.addActionListener(e -> {
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

            add(container);

            addActionListeners();
            setVisible(true);
        }

        private void addActionListeners() {
            backButton.addActionListener(e -> dispose());

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
                    }
                }

                dispose();
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




