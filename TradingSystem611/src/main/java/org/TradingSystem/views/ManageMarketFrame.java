package org.TradingSystem.views;

import org.TradingSystem.database.PeopleDao;
import org.TradingSystem.database.StockDao;
import org.TradingSystem.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Pattern;

public class ManageMarketFrame extends JFrame implements ActionListener {

    private final JPanel container;
    private final JLabel personIDLabel;
    private final JLabel nameLabel;
    private final JLabel showIDLabel;
    private final JLabel showNameLabel;
    private final JLabel stockInfoLabel;
    private final JLabel priceLabel;
    private final JButton back;
    private final JButton update;
    private final JButton delete;
    private final JButton addStock;
    private final JButton blockStock;
    private final JButton viewBlockedStocks;
    private final JButton updatePriceRealLife;
    //private JButton refreshButton;
    private final JButton refresh;
    private final JTextField priceTextField;
    private JTable stockTable;
    private JScrollPane scrollPane;
    private Manager manager;
    private StockDao stockDao;
    private JLabel stockLabel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel buttonPanel;

    public ManageMarketFrame(Manager manager){
        setTitle("Manage Unblocked Stocks");
        setVisible(true);
        setLocation(10,10);
        setSize(1000,800);
        setResizable(false);
        container = new JPanel();
        container.setPreferredSize(new Dimension(1000,800));
        personIDLabel = new JLabel("PersonID");
        nameLabel = new JLabel("Name");
        priceLabel = new JLabel("CHANGE PRICE");
        priceLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
        stockInfoLabel = new JLabel("Stock Info");
        this.manager = manager;
        int personID = manager.getID();
        String name = manager.getFirstName()+" "+manager.getLastName();
        showIDLabel = new JLabel(personID + "");
        showNameLabel = new JLabel(name + "");
        back = new JButton("BACK");
        back.setFont(new Font("Verdana", Font.PLAIN, 20));
        update = new JButton("UPDATE PRICE");
        update.setFont(new Font("Verdana", Font.PLAIN, 15));
        addStock = new JButton("ADD STOCK");
        addStock.setFont(new Font("Verdana", Font.PLAIN, 20));
        delete = new JButton("DELETE");
        delete.setFont(new Font("Verdana", Font.PLAIN, 20));
        blockStock = new JButton("BLOCK");
        blockStock.setFont(new Font("Verdana", Font.PLAIN, 20));
        viewBlockedStocks = new JButton("VIEW BLOCKED STOCKS");
        viewBlockedStocks.setFont(new Font("Verdana", Font.PLAIN, 20));
        refresh = new JButton("REFRESH");
        refresh.setFont(new Font("Verdana", Font.PLAIN, 20));
        updatePriceRealLife = new JButton("UPDATE REAL STOCK PRICE");
        updatePriceRealLife.setFont(new Font("Verdana", Font.PLAIN, 20));
        priceTextField = new JTextField();
        priceTextField.setPreferredSize(new Dimension(100,40));
        stockDao = StockDao.getInstance();

        topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(1000,170));

        stockLabel = new JLabel("STOCK CENTER",JLabel.CENTER);
        stockLabel.setFont(new Font("Verdana", Font.PLAIN, 50));
        stockLabel.setPreferredSize(new Dimension(1000,150));
        stockLabel.setForeground(Color.red);
        stockLabel.setOpaque(true);
        stockLabel.setBackground(Color.blue);

        buttonPanel = new JPanel(new GridLayout(7,1));
        buttonPanel.setPreferredSize(new Dimension(300,550));
        bottomPanel = new JPanel(new GridLayout(1,2));
        bottomPanel.setPreferredSize(new Dimension(1000,550));

        Object[] columnNames = {"ID", "Name", "Price", "Ticker"};
        List<Stock> list = Market.getInstance().getAllUnblockedStocks();

