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
    private final String[] columns = {"Account Number"};
    private final DefaultTableModel model;
    private final ArrayList<JCheckBox> checkBoxes;

    public ViewPendingAccountFrame(String personId, String personName, List<TradingAccount> accounts) {
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
        personIdLabel = new JLabel("Person ID: " + personId);
        personNameLabel = new JLabel("Person Name: " + personName);
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

        // Create checkboxes for each account
        checkBoxes = new ArrayList<>();
        for (TradingAccount account : accounts) {
            JCheckBox checkBox = new JCheckBox();
            checkBoxes.add(checkBox);

            Object[] row = {
                    account.getAccountNumber()
            };
            model.addRow(row);
        }

        add(container);

        setVisible(true);
    }

    public static void main(String[] args) {
        List<TradingAccount> accounts = TradingAccountDao.getInstance().getAllPending(123456);
        new ViewPendingAccountFrame("123456", "John Doe", accounts);
    }

}


