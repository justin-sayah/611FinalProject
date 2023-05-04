package org.TradingSystem.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageAccountFrame extends JFrame implements ActionListener {

    private final JPanel container;
    private final JLabel personIdLabel;
    private final JLabel personNameLabel;
    private final JButton approveButton;
    private final JButton deleteButton;
    private final JButton pendingButton;
    private final JButton activeButton;
    private final JButton backButton;
    private final Manager manager;

    public ManageAccountFrame(Manager manager) {
        setTitle("Manage Account");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        this.manager = manager;

        container = new JPanel();
        container.setLayout(new BorderLayout());

        // Person information panel
        JPanel personInfoPanel = new JPanel();
        personInfoPanel.setLayout(new GridLayout(2, 1));
        personIdLabel = new JLabel("Person ID: " + manager.getID());
        personNameLabel = new JLabel("Person Name: " + manager.getFirstName() + " " + manager.getLastName());
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

        addActionListeners();

        setVisible(true);
    }

    private void addActionListeners() {
        approveButton.addActionListener(this);
        deleteButton.addActionListener(this);
        pendingButton.addActionListener(this);
        activeButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            dispose();
        } else if (e.getSource() == approveButton) {
            System.out.println("Approve Account button clicked!");
            ApproveAccountFrame approveAccountFrame = new ApproveAccountFrame(manager);
            dispose();
        } else if (e.getSource() == deleteButton) {
            System.out.println("Delete Account button clicked!");
            DeleteAccountFrame deleteAccountFrame = new DeleteAccountFrame(manager);
        } else if (e.getSource() == pendingButton) {
            System.out.println("View Pending Accounts button clicked!");
            ViewPendingAccountFrame viewPendingAccountFrame = new ViewPendingAccountFrame(manager);
        } else if (e.getSource() == activeButton) {
            System.out.println("View Active Accounts button clicked!");
            ViewActiveAccountFrame viewActiveAccountFrame = new ViewActiveAccountFrame(manager);
        }
    }

    public static void main(String[] args) {
        Manager manager = new Manager(1, "John", "Doe", "username", "password", "1990-01-01", "123-45-6789");
        new ManageAccountFrame(manager);
    }
}




