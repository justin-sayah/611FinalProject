package org.TradingSystem.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewPositionsFrame extends JFrame implements ActionListener {
    private JTable positionTable;
    private final JPanel container;
    private JScrollPane scrollPane;
    private TradingAccountDao tDao;
    private final JButton back;
    private List<Position> list;

    public ViewPositionsFrame(TradingAccount account){
        setTitle("Customer Accounts");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(10,10);
        setSize(1000,600);
        setVisible(true);
        setResizable(false);
        container = new JPanel();
        tDao = new TradingAccountDao();

        Object[] columnNames = {"AccountId","SecurityId","Quantity","QuantitySold","CurrentPrice","AvgBuyPrice","RealizedProfitLoss","UnrealizedProfitLoss"};
        this.list = account.getAllPositions();
        Object[][] data = new Object[list.size()][8];
        for(int i = 0; i < list.size(); i++){
            data[i][0] = list.get(i).getAccountID();
            data[i][1] = list.get(i).getSecurityId();
            data[i][2] = list.get(i).getQuantity();
            data[i][3] = list.get(i).getQuantitySold();
            data[i][4] = list.get(i).getCurrentPrice();
            data[i][5] = list.get(i).getAvgBuyPrice();
            data[i][6] = list.get(i).getRealizedProfitLoss();
            data[i][7] = list.get(i).getUnrealizedProfitLoss();
        }
        positionTable = new JTable(data,columnNames);
        positionTable.setRowHeight(40);
        positionTable.setPreferredScrollableViewportSize(new Dimension(300,300));
        scrollPane = new JScrollPane(positionTable);
        container.setLayout(new BorderLayout());
        back = new JButton("Back");

        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();

    }
    private void setLocationAndSize(){
        back.setBounds(450,400,100,40);
    }

    private void addComponentsToContainer(){
        container.add(back);
        container.add(scrollPane, BorderLayout.CENTER);
        add(container);
    }

    private void addActionEvent(){
        back.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == back){
            dispose();
        }
    }
}
