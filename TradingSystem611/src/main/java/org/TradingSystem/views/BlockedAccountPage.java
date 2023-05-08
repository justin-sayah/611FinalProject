package org.TradingSystem.views;

import org.TradingSystem.database.TradingAccountDao;
import org.TradingSystem.model.Customer;
import org.TradingSystem.model.TradingAccount;
import org.TradingSystem.views.BlockedAccountsWithdrawPopup;
import org.TradingSystem.views.CustomerHomePage;
import org.TradingSystem.views.SellManageFrame;
import org.TradingSystem.views.TransactionHistoryFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlockedAccountPage extends JFrame implements ActionListener {
    private final JPanel container;
    private final JLabel customerNameLabel;
    private final JLabel accountIDLabel;

    private final JButton backButton;
    private final JButton manageButton;

    private final JButton transacButton;

    private final JButton withdrawButton;


    private final JLabel balanceLabel;
    protected final JLabel balance;
    private final JLabel realizedPLLabel;
    private final JLabel realizedPL;
    private final JLabel unrealizedPLLabel;
    private final JLabel unrealizedPL;
    private final JLabel accountID;
    private final JLabel nameLabel;
    private final JLabel customerName;
    private TradingAccountDao tradingAccountDao;
    private Customer customer;
    private TradingAccount tradingAccount;
    private JPanel customerInfoPanel;
    private JLabel customerAccountLabel;
    private JPanel topPanel;
    private JPanel buttonPanel;
    private JPanel bottomPanel;
    private JPanel infoPanel;


    private BlockedAccountsWithdrawPopup withdrawPopup;

    public BlockedAccountPage(TradingAccount tradingAccount) {
        this.tradingAccount = tradingAccount;
        customer = Customer.getCustomer(tradingAccount.getPersonId());
        setVisible(true);
        setTitle("Customer Block Account Page");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        container = new JPanel();
        container.setPreferredSize(new Dimension(1000,800));
        topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(1000,170));
        buttonPanel = new JPanel(new GridLayout(4,1));
        bottomPanel = new JPanel(new GridLayout(1,2));
        bottomPanel.setPreferredSize(new Dimension(1000,550));
        infoPanel = new JPanel(new GridLayout(3,2));


        customerNameLabel = new JLabel();
        customerName = new JLabel(customer.getLastName()+", "+customer.getFirstName());
        customerInfoPanel = new JPanel(new GridLayout(1, 4));
        customerInfoPanel.setPreferredSize(new Dimension(1000,20));
        customerAccountLabel = new JLabel("BLOCK ACCOUNT CENTER",JLabel.CENTER);
        customerAccountLabel.setFont(new Font("Verdana", Font.PLAIN, 50));
        customerAccountLabel.setPreferredSize(new Dimension(1000,150));
        customerAccountLabel.setForeground(Color.red);
        customerAccountLabel.setOpaque(true);
        customerAccountLabel.setBackground(Color.blue);


        accountIDLabel = new JLabel(String.valueOf(tradingAccount.getAccountNumber()));
        accountID = new JLabel("Account Number: ");
        nameLabel = new JLabel("Customer Name: ");
        backButton = new JButton("BACK");
        backButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        manageButton = new JButton("SELL");
        manageButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        transacButton = new JButton("TRANSACTIONS");
        transacButton.setFont(new Font("Verdana", Font.PLAIN, 20));
        withdrawButton = new JButton("WITHDRAW");
        withdrawButton.setFont(new Font("Verdana", Font.PLAIN, 20));

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

        setLocationAndSize();

        addComponentsToContainer();
        addActionEvent();
        tradingAccount.refresh();
    }

    public void setLocationAndSize() {

        customerInfoPanel.add(accountID);
        customerInfoPanel.add(accountIDLabel);
        customerInfoPanel.add(nameLabel);
        customerInfoPanel.add(customerName);

        topPanel.add(customerAccountLabel, BorderLayout.NORTH);
        topPanel.add(customerInfoPanel,BorderLayout.SOUTH);
        buttonPanel.add(manageButton);
        buttonPanel.add(withdrawButton);
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




    public void addComponentsToContainer() {
        container.add(topPanel);
        container.add(bottomPanel);
        add(container);
    }


    private void addActionEvent() {
        backButton.addActionListener(this);
        manageButton.addActionListener(this);
        transacButton.addActionListener(this);
        withdrawButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            new CustomerHomePage(customer);
            this.setVisible(false);
        } else if (e.getSource() == transacButton) {
            new TransactionHistoryFrame(tradingAccount);
            //TODO need to implement the transaction page
        } else if (e.getSource() == withdrawButton) { //call the withdraw popup window
            if (withdrawPopup == null) {
                withdrawPopup = new BlockedAccountsWithdrawPopup(this, tradingAccount);

            }
                withdrawPopup.setVisible(true); // Show the withdraw popup window

//

            } else if (e.getSource() == manageButton) {
                //TODO need to implement the manage page
                SellManageFrame sellManageFrame = new SellManageFrame(customer.getLastName() + customer.getFirstName(), tradingAccount);
                sellManageFrame.setVisible(true);
                this.setVisible(false);
            }
        }



}