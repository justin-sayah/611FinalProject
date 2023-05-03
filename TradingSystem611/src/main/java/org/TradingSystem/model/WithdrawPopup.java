package org.TradingSystem.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WithdrawPopup extends JDialog{

    private JTextField withdrawAmountField;
    private TradingAccountDao tradingAccountDao;

    private CustomerAccountView customerAccountView;

    public WithdrawPopup(CustomerAccountView customerAccountView, TradingAccount tradingAccount) {
        this.customerAccountView = customerAccountView;
        tradingAccountDao = new TradingAccountDao();

        // Create and configure the components
        JLabel instructionLabel = new JLabel("Enter withdraw amount:");
        withdrawAmountField = new JTextField(10);
        JButton confirmButton = new JButton("CONFIRM");
        JButton cancelButton = new JButton("CANCEL");

        // Configure the layout of the pop-up window
        setLayout(new FlowLayout());
        add(instructionLabel);
        add(withdrawAmountField);
        add(confirmButton);
        add(cancelButton);

        // Set the size and position of the pop-up window
        setSize(300, 150);
        setLocationRelativeTo(customerAccountView);

        // Add action listeners to the buttons
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform the deposit action
                String withdrawAmount = withdrawAmountField.getText();
                tradingAccount.setBalance(tradingAccount.getBalance()-Integer.parseInt(withdrawAmount));
                tradingAccountDao.update(tradingAccount);

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
