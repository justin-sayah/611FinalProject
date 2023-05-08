package org.TradingSystem.views;

import org.TradingSystem.database.PeopleDao;
import org.TradingSystem.model.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BlockSellPage extends JFrame implements ActionListener{


        private final JPanel container;
        private final JLabel accountID;
        private final JLabel accountIDLabel;
        private final JLabel customerName;
        private final JLabel customerLabel;
        private final JButton backButton;
        private final JButton sellButton;
        private final JButton viewTransactions;
        private final JLabel sellQuantityLabel;
        private final JTextField sellQuantity;
        private final JTable stockTable;
        private final DefaultTableModel stockTableModel;
        private final TradingAccount tradingAccount;
        private final PeopleDao.StockDao stockDao;
        private ViewBlockSellStockTransaction viewBlockSellStockTransaction;
        private JButton refreshButton;

        private JScrollPane scrollPane;
        private DocumentListener quantityChangeListener;
        private JTextField costLabel;
        private JLabel customerSellLabel;
        private int stockId;
        private Customer customer;

        public BlockSellPage(String name, TradingAccount tradingAccount) {
            customer = PeopleDao.getInstance().getCustomer(tradingAccount.getPersonId());
            this.tradingAccount = tradingAccount;
            this.stockDao = PeopleDao.StockDao.getInstance();

            setTitle("Sell/Manage Stocks");
            setSize(1000, 800);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            setVisible(true);

            container = new JPanel();
            container.setPreferredSize(new Dimension(1000,800));
            customerSellLabel = new JLabel("CUSTOMER SELL CENTER",JLabel.CENTER);
            customerSellLabel.setFont(new Font("Verdana", Font.PLAIN, 40));
            customerSellLabel.setForeground(Color.red);
            customerSellLabel.setOpaque(true);
            customerSellLabel.setBackground(Color.blue);
            customerSellLabel.setPreferredSize(new Dimension(1000,200));
            backButton = new JButton("BACK");
            sellButton = new JButton("SELL");
            viewTransactions = new JButton("VIEW TRANSACTIONS");
            refreshButton = new JButton("REFRESH");
            costLabel = new JTextField();



            JPanel topPanel = new JPanel(new GridLayout(1,4));
            topPanel.setPreferredSize(new Dimension(1000,40));
            accountIDLabel = new JLabel("Account Number: ");
            accountIDLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
            topPanel.add(accountIDLabel);
            accountID = new JLabel(String.valueOf(tradingAccount.getAccountNumber()));
            accountID.setFont(new Font("Verdana", Font.PLAIN, 15));
            topPanel.add(accountID);
            customerLabel = new JLabel("Customer Name: ");
            customerLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
            topPanel.add(customerLabel);
            customerName = new JLabel(name);
            customerName.setFont(new Font("Verdana", Font.PLAIN, 15));
            topPanel.add(customerName);


            JPanel buttonPanel = new JPanel(new GridLayout(4,1));
            buttonPanel.add(viewTransactions);
            buttonPanel.add(sellButton);
            buttonPanel.add(refreshButton);
            buttonPanel.add(backButton);




            sellQuantityLabel = new JLabel("Enter the Quantity: ");
            sellQuantity = new JTextField();
            sellQuantity.setPreferredSize(new Dimension(150,40));
            costLabel.setPreferredSize(new Dimension(150,40));
            JPanel sellPanel = new JPanel(new GridLayout(3,1));

            sellPanel.add(sellQuantityLabel);
            sellPanel.add(sellQuantity);
            sellPanel.add(costLabel);



            JPanel rightPanel = new JPanel(new BorderLayout());
            stockTableModel = new DefaultTableModel();
            stockTableModel.addColumn("Ticker");
            stockTableModel.addColumn("ID");
            stockTableModel.addColumn("Name");
            stockTableModel.addColumn("Price");
            stockTableModel.addColumn("Quantity");
            stockTable = new JTable(stockTableModel);
            stockTable.setFont(new Font("Verdana", Font.PLAIN, 15));
            stockTable.setGridColor(Color.ORANGE);
            scrollPane = new JScrollPane(stockTable);
            scrollPane.setPreferredSize(new Dimension(500,500));
            scrollPane.setFont(new Font("Verdana", Font.BOLD, 15));
            rightPanel.add(scrollPane);

            java.util.List<Position> positionList = Position.getAllPositions(tradingAccount.getAccountNumber());
            for (Position position1 : positionList) {
                Stock stock = stockDao.getStock(position1.getSecurityId());
                Object[] rowData = new Object[]{
                        stock.getTicker(),
                        stock.getSecurityId(),
                        stock.getName(),
                        stock.getPrice(),
                        position1.getQuantity(),

                };
                stockTableModel.addRow(rowData);
            }



            JPanel sellAndButtonPanel = new JPanel(new BorderLayout());
            sellAndButtonPanel.add(sellPanel, BorderLayout.NORTH);
            sellAndButtonPanel.add(buttonPanel, BorderLayout.CENTER);

// Create a new panel for the right panel and the sell/button panel
            JPanel center = new JPanel(new BorderLayout());
            center.add(rightPanel, BorderLayout.CENTER);
            center.add(sellAndButtonPanel, BorderLayout.EAST);

            container.add(customerSellLabel);
            container.add(topPanel,BorderLayout.WEST);
            container.add(center, BorderLayout.CENTER);
            add(container);
            pack();
            addActionEvent();

            setVisible(true);

            quantityChangeListener = new DocumentListener() {

                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateCostLabel();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    updateCostLabel();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    updateCostLabel();
                }
            };

            // Add the quantity change listener to the buyQuantity field
            sellQuantity.getDocument().addDocumentListener(quantityChangeListener);

        }









        private void updateCostLabel() {
            try {
                int quantity = Integer.parseInt(sellQuantity.getText());
                int selectedRow = stockTable.getSelectedRow();
                if (selectedRow != -1) {
                    Object stockPrice = stockTable.getValueAt(selectedRow, 3);
                    double cost = quantity * (double) stockPrice;
                    costLabel.setText("Your total value is: $" + cost);
                }
            } catch (NumberFormatException ignored) {
            }
        }

        private void addActionEvent() {
            backButton.addActionListener(this);
            sellButton.addActionListener(this);
            refreshButton.addActionListener(this);
            viewTransactions.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == backButton) {
                new CustomerHomePage(customer);
                this.setVisible(false);
                dispose();
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


                    // Show confirmation message
                    JOptionPane.showMessageDialog(this, String.format("Successfully sold %d shares of %s for $%.2f", sellQuantity, selectedStock.getName(), selectedStock.getPrice() * sellQuantity));

                    // Refresh the stock table
                    stockTableModel.setRowCount(0);
                    java.util.List<Position> positionList = Position.getAllPositions(tradingAccount.getAccountNumber());
                    for (Position position1 : positionList) {
                        Stock stock = stockDao.getStock(position1.getSecurityId());
                        Object[] rowData = new Object[]{
                                stock.getTicker(),
                                stock.getSecurityId(),
                                stock.getName(),
                                stock.getPrice(),
                                position1.getQuantity(),

                        };
                        stockTableModel.addRow(rowData);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a stock to sell.");
                }


            } else if (e.getSource() == refreshButton) {
                // Refresh the stock table
                stockTableModel.setRowCount(0);

                List<Position> positionList = Position.getAllPositions(tradingAccount.getAccountNumber());
                //List<Stock> allStocks = stockDao.getAllStocks();
                for (Position position1 : positionList) {
//                    Position p = Position.getPosition(tradingAccount.getAccountNumber(), stock.getSecurityId());
                    // int shareCount = position1 != null ? position1.getQuantity() : 0;
                    Stock stock = stockDao.getStock(position1.getSecurityId());
                    Object[] rowData = new Object[]{
                            stock.getTicker(),
                            stock.getSecurityId(),
                            stock.getName(),
                            stock.getPrice(),
                            position1.getQuantity(),

                    };
                    stockTableModel.addRow(rowData);
                }
            } else if (e.getSource() == viewTransactions) {
                int selectedRow = stockTable.getSelectedRow();

                if (selectedRow != -1) {
                    // Retrieve the selected stock
                    stockId = (int) stockTable.getValueAt(selectedRow, 1);
                    Stock selectedStock = stockDao.getStock(stockId);
                    if (selectedStock == null) {
                        JOptionPane.showMessageDialog(this, "Failed to retrieve selected stock.");
                        return;
                    }

                }
                if(viewBlockSellStockTransaction == null){
                    viewBlockSellStockTransaction = new ViewBlockSellStockTransaction(this,stockId);
                }
                viewBlockSellStockTransaction.setVisible(true);
            }
        }











}
