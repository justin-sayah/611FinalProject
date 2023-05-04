//package org.TradingSystem.model;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.util.ArrayList;
//
//public class TransactionHistoryFrame extends JFrame {
//
//    private final JPanel container;
//    private final JLabel accountNumberLabel;
//    private final JLabel accountNameLabel;
//    private final JTable transactionTable;
//    private final JButton backButton;
//    private final String[] columns = {"Stock Name", "Time", "Price", "Quantity", "Action"};
//    private final DefaultTableModel model;
//
//    TransactionHistoryFrame(String accountNumber, String accountName, ArrayList<String[]> transactions) {
//        setTitle("Transaction History");
//        setSize(800, 600);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setResizable(false);
//
//        container = new JPanel();
//        container.setLayout(new BorderLayout());
//
//        // Account information panel
//        JPanel accountInfoPanel = new JPanel();
//        accountInfoPanel.setLayout(new GridLayout(2, 1));
//        accountNumberLabel = new JLabel("Account Number: " + accountNumber);
//        accountNameLabel = new JLabel("Account Name: " + accountName);
//        accountInfoPanel.add(accountNumberLabel);
//        accountInfoPanel.add(accountNameLabel);
//        container.add(accountInfoPanel, BorderLayout.NORTH);
//
//        // Transaction table panel
//        JPanel transactionPanel = new JPanel();
//        transactionPanel.setLayout(new BorderLayout());
//        model = new DefaultTableModel(columns, 0);
//        transactionTable = new JTable(model);
//        JScrollPane scrollPane = new JScrollPane(transactionTable);
//        transactionPanel.add(scrollPane, BorderLayout.CENTER);
//        container.add(transactionPanel, BorderLayout.CENTER);
//
//        // Back button panel
//        JPanel buttonPanel = new JPanel();
//        backButton = new JButton("Back");
//        buttonPanel.add(backButton);
//        container.add(buttonPanel, BorderLayout.SOUTH);
//
//        // Populate transaction table with dummy data
//        transactions.add(new String[]{"AAPL", "2022-04-26 14:00:00", "139.0", "100", "Buy"});
//        transactions.add(new String[]{"MSFT", "2022-04-26 15:00:00", "260.0", "50", "Sell"});
//        transactions.add(new String[]{"TSLA", "2022-04-26 16:00:00", "750.0", "25", "Buy"});
//
//        for (String[] transaction : transactions) {
//            model.addRow(transaction);
//        }
//
//        add(container);
//
//        setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        ArrayList<String[]> transactions = new ArrayList<>();
//        new TransactionHistoryFrame("123456", "John Doe", transactions);
//    }
//}

package org.TradingSystem.model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TransactionHistoryFrame extends JFrame {

    private final JPanel container;
    private final JLabel accountNumberLabel;
    private final JLabel accountNameLabel;
    private final JTable transactionTable;
    private final JButton backButton;
    private final String[] columns = {"Stock Name", "Time", "Price", "Quantity", "Action"};
    private final DefaultTableModel model;


    private final PeopleDao peopleDao;
    private final List<Transaction> transactions;

    public TransactionHistoryFrame(TradingAccount tradingAccount) {

        setTitle("Transaction History");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        peopleDao = new PeopleDao();

        container = new JPanel();
        container.setLayout(new BorderLayout());

        // Account information panel
        JPanel accountInfoPanel = new JPanel();
        accountInfoPanel.setLayout(new GridLayout(2, 1));
        accountNumberLabel = new JLabel("Account Number: " + tradingAccount.getAccountNumber());
        accountNameLabel = new JLabel("Account Name: " + peopleDao.getCustomer(tradingAccount.getPersonId()).getLastName()+", "+peopleDao.getCustomer(tradingAccount.getPersonId()).getFirstName());
        accountInfoPanel.add(accountNumberLabel);
        accountInfoPanel.add(accountNameLabel);
        container.add(accountInfoPanel, BorderLayout.NORTH);

        // Transaction table panel
        JPanel transactionPanel = new JPanel();
        transactionPanel.setLayout(new BorderLayout());
        model = new DefaultTableModel(columns, 0);
        transactionTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        transactionPanel.add(scrollPane, BorderLayout.CENTER);
        container.add(transactionPanel, BorderLayout.CENTER);

        // Back button panel
        JPanel buttonPanel = new JPanel();
        backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose());
        buttonPanel.add(backButton);
        container.add(buttonPanel, BorderLayout.SOUTH);

        // Get transactions for account and add them to the table
        transactions = Transaction.getAllTransactionsByAccount(tradingAccount.getAccountNumber());
        for (Transaction transaction : transactions) {
            Stock stock = StockDao.getInstance().getStock(transaction.getStockId());
            String[] transactionData = {
                    stock.getName(),
                    transaction.getTimestamp(),
                    String.valueOf(transaction.getPrice()),
                    String.valueOf(transaction.getQuantity()),
                    transaction.getAction()
            };
            model.addRow(transactionData);
        }

        add(container);

        setVisible(true);
    }

//    public static void main(String[] args) {
//        // create a new account
//        TradingAccount account = new TradingAccount(1234);
//
//        // add a new transaction
//        Transaction.addTransaction(account.getAccountNumber(), 1, 10, 50.0, "buy");
//
//        // update the transaction
//        Transaction transaction = Transaction.getAllTransactionsByAccount(account.getAccountNumber()).get(0);
//        transaction.setPrice(55.0);
//        transaction.updateTransaction();
//
//        // delete the transaction
//        Transaction.deleteTransaction(transaction);
//
//        // print all transactions for the account
//        List<Transaction> transactions = Transaction.getAllTransactionsByAccount(account.getAccountNumber());
//        for (Transaction t : transactions) {
//            System.out.println(t);
//        }
//    }


}


