package org.TradingSystem.model;

import javax.swing.*;
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
        customerNameLabel = new JLabel(customer.getLastName()+", "+customer.getFirstName());
        accountIDLabel = new JLabel(String.valueOf(tradingAccount.getAccountNumber()));
        accountID = new JLabel("Account Number: ");
        nameLabel = new JLabel("Customer Name: ");
        backButton = new JButton("BACK");
        manageButton = new JButton("MANAGE/SELL");
        buyButton = new JButton("BUY");
        transacButton = new JButton("TRANSACTIONS");
        withdrawButton = new JButton("WITHDRAW");
        depositButton = new JButton("DEPOSIT");

        //TODO to implement the automatic refresh function later


        balanceLabel = new JLabel("Balance");
        balance = new JLabel(String.valueOf(tradingAccount.getBalance()));
        realizedPLLabel = new JLabel("Realized Profit/Loss");
        realizedPL = new JLabel(String.valueOf(tradingAccount.getRealizedProfitLoss()));
        unrealizedPLLabel = new JLabel("Unrealized Profit/Loss");
        unrealizedPL = new JLabel(String.valueOf(tradingAccount.getUnrealizedProfitLoss()));
        setLocationAndSize();
        setLayoutManager();
        addComponentsToContainer();
        addActionEvent();


    }
//set locations for each label and button: x represents the column, y represents the row
    public void setLocationAndSize() {

        accountID.setBounds(0,0,110,40);
        accountIDLabel.setBounds(120, 0, 20, 40);
        nameLabel.setBounds(150,0,110,40);
        customerNameLabel.setBounds(260, 0, 200, 40);
        balanceLabel.setBounds(150, 200, 100, 40);
        balance.setBounds(350, 200, 100, 40);
        realizedPLLabel.setBounds(150, 300, 200, 40);
        realizedPL.setBounds(350, 300, 100, 40);
        unrealizedPLLabel.setBounds(150, 400, 200, 40);
        unrealizedPL.setBounds(350, 400, 100, 40);
        manageButton.setBounds(600, 250, 150, 40);
        backButton.setBounds(878,0,100,40);
        buyButton.setBounds(600, 350, 100, 40);
        depositButton.setBounds(150, 550, 100, 40);
        withdrawButton.setBounds(300, 550, 100, 40);
        transacButton.setBounds(650, 550, 150, 40);


    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void addComponentsToContainer(){
        container.add(accountID);
        container.add(accountIDLabel);
        container.add(customerNameLabel);
        container.add(balanceLabel);
        container.add(balance);
        container.add(realizedPLLabel);
        container.add(realizedPL);
        container.add(unrealizedPLLabel);
        container.add(unrealizedPL);
        container.add(manageButton);
        container.add(buyButton);
        container.add(backButton);

        container.add(depositButton);
        container.add(withdrawButton);
        container.add(transacButton);
        container.add(nameLabel);
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
            this.setVisible(false);
        }else if(e.getSource() == buyButton){
            new BuyStockPage(this,customer.getLastName()+customer.getFirstName(),tradingAccount);
            this.setVisible(false);
        }
    }




}
