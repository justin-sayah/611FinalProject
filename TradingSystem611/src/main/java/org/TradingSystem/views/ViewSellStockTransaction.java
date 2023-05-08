package org.TradingSystem.views;

import org.TradingSystem.database.PeopleDao;
import org.TradingSystem.database.StockDao;
import org.TradingSystem.model.Stock;
import org.TradingSystem.model.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewSellStockTransaction extends JDialog {
    private JPanel container;
    private final DefaultTableModel model;
    private final JTable transactionTable;
    private final String[] columns = {"Stock Name", "Time", "Price", "Quantity", "Action"};
    public ViewSellStockTransaction(SellManageFrame sellManageFrame, int stockId){
        setTitle("Transaction History");
        setLocationRelativeTo(sellManageFrame);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000,800);

        container = new JPanel();
        container.setLayout(new BorderLayout());


        JPanel transactionPanel = new JPanel();
        transactionPanel.setLayout(new BorderLayout());
        model = new DefaultTableModel(columns, 0);
        transactionTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        transactionPanel.add(scrollPane, BorderLayout.CENTER);
        container.add(transactionPanel, BorderLayout.CENTER);

        List<Transaction> transactions = Transaction.getAllTransactionsByStock(stockId);
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
}
