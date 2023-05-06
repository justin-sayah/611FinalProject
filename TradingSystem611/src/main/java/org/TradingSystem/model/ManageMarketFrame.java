package org.TradingSystem.model;

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
    private PeopleDao peopleDao;
    private StockDao stockDao;

    public ManageMarketFrame(Manager manager){
        setTitle("Manage Unblocked Stocks");
        setVisible(true);
        setLocation(10,10);
        setSize(1000,600);
        setResizable(false);
        container = new JPanel();
        personIDLabel = new JLabel("PersonID");
        nameLabel = new JLabel("Name");
        priceLabel = new JLabel("Price");
        stockInfoLabel = new JLabel("Stock Info");
        this.manager = manager;
        int personID = manager.getID();
        String name = manager.getFirstName()+" "+manager.getLastName();
        showIDLabel = new JLabel(personID + "");
        showNameLabel = new JLabel(name + "");
        back = new JButton("Back");
        update = new JButton("Update Price");
        addStock = new JButton("Add Stock");
        delete = new JButton("Delete");
        blockStock = new JButton("Block");
        viewBlockedStocks = new JButton("View Blocked Stocks");
        refresh = new JButton("Refresh");
        updatePriceRealLife = new JButton("Update Price Real Life");
        priceTextField = new JTextField();
        peopleDao = new PeopleDao();
        stockDao = new StockDao();
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
        container.setLayout(new BorderLayout());
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();

    }

    private void setLocationAndSize(){
        personIDLabel.setBounds(100, 50, 100, 30);
        nameLabel.setBounds(300, 50, 100, 30);
        showIDLabel.setBounds(100,100,100,30);
        showNameLabel.setBounds(300,100,100,30);
        stockInfoLabel.setBounds(450, 210, 100, 40);
        refresh.setBounds(650,500,100,30);
        priceLabel.setBounds(100,500,50,30);
        priceTextField.setBounds(170,500,100,30);
        update.setBounds(280,500,150,30);
        delete.setBounds(100,300,100,40);
        back.setBounds(800,500,100,30);
        addStock.setBounds(800,350,100,40);
        blockStock.setBounds(100,250,100,40);
        viewBlockedStocks.setBounds(700,300,200,40);
        updatePriceRealLife.setBounds(100,350,200,40);

    }

    private void addComponentsToContainer(){

        container.add(stockInfoLabel);
        container.add(priceLabel);
        container.add(priceTextField);
        container.add(addStock);
        container.add(update);
        container.add(delete);
        container.add(back);
        container.add(blockStock);
        container.add(viewBlockedStocks);
        container.add(refresh);
        container.add(updatePriceRealLife);
        container.add(scrollPane, BorderLayout.CENTER);

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
