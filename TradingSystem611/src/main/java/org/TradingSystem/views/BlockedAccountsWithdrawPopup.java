package org.TradingSystem.views;

import org.TradingSystem.model.TradingAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlockedAccountsWithdrawPopup extends JDialog {
    private JTextField withdrawAmountField;
    protected TradingAccount updatedTradingAccount;

    public BlockedAccountsWithdrawPopup(BlockedAccountPage blockedAccountPage, TradingAccount tradingAccount) {


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
        setLocationRelativeTo(blockedAccountPage);

        // Add action listeners to the buttons
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform the deposit action
                String withdrawAmount = withdrawAmountField.getText();
                int amount = Integer.parseInt(withdrawAmount);
                if (amount < 0) {
                    JOptionPane.showMessageDialog(BlockedAccountsWithdrawPopup.this, "Invalid withdraw amount. Please enter a positive value.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //tradingAccount.setBalance(tradingAccount.getBalance()-Integer.parseInt(withdrawAmount));
                System.out.println("balance before withdraw: " + tradingAccount.getBalance());
                tradingAccount.withdraw(amount);
                System.out.println("balance after withdraw: "+tradingAccount.getBalance());
                TradingAccount.update(tradingAccount);
                tradingAccount.refresh();
                blockedAccountPage.balance.setText(String.valueOf(tradingAccount.getBalance()));

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
