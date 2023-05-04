package org.TradingSystem.model;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BuyStockPage extends JFrame implements ActionListener {

    private final JPanel container;
    private final JLabel accountID;
    private final JLabel accountIDLabel;
    private final JLabel customerName;
    private final JLabel customerLabel;
    private final JButton backButton;
    private final JButton buyButton;
    private final JLabel buyQuantityLabel;
    protected final JTextField buyQuantity;
    private final JTable stockTable;
    private final JLabel balanceLabel;
    private final JLabel balance;
    private BuyConfirmPopup buyPopup;

    private JTextField costLabel;
    private StockDao stockDao;
    private DefaultTableModel stockTableModel;
    private DocumentListener quantityChangeListener;
    private int accountNum;
    private String name;
    private int customerId;
    private TradingAccountDao tradingAccountDao;
    private TradingAccount tradingAccount;


    public BuyStockPage( String name,TradingAccount tradingAccount) {

        this.name = name;
        this.tradingAccount = tradingAccount;
        tradingAccountDao = new TradingAccountDao();
        setVisible(true);
        setTitle("Stock Buy Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(10,10);
        setSize(1000,800);
        setResizable(false);
        stockDao = new StockDao();
        container = new JPanel();
        accountID = new JLabel(String.valueOf(tradingAccount.getAccountNumber()));
        accountIDLabel = new JLabel("Account Number: ");
        customerName = new JLabel(name);
        customerLabel = new JLabel("Customer Name: ");
        balanceLabel = new JLabel("Balance: ");
        balance = new JLabel(String.valueOf(tradingAccount.getBalance()));
        buyQuantityLabel = new JLabel("Enter the Quantity: ");
        costLabel = new JTextField("");
        costLabel.setPreferredSize(new Dimension(200,20));
        buyQuantity = new JTextField();

        buyQuantity.setColumns(10);

        backButton = new JButton("BACK");
        buyButton = new JButton("BUY");
        stockTable = new JTable();

        stockTableModel = new DefaultTableModel();
        stockTableModel.addColumn("Ticker");
        stockTableModel.addColumn("ID");
        stockTableModel.addColumn("Name");
        stockTableModel.addColumn("Price");
        stockTable.setModel(stockTableModel);
        stockTable.setPreferredScrollableViewportSize(new Dimension(300, 300));



        List<Stock> allStocks = stockDao.getAllUnblockedStocks();
        for(Stock stock:allStocks){
            Object[] rowData = new Object[]{
                    stock.getTicker(),
                    stock.getSecurityId(),
                    stock.getName(),
                    stock.getPrice(),
            };
            stockTableModel.addRow(rowData);
        }

        container.setLayout(new BorderLayout());
        container.setPreferredSize(new Dimension(1000,800));
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(accountIDLabel);
        leftPanel.add(accountID);
        leftPanel.add(customerLabel);
        leftPanel.add(customerName);
        leftPanel.add(balanceLabel);
        leftPanel.add(balance);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(backButton);
        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);
        container.add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(buyQuantityLabel);
        bottomPanel.add(buyQuantity);
        bottomPanel.add(costLabel);
        bottomPanel.add(buyButton);
        JPanel bottomPanelWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanelWrapper.add(bottomPanel);
        JScrollPane scrollPane= new JScrollPane(stockTable);
        scrollPane.setPreferredSize(new Dimension(300,300));
        container.add(scrollPane, BorderLayout.CENTER);
        container.add(bottomPanelWrapper, BorderLayout.SOUTH);
        container.add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.WEST);
        container.add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.EAST);
        add(container);
        pack();




        setLocationAndSize();
        actionEvent();

        // Initialize the quantity change listener
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
        buyQuantity.getDocument().addDocumentListener(quantityChangeListener);

    }

    private void updateCostLabel() {
        try {
            int quantity = Integer.parseInt(buyQuantity.getText());
            int selectedRow = stockTable.getSelectedRow();
            if (selectedRow != -1) {
                Object stockPrice = stockTable.getValueAt(selectedRow, 3);
                double cost = quantity * (double) stockPrice;
                costLabel.setText("Your total cost is: $" + cost);
            }
        } catch (NumberFormatException ignored) {
        }
    }

    public void setLocationAndSize(){
        accountIDLabel.setBounds(0,0,110,40);
        accountID.setBounds(120, 0, 100, 40);
        customerName.setBounds(270,0,100,40);
        customerLabel.setBounds(150, 0, 110, 40);
        stockTable.setBounds(250,150,500,500);
        costLabel.setBounds(500,700,200,40);
        buyQuantityLabel.setBounds(200,700,100,40);
        buyQuantity.setBounds(350,700,100,40);
        backButton.setBounds(878,0,100,40);
        buyButton.setBounds(600,700,100,40);

    }



    public void actionEvent(){
        buyButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backButton){
            new CustomerAccountView(tradingAccount);
            this.setVisible(false);
        }else if(e.getSource() == buyButton){
            int selectedRow = stockTable.getSelectedRow();
            if (selectedRow != -1) {
                // Retrieve the selected row data
                Object stockId = stockTable.getValueAt(selectedRow,1);
                Object stockPrice = stockTable.getValueAt(selectedRow, 3);
                if(buyQuantity==null){
                    JOptionPane.showMessageDialog(this,"You have not entered any quantity");
                }else{
                    try {
                        int quantity = Integer.parseInt(buyQuantity.getText());
                        int stockId_num =(int)(stockId);
                        //double initBalance = tradingAccountDao.getAccount(tradingAccount.getAccountNumber(),tradingAccount.getPersonId()).getBalance();
                        if(tradingAccount.getBalance() >= Market.getInstance().getStock(stockId_num).getPrice() * quantity){
                            if ( buyPopup == null) {
                                buyPopup = new BuyConfirmPopup(this,(quantity*(double)stockPrice),tradingAccount.getBalance() ,tradingAccount, (int)(stockId),quantity,(double)stockPrice); // Create the deposit popup window if it's not already created
                            }
                            buyPopup.setVisible(true); // Show the deposit popup window

//
                        }else{
                            JOptionPane.showMessageDialog(this,"You do not have enough balance");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a valid number.");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(this,"You have not selected any stock");
            }


        }
    }
//    public static void main(String[] args){
//        BuyStockPage buyStockPage = new BuyStockPage();
//        buyStockPage.setVisible(true);
//    }
}
