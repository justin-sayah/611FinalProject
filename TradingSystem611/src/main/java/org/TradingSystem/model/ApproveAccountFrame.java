package org.TradingSystem.model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ApproveAccountFrame extends JFrame {

    private final JPanel container;
    private final JLabel personIdLabel;
    private final JLabel personNameLabel;
    private final JTable accountsTable;
    private final JButton approveButton;
    private final JButton backButton;
    private final String[] columns = {"Account Number"};
    private final DefaultTableModel model;
    private final ArrayList<JCheckBox> checkBoxes;

    public ApproveAccountFrame(String personId, String personName, ArrayList<String> accounts) {
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

        // Create checkboxes for each account
        checkBoxes = new ArrayList<>();
        for (String account : accounts) {
            JCheckBox checkBox = new JCheckBox();
            checkBox.addActionListener(e -> approveButton.setEnabled(checkBox.isSelected()));
            checkBoxes.add(checkBox);

            Object[] row = {
                    account
            };
            model.addRow(row);
        }

        add(container);

        setVisible(true);
    }


    public static void main(String[] args) {
        ArrayList<String> accounts = new ArrayList<>();
        accounts.add("123456");
        accounts.add("789012");
        accounts.add("345678");
        new ApproveAccountFrame("123456", "John Doe", accounts);
    }

}
