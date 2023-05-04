package org.TradingSystem.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DepositPopup extends JDialog {

    private JTextField depositAmountField;
    private TradingAccountDao tradingAccountDao;

    public DepositPopup(CustomerAccountView customerAccountView, TradingAccount tradingAccount) {

        tradingAccountDao = new TradingAccountDao();

        // Create and configure the components
        JLabel instructionLabel = new JLabel("Enter deposit amount:");
        depositAmountField = new JTextField(10);
        JButton confirmButton = new JButton("CONFIRM");
        JButton cancelButton = new JButton("CANCEL");

        // Configure the layout of the pop-up window
        setLayout(new FlowLayout());
        add(instructionLabel);
        add(depositAmountField);
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
                String depositAmount = depositAmountField.getText();
                int amount = Integer.parseInt(depositAmount);
                if (amount < 0) {
                    JOptionPane.showMessageDialog(DepositPopup.this, "Invalid deposit amount. Please enter a positive value.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                tradingAccount.setBalance(Integer.parseInt(depositAmount)+tradingAccount.getBalance());
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
