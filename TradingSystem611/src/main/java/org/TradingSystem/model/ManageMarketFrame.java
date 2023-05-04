package org.TradingSystem.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManageMarketFrame extends JFrame implements ActionListener {

    private final JPanel container;
    private final JLabel personIDLabel;
    private final JLabel nameLabel;
    private final JLabel showIDLabel;
    private final JLabel showNameLabel;
    private final JLabel stockInfoLabel;
    private final JLabel priceLabel;
    private final JLabel stockIDLabel;
    private final JTextField stockIDTextField;
    private final JButton back;
    private final JButton update;
    private final JButton delete;
    private final JButton addStock;
    private final JButton blockStock;
    private final JButton unblockStock;
    private final JButton updatePriceRealLife;
    //private JButton refreshButton;
    private JButton ref;
    private final JTextField priceTextField;
    private JTable stockTable;
    private JScrollPane scrollPane;
    private Manager manager;
    private PeopleDao peopleDao;
    private StockDao stockDao;

    public ManageMarketFrame(Manager manager){
        setTitle("Manage Market");
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
        update = new JButton("Update");
        addStock = new JButton("Add Stock");
        delete = new JButton("Delete");
        blockStock = new JButton("Block");
        unblockStock = new JButton("Unblock");
        ref = new JButton("Refresh");
        updatePriceRealLife = new JButton("Update Price Real Life");
        priceTextField = new JTextField();
        peopleDao = new PeopleDao();
        stockDao = new StockDao();
        stockIDLabel = new JLabel("StockID");
        stockIDTextField = new JTextField();
        //stockTable = new JTable();
        Object[] columnNames = {"ID", "Name", "Price", "Ticker"};
        List<Stock> list = stockDao.getAllUnblockedStocks();
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
        personIDLabel.setBounds(100, 50, 100, 30);
        nameLabel.setBounds(300, 50, 100, 30);
        showIDLabel.setBounds(100,100,100,30);
        showNameLabel.setBounds(300,100,100,30);
        stockInfoLabel.setBounds(450, 210, 100, 40);
        ref.setBounds(450,260,100,40);
        priceLabel.setBounds(150,450,100,40);
        priceTextField.setBounds(260,450,100,40);
        update.setBounds(380,450,100,40);
        delete.setBounds(500,450,100,40);
        back.setBounds(800,220,100,40);
        addStock.setBounds(800,400,100,40);
        blockStock.setBounds(800,300,100,40);
        unblockStock.setBounds(800,350,100,40);
        updatePriceRealLife.setBounds(100,300,200,40);
        stockIDLabel.setBounds(150,400,100,40);
        stockIDTextField.setBounds(270,400,100,40);
    }

    private void addComponentsToContainer(){
        //container.add(personIDLabel);
        //container.add(nameLabel);
        //container.add(showIDLabel);
        //container.add(showNameLabel);
        container.add(stockInfoLabel);
        container.add(priceLabel);
        container.add(priceTextField);
        container.add(addStock);
        container.add(update);
        container.add(delete);
        container.add(back);
        container.add(blockStock);
        container.add(unblockStock);
        container.add(stockIDLabel);
        container.add(stockIDTextField);
        container.add(ref);
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
        unblockStock.addActionListener(this);
        ref.addActionListener(this);
        updatePriceRealLife.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String priceText = priceTextField.getText();
        String stockIDText = stockIDTextField.getText();

        if (e.getSource() == back) {
            new ManagerFrame(manager);
            dispose();
        }else if(e.getSource() == addStock){
            new AddStockFrame();

        }else if(e.getSource() == update){
            try{
                if(priceText.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Please enter a price!");
                }else if(stockIDText.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Please enter a stockID!");
                }else {
                    double price = Double.parseDouble(priceText);
                    int stockID = Integer.parseInt(stockIDText);
                    Stock stock = stockDao.getStock(stockID);
                    stockDao.updateStockPrice(stock, price);
                    JOptionPane.showMessageDialog(this, "Price Successfully Updated!");
                }

            }catch(Exception ex){
                ex.printStackTrace();
            }
        }else if(e.getSource() == delete){
            try{
                if(stockIDText.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Please enter a stockID!");
                }else {
                    int stockID = Integer.parseInt(stockIDText);
                    Stock stock = stockDao.getStock(stockID);
                    stockDao.deleteStock(stock);
                    JOptionPane.showMessageDialog(this, "Stock Successfully Deleted!");
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }else if(e.getSource() == blockStock){
            try{
                if(stockIDText.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Please enter a stockID!");
                }else {
                    int stockID = Integer.parseInt(stockIDText);
                    Stock stock = stockDao.getStock(stockID);
                    stockDao.blockStock(stockID);
                    JOptionPane.showMessageDialog(this, "Stock Successfully Blocked!");
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }else if(e.getSource() == unblockStock){
            try{
                if(stockIDText.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Please enter a stockID!");
                }else {
                    int stockID = Integer.parseInt(stockIDText);
                    Stock stock = stockDao.getStock(stockID);
                    stockDao.unblockStock(stockID);
                    JOptionPane.showMessageDialog(this, "Stock Successfully Unblocked!");
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }else if(e.getSource() == ref) {
            Object[] columnNames = {"ID", "Name", "Price", "Ticker"};
            List<Stock> list = stockDao.getAllUnblockedStocks();
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
            new ManageMarketFrame(manager);
            dispose();
        }else if(e.getSource() == updatePriceRealLife){
            try{
                if(stockIDText.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Please enter a stockID!");
                }else {
                    int stockID = Integer.parseInt(stockIDText);
                    Market.updatePriceRealLife(stockID);
                    JOptionPane.showMessageDialog(this, "Stock Successfully Updated Real Life!");
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

    }
}
