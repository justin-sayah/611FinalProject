package org.TradingSystem.views;

import org.TradingSystem.model.TradingAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DepositPopup extends JDialog {

    private JTextField depositAmountField;

    public DepositPopup(CustomerAccountView customerAccountView, TradingAccount tradingAccount) {


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
                try {
                    int amount = Integer.parseInt(depositAmount);
                    if (amount < 0) {
                        JOptionPane.showMessageDialog(DepositPopup.this, "Invalid deposit amount. Please enter a positive value.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    tradingAccount.deposit(amount);
                    TradingAccount.update(tradingAccount);
                    tradingAccount.refresh();
                    customerAccountView.balance.setText(String.valueOf(tradingAccount.getBalance()));
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(DepositPopup.this, "Deposit amount must be a positive integer.");
                    return;
                }
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