        Object[][] data = new Object[list.size()][4];
        for(int i = 0; i < list.size(); i++){
            data[i][0] = list.get(i).getSecurityId();
            data[i][1] = list.get(i).getName();
            data[i][2] = list.get(i).getPrice();
            data[i][3] = list.get(i).getTicker();
        }
        stockTable = new JTable(data,columnNames);
        stockTable.setRowHeight(30);
        stockTable.setPreferredScrollableViewportSize(new Dimension(300,200));
        //stockTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane = new JScrollPane(stockTable);
        scrollPane.setSize(new Dimension(600,200));

        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();

    }

    private void setLocationAndSize(){
        topPanel.add(stockLabel);

        buttonPanel.add(blockStock);
        buttonPanel.add(delete);
        buttonPanel.add(updatePriceRealLife);
        buttonPanel.add(viewBlockedStocks);
        buttonPanel.add(addStock);
        buttonPanel.add(refresh);
        buttonPanel.add(back);

        JPanel right = new JPanel(new BorderLayout());
        JPanel updatePrice = new JPanel(new GridLayout(1,3));
        updatePrice.add(priceLabel);
        updatePrice.add(priceTextField);
        updatePrice.add(update);
        right.add(scrollPane,BorderLayout.NORTH);
        right.add(updatePrice,BorderLayout.SOUTH);
        bottomPanel.add(buttonPanel);
        bottomPanel.add(right);
//

    }

    private void addComponentsToContainer(){

       container.add(topPanel);
       container.add(bottomPanel);
        add(container);
    }

    private void addActionEvent(){
        back.addActionListener(this);
        addStock.addActionListener(this);
        update.addActionListener(this);
        delete.addActionListener(this);
        blockStock.addActionListener(this);
        viewBlockedStocks.addActionListener(this);
        refresh.addActionListener(this);
        updatePriceRealLife.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String priceText = priceTextField.getText();
        List<Stock> list = stockDao.getAllUnblockedStocks();
        System.out.println("LIST AFTER CLICKING BACK:" + list);
        if (e.getSource() == back) {
            new ManagerFrame(manager);
            dispose();
        }else if(e.getSource() == addStock){
            new AddStockFrame();

        }else if(e.getSource() == update){
            try{
                if(priceText.isEmpty() || !isDouble(priceText)){
                    JOptionPane.showMessageDialog(this, "Please enter a valid price!");
                }else {
                    double price = Double.parseDouble(priceText);
                    int row = stockTable.getSelectedRow();

                    if(row < 0){
                        JOptionPane.showMessageDialog(this, "Please choose a row!");
                    }else {
                        Stock stock = list.get(row);
                        stockDao.updateStockPrice(stock, price);
                        JOptionPane.showMessageDialog(this, "Price Successfully Updated!");
                        refresh();
                    }
                }

            }catch(Exception ex){
                ex.printStackTrace();
            }
        }else if(e.getSource() == delete){
            int row = stockTable.getSelectedRow();

            if(row < 0){
                JOptionPane.showMessageDialog(this, "Please choose a row!");
            }else {
                Stock stock = list.get(row);
                stockDao.deleteStock(stock);
                JOptionPane.showMessageDialog(this, "Stock Successfully Deleted!");
                refresh();
            }
        }else if(e.getSource() == blockStock){
            int row = stockTable.getSelectedRow();

            if(row < 0){
                JOptionPane.showMessageDialog(this, "Please choose a row!");
            }else {
                Stock stock = list.get(row);
                stockDao.blockStock(stock);
                JOptionPane.showMessageDialog(this, "Stock Successfully Blocked!");
                refresh();
            }
        }else if(e.getSource() == viewBlockedStocks){
            new ViewBlockedStocksFrame(manager);
        }else if(e.getSource() == updatePriceRealLife){
            int row = stockTable.getSelectedRow();
            if(row < 0){
                JOptionPane.showMessageDialog(this, "Please choose a row!");
            }else {
                Stock stock = list.get(row);
                Market.updatePriceRealLife(stock.getSecurityId());
                //Market.updateAllPricesRealLife();
                JOptionPane.showMessageDialog(this, "Price Successfully Updated to Real Life!");
                refresh();
            }
        }else if(e.getSource() == refresh){
            refresh();
        }

    }

    private void refresh(){
        new ManageMarketFrame(manager);
        dispose();
    }

    private boolean isDouble(String s) {
        Pattern pattern = Pattern.compile("[+]?\\d+(.\\d+)?");
        return pattern.matcher(s).matches();
    }
}
