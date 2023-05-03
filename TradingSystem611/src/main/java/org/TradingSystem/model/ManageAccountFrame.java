package org.TradingSystem.model;

import javax.swing.*;
import java.awt.*;

public class ManageAccountFrame extends JFrame {

    private final JPanel container;
    private final JLabel personIdLabel;
    private final JLabel personNameLabel;
    private final JButton approveButton;
    private final JButton deleteButton;
    private final JButton pendingButton;
    private final JButton activeButton;
    private final JButton backButton;

    public ManageAccountFrame(String personId, String personName) {
        setTitle("Manage Account");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        container = new JPanel();
        container.setLayout(new BorderLayout());

        // Person information panel
        JPanel personInfoPanel = new JPanel();
        personInfoPanel.setLayout(new GridLayout(2, 1));
        personIdLabel = new JLabel("Person ID: " + personId);
        personNameLabel = new JLabel("Person Name: " + personName);
        personInfoPanel.add(personIdLabel);
        personInfoPanel.add(personNameLabel);
        container.add(personInfoPanel, BorderLayout.WEST);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2, 2));

        approveButton = new JButton("Approve Account");
        deleteButton = new JButton("Delete Account");
        pendingButton = new JButton("View Pending Accounts");
        activeButton = new JButton("View Active Accounts");
        backButton = new JButton("Back");

        buttonsPanel.add(approveButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(pendingButton);
        buttonsPanel.add(activeButton);
        container.add(buttonsPanel, BorderLayout.CENTER);

        // Back button panel
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.add(backButton);
        container.add(backButtonPanel, BorderLayout.NORTH);

        add(container);

        setVisible(true);
    }


    public static void main(String[] args) {
        new ManageAccountFrame("123456", "John Doe");
    }
}


