package org.TradingSystem.model;

import javax.swing.*;
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
    private final JTextField buyQuantity;
    private final JTable stockTable;
    private StockDao stockDao;
    private DefaultTableModel stockTableModel;
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
        buyQuantityLabel = new JLabel("Enter the Quantity: ");
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



        List<Stock> allStocks = stockDao.getAllStocks();
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

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(backButton);
        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);
        container.add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(buyQuantityLabel);
        bottomPanel.add(buyQuantity);
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

    }

    public void setLocationAndSize(){
        accountIDLabel.setBounds(0,0,110,40);
        accountID.setBounds(120, 0, 100, 40);
        customerName.setBounds(270,0,100,40);
        customerLabel.setBounds(150, 0, 110, 40);
        stockTable.setBounds(250,150,500,500);
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
            new CustomerAccountView(tradingAccount.getAccountNumber(),name,tradingAccount.getPersonId());
            this.setVisible(false);
        }else if(e.getSource() == buyButton){
            int selectedRow = stockTable.getSelectedRow();
            if (selectedRow != -1) {
                // Retrieve the selected row data
                Object stockPrice = stockTable.getValueAt(selectedRow, 3);
                if(buyQuantity==null){
                    JOptionPane.showMessageDialog(this,"You have not entered any quantity");
                }else{
                    try {
                        int quantity = Integer.parseInt(buyQuantity.getText());
                        double initBalance = tradingAccountDao.getAccount(tradingAccount.getAccountNumber(),tradingAccount.getPersonId()).getBalance();
                        if((quantity*(double)stockPrice)<initBalance){
                            JOptionPane.showMessageDialog(this,"You have successfully bought the stock");
                            double balanceLeft = initBalance-quantity*(double)stockPrice;
                            tradingAccount.setBalance(balanceLeft);
                            tradingAccountDao.update(tradingAccount);
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