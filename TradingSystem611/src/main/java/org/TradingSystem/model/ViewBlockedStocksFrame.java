package org.TradingSystem.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewBlockedStocksFrame extends JFrame implements ActionListener {

    private final JPanel container;
    private final JButton back;
    private final JButton unblock;
    private JScrollPane scrollPane;
    private JTable stockTable;
    private StockDao stockDao;
    private Manager manager;

    public ViewBlockedStocksFrame(Manager manager){
        setTitle("View Blocked Stocks");
        setVisible(true);
        setLocation(10,10);
        setSize(1000,600);
        setResizable(false);
        container = new JPanel();
        back = new JButton("Back");
        unblock = new JButton("Unblock");
        stockDao = new StockDao();
        this.manager = manager;
        Object[] columnNames = {"ID", "Name", "Price", "Ticker"};
        List<Stock> list = stockDao.getAllBlockedStocks();
        Object[][] data = new Object[list.size()][4];
        for(int i = 0; i < list.size(); i++){
            data[i][0] = list.get(i).getSecurityId();
            data[i][1] = list.get(i).getName();
            data[i][2] = list.get(i).getPrice();
            data[i][3] = list.get(i).getTicker();
        }
        stockTable = new JTable(data,columnNames);
        stockTable.setRowHeight(40);
        stockTable.setPreferredScrollableViewportSize(new Dimension(300,200));
        scrollPane = new JScrollPane(stockTable);
        container.setLayout(new BorderLayout());
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    private void setLocationAndSize(){
        unblock.setBounds(450,310,100,40);
        back.setBounds(450,370,100,40);
    }

    private void addComponentsToContainer(){
        container.add(unblock);
        container.add(back);
        container.add(scrollPane, BorderLayout.CENTER);

        add(container);
    }

    private void addActionEvent(){
        unblock.addActionListener(this);
        back.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == unblock){
            int row = stockTable.getSelectedRow();
            List<Stock> list = stockDao.getAllBlockedStocks();
            if(row < 0){
                JOptionPane.showMessageDialog(this, "Please choose a row!");
            }else {
                Stock stock = list.get(row);
                stockDao.unblockStock(stock);
                JOptionPane.showMessageDialog(this, "Stock Successfully Unblocked!");
                refresh();
            }
        }else if(e.getSource() == back){
            dispose();
        }
    }

    private void refresh(){
        new ViewBlockedStocksFrame(manager);
        dispose();
    }
}
