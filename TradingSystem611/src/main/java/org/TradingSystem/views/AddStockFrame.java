package org.TradingSystem.views;

import org.TradingSystem.database.PeopleDao;
import org.TradingSystem.model.Market;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddStockFrame extends JFrame implements ActionListener {
    private final JPanel container;
    private final JLabel stockNameLabel;
    private final JLabel priceLabel;
    private final JLabel tickerLabel;
    private final JLabel stockIDLabel;
    private final JTextField stockNameTextField;
    private final JTextField priceTextField;
    private final JTextField tickerTextField;
    private final JTextField stockIDTextField;
    private final JButton close;
    private final JButton add;
    private PeopleDao.StockDao stockDao;

    public AddStockFrame(){
        setTitle("Add Stock");
        setVisible(true);
        setLocation(10,10);
        setSize(1000,600);
        setResizable(false);
        container = new JPanel();
        stockIDLabel = new JLabel("Stock ID");
        stockNameLabel = new JLabel("Stock Name");
        priceLabel = new JLabel("Price");
        tickerLabel = new JLabel("Ticker");
        close = new JButton("Close");
        add = new JButton("Add");
        stockNameTextField = new JTextField();
        priceTextField = new JTextField();
        tickerTextField = new JTextField();
        stockIDTextField = new JTextField();
        stockDao = PeopleDao.StockDao.getInstance();
        container.setLayout(null);
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    private void setLocationAndSize(){
        stockIDLabel.setBounds(150, 100, 100, 40);
        stockIDTextField.setBounds(300, 100, 400, 40);
        stockNameLabel.setBounds(150, 200, 100, 40);
        stockNameTextField.setBounds(300, 200, 400, 40);
        priceLabel.setBounds(150, 300, 100, 40);
        priceTextField.setBounds(300, 300, 400, 40);
        tickerLabel.setBounds(150, 400, 100, 40);
        tickerTextField.setBounds(300, 400, 400, 40);
        close.setBounds(800,50,100,40);
        add.setBounds(300,500,100,40);

    }

    private void addComponentsToContainer(){
        container.add(stockIDLabel);
        container.add(stockIDTextField);
        container.add(stockNameLabel);
        container.add(stockNameTextField);
        container.add(priceLabel);
        container.add(priceTextField);
        container.add(tickerLabel);
        container.add(tickerTextField);
        container.add(close);
        container.add(add);
        add(container);
    }

    private void addActionEvent(){
        close.addActionListener(this);
        add.addActionListener(this);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == close) {
            dispose();
        }else if(e.getSource() == add){
            String stockNameText = stockNameTextField.getText();
            String priceText = priceTextField.getText();
            String tickerText = tickerTextField.getText();
            String stockIDText = stockIDTextField.getText();
            if(stockNameText.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter a stock name");
            }else if(priceText.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter a price");
            }else if(tickerText.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter a ticker");
            }else if(stockIDText.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter a stockID");
            }else{
                try{
                    double price = Double.parseDouble(priceText);
                    int stockID = Integer.parseInt(stockIDText);
                    Market.getInstance().addStock(stockNameText, price, tickerText);
                    JOptionPane.showMessageDialog(this, "Stock Added Successfully!");
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }

    }


}
