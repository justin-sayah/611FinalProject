package org.TradingSystem.model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ViewPendingAccountFrame extends JFrame {

    private final JPanel container;
    private final JLabel personIdLabel;
    private final JLabel personNameLabel;
    private final JTable accountsTable;
    private final JButton backButton;
    private final String[] columns = {"Account Number","Customer ID","Account Last Name", "Account First Name", "Account Type"};
    private final DefaultTableModel model;
    private final ArrayList<JCheckBox> checkBoxes;

    private final Manager manager;
    private final PeopleDao peopleDao;


    public ViewPendingAccountFrame(Manager manager) {
        this.manager = manager;
        peopleDao = new PeopleDao();
        setTitle("View Pending Accounts");
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

        // Create checkboxes for each account
        checkBoxes = new ArrayList<>();

        //list of all pending accounts
        List<TradingAccount> accounts = TradingAccountDao.getInstance().getAllPending();
        for (TradingAccount account : accounts) {
            JCheckBox checkBox = new JCheckBox();
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
        backButton.addActionListener(e -> {
            dispose();
            new ManageAccountFrame(manager);
        });
    }

//
//    public static void main(String[] args) {
//        Manager manager = new Manager(1, "John", "Doe", "username", "password", "1990-01-01", "123-45-6789");
//        new ViewPendingAccountFrame(manager);
//    }

}



