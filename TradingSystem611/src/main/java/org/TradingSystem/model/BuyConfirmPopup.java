package org.TradingSystem.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuyConfirmPopup extends JDialog {
    private TradingAccountDao tradingAccountDao;


    public BuyConfirmPopup(CustomerAccountView customerAccountView,BuyStockPage buyStockPage, double amountCost, double initBalance,TradingAccount tradingAccount, int stockId, int quantity,double price){

        tradingAccountDao = new TradingAccountDao();

        JLabel instructionLabel = new JLabel("Your total cost is $"+ amountCost);
        JButton confirmButton = new JButton("CONFIRM");
        JButton cancelButton = new JButton("CANCEL");

        // Configure the layout of the pop-up window
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.add(instructionLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        panel.add(buttonPanel);
        add(panel, BorderLayout.CENTER);
        // Set the size and position of the pop-up window
        setSize(300, 150);
        setLocationRelativeTo(buyStockPage);

        // Add action listeners to the buttons
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform the deposit action
                JOptionPane.showMessageDialog(BuyConfirmPopup.this,"You have successfully bought the stock");
                Position.buy(tradingAccount.getAccountNumber(),stockId,quantity);

                buyStockPage.balance.setText(String.valueOf(TradingAccount.getAccount(tradingAccount.getAccountNumber()).getBalance()));
                customerAccountView.balance.setText(String.valueOf(TradingAccount.getAccount(tradingAccount.getAccountNumber()).getBalance()));

                //transactionDao.addTransaction(tradingAccount.getAccountNumber(),buyStockPage.buyQuantity);

                // Add your code to process the deposit amount here
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the pop-up window when canceling the deposit
            }
        });
    }

    }

