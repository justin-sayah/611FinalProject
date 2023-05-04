package org.TradingSystem.model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
//
//public class ApproveAccountFrame extends JFrame {
//
//    private final JPanel container;
//    private final JLabel personIdLabel;
//    private final JLabel personNameLabel;
//    private final JTable accountsTable;
//    private final JButton approveButton;
//    private final JButton backButton;
//    private final String[] columns = {"Account Number"};
//    private final DefaultTableModel model;
//    private final ArrayList<JCheckBox> checkBoxes;
//
//    public ApproveAccountFrame(String personId, String personName, ArrayList<String> accounts) {
//        setTitle("Approve Accounts");
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
//        // Approve button panel
//        JPanel approveButtonPanel = new JPanel();
//        approveButton = new JButton("Approve");
//        approveButton.setEnabled(false);
//        approveButtonPanel.add(approveButton);
//        container.add(approveButtonPanel, BorderLayout.SOUTH);
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
//            checkBox.addActionListener(e -> approveButton.setEnabled(checkBox.isSelected()));
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
//        new ApproveAccountFrame("123456", "John Doe", accounts);
//    }
//
//}

    public class ApproveAccountFrame extends JFrame {

        private final Manager manager;
        private final JPanel container;
        private final JLabel personIdLabel;
        private final JLabel personNameLabel;
        private final JTable accountsTable;
        private final JButton approveButton;
        private final JButton backButton;
        private final String[] columns = {"Account Number", "Customer Name"};
        private final DefaultTableModel model;
        private final ArrayList<JCheckBox> checkBoxes;

        public ApproveAccountFrame(Manager manager) {
            this.manager = manager;
            setTitle("Approve Accounts");
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

            // Approve button panel
            JPanel approveButtonPanel = new JPanel();
            approveButton = new JButton("Approve");
            approveButton.setEnabled(false);
            approveButtonPanel.add(approveButton);
            container.add(approveButtonPanel, BorderLayout.SOUTH);


            // Back button panel
            JPanel backButtonPanel = new JPanel();
            backButton = new JButton("Back");
            backButtonPanel.add(backButton);
            container.add(backButtonPanel, BorderLayout.NORTH);

            // Create checkboxes for each pending account
            checkBoxes = new ArrayList<>();
            List<TradingAccount> pendingAccounts = TradingAccountDao.getInstance().getAllPending(manager.getID());

            for (TradingAccount account : pendingAccounts) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.addActionListener(e -> {
                    approveButton.setEnabled(checkBoxes.stream().anyMatch(JCheckBox::isSelected));
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

            approveButton.addActionListener(e -> {
                System.out.println("Approve button clicked!");
                List<Integer> selectedAccountNumbers = new ArrayList<>();
                for (int i = 0; i < checkBoxes.size(); i++) {
                    JCheckBox checkBox = checkBoxes.get(i);
                    if (checkBox.isSelected()) {
                        String accountNumberString = (String) model.getValueAt(i, 0);
                        int accountNumber = Integer.parseInt(accountNumberString);
                        selectedAccountNumbers.add(accountNumber);
                    }
                }

                Manager manager = Manager.getManager(getManagerId());
                for (int accountNumber : selectedAccountNumbers) {
                    TradingAccount account = manager.getPendingAccount(accountNumber, getPersonId());
                    if (account != null) {
                        manager.activateAccount(account);
                    }
                }

                dispose();
            });

            // Disable the approve button if no accounts are selected
            approveButton.setEnabled(false);

            // Enable the approve button if any accounts are selected
            accountsTable.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    approveButton.setEnabled(accountsTable.getSelectedRowCount() > 0);
                }
            });
        }

        private int getPersonId() {
            return manager.getID();
        }

        private int getManagerId() {
            return getPersonId();
        }

        public boolean activateAccount(int accountNumber){
            TradingAccount account = TradingAccountDao.getInstance().getPendingAccount(accountNumber, getManagerId());
            if (account != null) {
                TradingAccountDao.getInstance().deleteFromPending(account);
                TradingAccountDao.getInstance().addTradingAccount(account);
                return true;
            } else {
                return false;
            }
        }

        public static void main(String[] args) {
            // Create a new Manager
            Manager manager = new Manager(1, "John", "Doe", "username", "password", "1990-01-01", "123-45-6789");

            // Create and show the ApproveAccountFrame
            ApproveAccountFrame approveAccountFrame = new ApproveAccountFrame(manager);
        }



    }
