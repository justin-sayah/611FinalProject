package org.TradingSystem.model;

import javax.swing.*;
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

    public ManagerFrame(Manager manager1){
        setTitle("Manager");
        setVisible(true);
        setLocation(10,10);
        setSize(1000,700);
        setResizable(false);
        container = new JPanel();
        personIDLabel = new JLabel("PersonID");
        nameLabel = new JLabel("Name");

        manageAccount = new JButton("Manage Account");
        viewCustomerInformation = new JButton("View Customer Information");
        manageMarket = new JButton("Manage Market");
        sendMessage = new JButton("Send Message");
        logout = new JButton("Logout");
        peopleDao = new PeopleDao();
        container.setLayout(null);
        manager = manager1;
        int personID = manager.getID();
        String name = manager.getFirstName()+" "+manager.getLastName();
        showIDLabel = new JLabel(personID + "");
        showNameLabel = new JLabel(name + "");
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();

    }

    private void setLocationAndSize() {
        personIDLabel.setBounds(100, 50, 100, 30);
        nameLabel.setBounds(300, 50, 100, 30);
        showIDLabel.setBounds(100,100,100,30);
        showNameLabel.setBounds(300,100,100,30);
        manageAccount.setBounds(150,250,200,40);
        viewCustomerInformation.setBounds(150, 400, 200, 40);
        manageMarket.setBounds(150, 500, 200, 40);
        sendMessage.setBounds(150, 600, 200, 40);
        logout.setBounds(800,50,100,30);
    }

    private void addComponentsToContainer(){
        container.add(personIDLabel);
        container.add(nameLabel);
        container.add(showIDLabel);
        container.add(showNameLabel);
        container.add(manageAccount);
        container.add(viewCustomerInformation);
        container.add(manageMarket);
        //container.add(sendMessage);
        container.add(logout);
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
