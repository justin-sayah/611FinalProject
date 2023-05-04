package org.TradingSystem.model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SellManageFrame extends JFrame implements ActionListener {

    private final JPanel container;
    private final JLabel accountID;
    private final JLabel accountIDLabel;
    private final JLabel customerName;
    private final JLabel customerLabel;
    private final JButton backButton;
    private final JButton sellButton;
    private final JLabel sellQuantityLabel;
    private final JTextField sellQuantity;
    private final JTable stockTable;
    private final DefaultTableModel stockTableModel;
    private final TradingAccount tradingAccount;
    private final TradingAccountDao tradingAccountDao;
    private final StockDao stockDao;
    private int customerId;


    private String name;
    private JScrollPane scrollPane;

    public SellManageFrame(String name, TradingAccount tradingAccount) {
        this.tradingAccount = tradingAccount;
        this.tradingAccountDao = new TradingAccountDao();
        this.stockDao = new StockDao();

        setTitle("Sell/Manage Stocks");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        container = new JPanel();
        accountID = new JLabel(String.valueOf(tradingAccount.getAccountNumber()));
        accountIDLabel = new JLabel("Account Number: ");
        customerName = new JLabel(name);
        customerLabel = new JLabel("Customer Name: ");
        sellQuantityLabel = new JLabel("Enter the Quantity: ");
        sellQuantity = new JTextField();

        sellQuantity.setColumns(10);

        backButton = new JButton("BACK");
        sellButton = new JButton("SELL");

        stockTableModel = new DefaultTableModel();
        stockTableModel.addColumn("Ticker");
        stockTableModel.addColumn("ID");
        stockTableModel.addColumn("Name");
        stockTableModel.addColumn("Price");
        stockTableModel.addColumn("Quantity");
        stockTable = new JTable(stockTableModel);
        stockTable.setPreferredScrollableViewportSize(new Dimension(500, 300));

        scrollPane = new JScrollPane(stockTable);

        List<Stock> allStocks = stockDao.getAllStocks();
        for (Stock stock : allStocks) {
            Object[] rowData = new Object[]{
                    stock.getTicker(),
                    stock.getSecurityId(),
                    stock.getName(),
                    stock.getPrice(),
            };
            stockTableModel.addRow(rowData);
        }

        setLocationAndSize();
        setLayoutManager();
        addComponentsToContainer();
        addActionEvent();

        setVisible(true);
    }

    public void setLocationAndSize() {
        accountIDLabel.setBounds(0, 0, 110, 40);
        accountID.setBounds(120, 0, 100, 40);
        customerName.setBounds(270, 0, 100, 40);
        customerLabel.setBounds(150, 0, 110, 40);
        stockTable.setBounds(250, 150, 500, 300);
        backButton.setBounds(878, 0, 100, 40);
        sellButton.setBounds(600, 600, 100, 40);

        // Add label and textfield for input quantity to sell
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sellQuantityLabel.setBounds(0, 0, 150, 40);
        sellQuantity.setBounds(150, 0, 100, 40);
        bottomPanel.add(sellQuantityLabel);
        bottomPanel.add(sellQuantity);
        bottomPanel.add(sellButton);
        container.add(bottomPanel, BorderLayout.SOUTH);

        container.add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.WEST);
        container.add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.EAST);
        add(container);
    }


    public void setLayoutManager() {
        container.setLayout(new BorderLayout());
    }

    public void addComponentsToContainer() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(accountIDLabel);
        leftPanel.add(accountID);
        leftPanel.add(customerLabel);
        leftPanel.add(customerName);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(backButton);
        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);

        container.add(topPanel, BorderLayout.NORTH);
        container.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel sellPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sellPanel.add(sellButton);

        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        quantityPanel.add(sellQuantityLabel);
        quantityPanel.add(sellQuantity);

        bottomPanel.add(sellPanel, BorderLayout.SOUTH);
        bottomPanel.add(quantityPanel, BorderLayout.CENTER);

        container.add(bottomPanel, BorderLayout.SOUTH);

        container.add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.WEST);
        container.add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.EAST);
        add(container);
    }


    private void addActionEvent() {
        backButton.addActionListener(this);
        sellButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            new CustomerAccountView(tradingAccount.getAccountNumber(), name, tradingAccount.getPersonId());
            this.setVisible(false);
        } else if (e.getSource() == sellButton) {
            int selectedRow = stockTable.getSelectedRow();
            if (selectedRow != -1) {
                // Retrieve the selected stock
                int stockId = (int) stockTable.getValueAt(selectedRow, 1);
                Stock selectedStock = stockDao.getStock(stockId);
                if (selectedStock == null) {
                    JOptionPane.showMessageDialog(this, "Failed to retrieve selected stock.");
                    return;
                }

                // Get the sell quantity from the user
                String sellQuantityStr = sellQuantity.getText();
                if (sellQuantityStr == null || sellQuantityStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a quantity to sell.");
                    return;
                }
                int sellQuantity;
                try {
                    sellQuantity = Integer.parseInt(sellQuantityStr);
                    if (sellQuantity <= 0) {
                        JOptionPane.showMessageDialog(this, "Sell quantity must be a positive integer.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Sell quantity must be a positive integer.");
                    return;
                }

                // Retrieve the position for the selected stock
                Position position = Position.getPosition(tradingAccount.getAccountNumber(), stockId);
                if (position == null) {
                    JOptionPane.showMessageDialog(this, "Failed to retrieve position for selected stock.");
                    return;
                }

                // Sell the shares and update the position and trading account
                position.sell(sellQuantity);
                Position.updatePosition(position);
                tradingAccountDao.update(tradingAccount);

//                // Add the sale transaction to the transaction history
//                Transaction sellTransaction = new Transaction(Transaction.Type.SELL, selectedStock.getSecurityId(), selectedStock.getName(), selectedStock.getTicker(), sellQuantity, selectedStock.getPrice(), selectedStock.getPrice() * sellQuantity);
//                tradingAccount.getTransactionHistory().add(sellTransaction);

                // Show confirmation message
                JOptionPane.showMessageDialog(this, String.format("Successfully sold %d shares of %s for $%.2f", sellQuantity, selectedStock.getName(), selectedStock.getPrice() * sellQuantity));

                // Refresh the stock table
                stockTableModel.setRowCount(0);
                List<Stock> allStocks = stockDao.getAllStocks();
                for (Stock stock : allStocks) {
                    Position p = Position.getPosition(tradingAccount.getAccountNumber(), stock.getSecurityId());
                    int shareCount = p != null ? p.getQuantity() : 0;
                    Object[] rowData = new Object[]{
                            stock.getTicker(),
                            stock.getSecurityId(),
                            stock.getName(),
                            stock.getPrice(),
                            shareCount
                    };
                    stockTableModel.addRow(rowData);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a stock to sell.");
            }


        }
    }

    public static void main(String[] args) {
        String name = "John Doe"; // replace with customer name
        TradingAccount tradingAccount = new TradingAccount(1234, 5678, 10000.0, 0.0, 0.0);
        SellManageFrame frame = new SellManageFrame(name, tradingAccount);
    }


}







