package org.TradingSystem.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerFrame extends JFrame implements ActionListener {
    private final JPanel container;
    private final JLabel personIDLabel;
    private final JLabel nameLabel;
    private final JLabel showIDLabel;
    private final JLabel showNameLabel;

    private final JButton manageAccount;
    private final JButton viewCustomerInformation;
    private final JButton manageMarket;
    private final JButton sendMessage;
    private final JButton logout;
    private PeopleDao peopleDao;
    private Manager manager;
    private JPanel topPanel;
    private JPanel customerInfoPanel;
    private JLabel customerAccountLabel;
    private JPanel bottomPanel;
    private JPanel buttonPanel;

    public ManagerFrame(Manager manager1){
        setTitle("Manager");
        setVisible(true);
        setLocation(10,10);
        setSize(1000,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        container = new JPanel();
        container.setPreferredSize(new Dimension(1000,800));
        topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(1000,170));
        customerInfoPanel = new JPanel(new GridLayout(1,4));
        customerInfoPanel.setPreferredSize(new Dimension(1000,20));
        customerAccountLabel = new JLabel("MANAGER CENTER",JLabel.CENTER);
        customerAccountLabel.setFont(new Font("Verdana", Font.PLAIN, 50));
        customerAccountLabel.setPreferredSize(new Dimension(1000,150));
        customerAccountLabel.setForeground(Color.red);
        customerAccountLabel.setOpaque(true);
        customerAccountLabel.setBackground(Color.blue);
        buttonPanel = new JPanel(new GridLayout(5,1));
        buttonPanel.setPreferredSize(new Dimension(500,450));

        personIDLabel = new JLabel("  PersonID");
        personIDLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
        nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Verdana", Font.PLAIN, 15));

        manageAccount = new JButton("MANAGE ACCOUNT");
        manageAccount.setFont(new Font("Verdana", Font.PLAIN, 20));
        viewCustomerInformation = new JButton("VIEW CUSTOMER INFORMATION");
        viewCustomerInformation.setFont(new Font("Verdana", Font.PLAIN, 20));
        manageMarket = new JButton("MANAGE MARKET");
        manageMarket.setFont(new Font("Verdana", Font.PLAIN, 20));
        sendMessage = new JButton("SEND MESSAGE");
        sendMessage.setFont(new Font("Verdana", Font.PLAIN, 20));
        logout = new JButton("LOGOUT");
        logout.setFont(new Font("Verdana", Font.PLAIN, 20));
        peopleDao = new PeopleDao();
        manager = manager1;
        int personID = manager.getID();
        String name = manager.getFirstName()+" "+manager.getLastName();
        showIDLabel = new JLabel(personID + "");
        showIDLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
        showNameLabel = new JLabel(name + "");
        showNameLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
        //setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();

    }



    private void addComponentsToContainer(){
        topPanel.add(customerAccountLabel,BorderLayout.NORTH);
        customerInfoPanel.add(personIDLabel);
        customerInfoPanel.add(showIDLabel);
        customerInfoPanel.add(nameLabel);
        customerInfoPanel.add(showNameLabel);
        topPanel.add(customerInfoPanel,BorderLayout.SOUTH);
        buttonPanel.add(manageAccount);
        buttonPanel.add(viewCustomerInformation);
        buttonPanel.add(manageMarket);
        buttonPanel.add(sendMessage);
        buttonPanel.add(logout);
        container.add(topPanel,BorderLayout.NORTH);
        container.add(buttonPanel);
        add(container);
    }

    private void addActionEvent(){
        manageAccount.addActionListener(this);
        viewCustomerInformation.addActionListener(this);
        manageMarket.addActionListener(this);
        sendMessage.addActionListener(this);
        logout.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == manageAccount) {
            new ManageAccountFrame(manager);
            dispose();
        }else if(e.getSource() == viewCustomerInformation){
            new CustomerInformationFrame(manager);
            dispose();
        }else if(e.getSource() == manageMarket){
            new ManageMarketFrame(manager);
            dispose();
        }else if(e.getSource() == sendMessage){
            //new SendMessageFrame(manager);
            dispose();
        }else if(e.getSource() == logout){
            new LoginFrame();
            this.setVisible(false);
        }
    }
}
