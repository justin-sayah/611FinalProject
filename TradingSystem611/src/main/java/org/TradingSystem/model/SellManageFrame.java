package org.TradingSystem.model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SellManageFrame extends JFrame implements ActionListener {

    private final JPanel container;
    private final JLabel accountNumberLabel;
    private final JLabel accountNameLabel;
    private final JTable stocksTable;
    private final JButton backButton;
    private final JButton sellButton;
    private final String[] columns = {"Stock Name", "Sell Price", "Stock ID", "Quantity"};
    private final DefaultTableModel model;
    private final ArrayList<JCheckBox> checkBoxes;
    private final ArrayList<JTextField> quantityFields;
    private final ArrayList<String[]> stocks;

    SellManageFrame(String accountNumber, String accountName, ArrayList<String[]> stocks) {
        setTitle("Sell/Manage Stocks");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        container = new JPanel();
        container.setLayout(new BorderLayout());

        // Account information panel
        JPanel accountInfoPanel = new JPanel();
        accountInfoPanel.setLayout(new GridLayout(2, 1));
        accountNumberLabel = new JLabel("Account Number: " + accountNumber);
        accountNameLabel = new JLabel("Account Name: " + accountName);
        accountInfoPanel.add(accountNumberLabel);
        accountInfoPanel.add(accountNameLabel);
        container.add(accountInfoPanel, BorderLayout.NORTH);

        // Stocks table panel
        JPanel stocksPanel = new JPanel();
        stocksPanel.setLayout(new BorderLayout());
        model = new DefaultTableModel(columns, 0);
        stocksTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(stocksTable);
        stocksPanel.add(scrollPane, BorderLayout.CENTER);
        container.add(stocksPanel, BorderLayout.CENTER);

        // Sell button panel
        JPanel buttonPanel = new JPanel();
        sellButton = new JButton("Sell");
        sellButton.setEnabled(false);
        buttonPanel.add(sellButton);
        container.add(buttonPanel, BorderLayout.SOUTH);

        // Back button panel
        JPanel backButtonPanel = new JPanel();
        backButton = new JButton("Back");
        backButtonPanel.add(backButton);
        container.add(backButtonPanel, BorderLayout.NORTH);

        // Create checkboxes and quantity fields for each stock
        checkBoxes = new ArrayList<>();
        quantityFields = new ArrayList<>();
        this.stocks = stocks;
        for (String[] stock : stocks) {
            JCheckBox checkBox = new JCheckBox();
            checkBox.addActionListener(this);
            checkBoxes.add(checkBox);

            JTextField quantityField = new JTextField();
            quantityField.setColumns(5);
            quantityField.setEnabled(false);
            quantityFields.add(quantityField);


        }

        add(container);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Check if any checkboxes are selected
        boolean hasSelection = false;
        for (JCheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                hasSelection = true;
                break;
            }
        }

        // Enable sell button if there are checkboxes selected
        sellButton.setEnabled(hasSelection);

        // Enable quantity field for selected checkboxes
        for (int i = 0; i < checkBoxes.size(); i++) {
            JCheckBox checkBox = checkBoxes.get(i);
            JTextField quantityField = quantityFields.get(i);
            quantityField.setEnabled(checkBox.isSelected());
        }
    }

    public static void main(String[] args) {
        ArrayList<String[]> stocks = new ArrayList<>();
        stocks.add(new String[] {"AAPL", "150.0", "100", "110.0"});
        stocks.add(new String[] {"MSFT", "250.0", "50", "200.0"});
        stocks.add(new String[] {"TSLA", "800.0", "25", "700.0"});
        new SellManageFrame("123456", "John Doe", stocks);
    }

}