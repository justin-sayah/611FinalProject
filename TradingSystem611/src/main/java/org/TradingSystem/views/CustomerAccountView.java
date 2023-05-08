package org.TradingSystem.views;

import org.TradingSystem.model.Customer;
import org.TradingSystem.model.TradingAccount;
import org.TradingSystem.views.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerAccountView extends JFrame implements ActionListener {
    //labels declarations
    private final JPanel container;
    private final JLabel customerNameLabel;
    private final JLabel accountIDLabel;

    private final JButton backButton;
    private final JButton manageButton;
    private final JButton buyButton;

    private final JButton transacButton;

    private final JButton withdrawButton;
    private final JButton depositButton;

    private final JLabel balanceLabel;
    protected final JLabel balance;
    private final JLabel realizedPLLabel;
    private final JLabel realizedPL;
    private final JLabel unrealizedPLLabel;
    private final JLabel unrealizedPL;
    private final JLabel accountID;
    private final JLabel nameLabel;
    private Customer customer;
    private TradingAccount tradingAccount;

    private DepositPopup depositPopup;

    private WithdrawPopup withdrawPopup;
    private final JLabel customerAccountLabel;
    private final JPanel buttonPanel;
    private final JPanel bottomPanel;
    private final JPanel infoPanel;
    private final JPanel customerInfoPanel;
    private final JPanel topPanel;



    public CustomerAccountView(TradingAccount tradingAccount) {

        this.tradingAccount = tradingAccount;
        tradingAccount.refresh();
        customer = Customer.getCustomer(tradingAccount.getPersonId());
        setVisible(true);
        setTitle("Customer Account Page");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        container = new JPanel();
        container.setPreferredSize(new Dimension(1000,800));
        topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(1000,170));
        customerInfoPanel = new JPanel(new GridLayout(1,4));
        customerInfoPanel.setPreferredSize(new Dimension(1000,20));
        customerAccountLabel = new JLabel("ACCOUNT CENTER",JLabel.CENTER);
        customerAccountLabel.setFont(new Font("Verdana", Font.PLAIN, 50));
        customerAccountLabel.setPreferredSize(new Dimension(1000,150));
        customerAccountLabel.setForeground(Color.red);
        customerAccountLabel.setOpaque(true);
        customerAccountLabel.setBackground(Color.blue);

        customerNameLabel = new JLabel(customer.getLastName()+", "+customer.getFirstName());
        customerNameLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
        accountIDLabel = new JLabel(String.valueOf(tradingAccount.getAccountNumber()));
        accountIDLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
        accountID = new JLabel("     Account Number: ");
        accountID.setFont(new Font("Verdana", Font.PLAIN, 15));
        nameLabel = new JLabel("Customer Name: ");
        nameLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
        backButton = new JButton("BACK");
        backButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        manageButton = new JButton("MANAGE/SELL");
        manageButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        buyButton = new JButton("PURCHASE");
        buyButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        transacButton = new JButton("TRANSACTIONS");
        transacButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        withdrawButton = new JButton("WITHDRAW");
        withdrawButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        depositButton = new JButton("DEPOSIT");
        depositButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        buttonPanel = new JPanel(new GridLayout(6,1));
        bottomPanel = new JPanel(new GridLayout(1,2));
        bottomPanel.setPreferredSize(new Dimension(1000,550));
        infoPanel = new JPanel(new GridLayout(3,2));
        balanceLabel = new JLabel("Balance",JLabel.CENTER);
        balanceLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        balance = new JLabel(String.valueOf(tradingAccount.getBalance()),JLabel.CENTER);
        balance.setFont(new Font("Verdana", Font.PLAIN, 20));
        realizedPLLabel = new JLabel("Realized Profit/Loss",JLabel.CENTER);
        realizedPLLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        realizedPL = new JLabel(String.valueOf(tradingAccount.getRealizedProfitLoss()),JLabel.CENTER);
        realizedPL.setFont(new Font("Verdana", Font.PLAIN, 20));
        unrealizedPLLabel = new JLabel("Unrealized Profit/Loss",JLabel.CENTER);
        unrealizedPLLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        unrealizedPL = new JLabel(String.valueOf(tradingAccount.getUnrealizedProfitLoss()),JLabel.CENTER);
        unrealizedPL.setFont(new Font("Verdana", Font.PLAIN, 20));

        addComponentsToContainer();
        setLocationAndSize();

        addActionEvent();


    }
//set locations for each label and button: x represents the column, y represents the row
    public void setLocationAndSize() {

        customerInfoPanel.add(accountID);
        customerInfoPanel.add(accountIDLabel);
        customerInfoPanel.add(nameLabel);
        customerInfoPanel.add(customerNameLabel);
        topPanel.add(customerAccountLabel,BorderLayout.NORTH);
        topPanel.add(customerInfoPanel,BorderLayout.SOUTH);

        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(manageButton);
        buttonPanel.add(buyButton);
        buttonPanel.add(transacButton);
        buttonPanel.add(backButton);

        infoPanel.add(balanceLabel);
        infoPanel.add(balance);
        infoPanel.add(realizedPLLabel);
        infoPanel.add(realizedPL);
        infoPanel.add(unrealizedPLLabel);
        infoPanel.add(unrealizedPL);

        bottomPanel.add(buttonPanel);
        bottomPanel.add(infoPanel);





    }



    public void addComponentsToContainer(){
       container.add(customerAccountLabel);
       container.add(topPanel);
       container.add(bottomPanel);
       add(container);
    }
    private void addActionEvent() {
        backButton.addActionListener(this);
        manageButton.addActionListener(this);
        buyButton.addActionListener(this);
        depositButton.addActionListener(this);
        transacButton.addActionListener(this);
        withdrawButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backButton){
            new CustomerHomePage(customer);
            this.setVisible(false);
        }else if(e.getSource() == transacButton){
            new TransactionHistoryFrame(tradingAccount);
            this.setVisible(true);
        }else if(e.getSource() == depositButton){ //call the deposit popup window
            if (depositPopup == null) {
                depositPopup = new DepositPopup(this,tradingAccount); // Create the deposit popup window if it's not already created
            }
            depositPopup.setVisible(true); // Show the deposit popup window
        }else if(e.getSource() == withdrawButton){ //call the withdraw popup window
            if (withdrawPopup == null) {
                withdrawPopup = new WithdrawPopup(this,tradingAccount); // Create the deposit popup window if it's not already created
            }
            withdrawPopup.setVisible(true); // Show the deposit popup window
        }else if(e.getSource() == manageButton){
            //TODO need to implement the manage page
            SellManageFrame sellManageFrame = new SellManageFrame(customer.getLastName()+customer.getFirstName(), tradingAccount);
            sellManageFrame.setVisible(true);
            dispose();
            this.setVisible(false);
        }else if(e.getSource() == buyButton){
            new BuyStockPage(this,customer.getLastName()+customer.getFirstName(),tradingAccount);
            this.setVisible(false);
        }
    }




}
