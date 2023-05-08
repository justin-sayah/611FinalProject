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
    protected final JLabel balance;
    private BuyConfirmPopup buyPopup;

    private JTextField costLabel;
    private PeopleDao.StockDao stockDao;
    private DefaultTableModel stockTableModel;
    private DocumentListener quantityChangeListener;

    private TradingAccount tradingAccount;
    private CustomerAccountView customerAccountView;
    private JLabel customerBuyLabel;


    public BuyStockPage( CustomerAccountView customerAccountView,String name,TradingAccount tradingAccount) {
        this.customerAccountView = customerAccountView;

        this.tradingAccount = tradingAccount;
        setVisible(true);
        setTitle("Stock Buy Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(10,10);
        setSize(1000,800);
        setResizable(false);
        stockDao = PeopleDao.StockDao.getInstance();
        container = new JPanel();
        container.setPreferredSize(new Dimension(1000,800));
        accountID = new JLabel(String.valueOf(tradingAccount.getAccountNumber()));
        accountID.setFont(new Font("Verdana", Font.PLAIN, 20));
        accountIDLabel = new JLabel("Account Number: ");
        accountIDLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        customerName = new JLabel(name);
        customerName.setFont(new Font("Verdana", Font.PLAIN, 20));
        customerLabel = new JLabel("Customer Name: ");
        customerLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        balanceLabel = new JLabel("Balance: ");
        balanceLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        balance = new JLabel(String.valueOf(tradingAccount.getBalance()));
        balance.setFont(new Font("Verdana", Font.PLAIN, 20));
        buyQuantityLabel = new JLabel("Enter the Quantity: ");
        costLabel = new JTextField("");
        costLabel.setPreferredSize(new Dimension(150,20));
        buyQuantity = new JTextField();

        buyQuantity.setColumns(10);
        backButton = new JButton("BACK");
        backButton.setPreferredSize(new Dimension(100,40));
        buyButton = new JButton("BUY");
        buyButton.setPreferredSize(new Dimension(100,40));
        stockTable = new JTable();


        customerBuyLabel = new JLabel("CUSTOMER PURCHASE CENTER",JLabel.CENTER);
        customerBuyLabel.setFont(new Font("Verdana", Font.PLAIN, 40));
        customerBuyLabel.setForeground(Color.red);
        customerBuyLabel.setOpaque(true);
        customerBuyLabel.setBackground(Color.blue);
        customerBuyLabel.setPreferredSize(new Dimension(1000,200));


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


        //JPanel topPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new GridLayout(1,6));
        topPanel.add(accountIDLabel);
        topPanel.add(accountID);
        topPanel.add(customerLabel);
        topPanel.add(customerName);
        topPanel.add(balanceLabel);
        topPanel.add(balance);

        JPanel buttonPanel = new JPanel(new GridLayout(2,1));
        buttonPanel.add(buyButton);
        buttonPanel.add(backButton);

        JPanel buyPanel = new JPanel(new GridLayout(3,1));
        buyPanel.add(buyQuantityLabel);
        buyPanel.add(buyQuantity);
        buyPanel.add(costLabel);

        JPanel buyAndButtonPanel = new JPanel(new BorderLayout());

        buyAndButtonPanel.add(buyPanel, BorderLayout.NORTH);
        buyAndButtonPanel.add(buttonPanel, BorderLayout.SOUTH);


        JScrollPane scrollPane= new JScrollPane(stockTable);
        scrollPane.setPreferredSize(new Dimension(500,500));
        stockTable.setGridColor(Color.ORANGE);
        scrollPane.setFont(new Font("Verdana", Font.BOLD, 15));

        JPanel center = new JPanel(new BorderLayout());
        center.add(scrollPane, BorderLayout.CENTER);
        center.add(buyAndButtonPanel,BorderLayout.EAST);

        container.add(customerBuyLabel);
        container.add(topPanel, BorderLayout.WEST);
        container.add(center, BorderLayout.CENTER);

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
        buyQuantityLabel.setBounds(150,700,100,40);
        buyQuantity.setBounds(200,700,100,40);
        backButton.setBounds(878,0,100,40);
        buyButton.setBounds(350,700,100,40);

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
                                buyPopup = new BuyConfirmPopup(customerAccountView,this,(quantity*(double)stockPrice),tradingAccount.getBalance() ,tradingAccount, (int)(stockId),quantity,(double)stockPrice); // Create the deposit popup window if it's not already created
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
}
