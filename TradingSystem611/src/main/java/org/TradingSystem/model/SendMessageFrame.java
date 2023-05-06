package org.TradingSystem.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SendMessageFrame extends JFrame implements ActionListener {
    private final JPanel container;
    private final JLabel personIDLabel;
    private final JLabel nameLabel;
    private final JLabel showIDLabel;
    private final JLabel showNameLabel;
    private final JLabel customerIDLabel;
    private final JLabel textLabel;
    private final JTextField personIDTextField;
    private final JTextField textTextField;
    private final JButton back;
    private final JButton send;
    private final Manager manager;
    private PeopleDao dao;

    public SendMessageFrame(Manager manager){
        setTitle("Send Message");
        setVisible(true);
        setLocation(10,10);
        setSize(1000,600);
        setResizable(false);
        container = new JPanel();
        personIDLabel = new JLabel("PersonID");
        nameLabel = new JLabel("Name");
        customerIDLabel = new JLabel("CustomerID");
        textLabel = new JLabel("Text");
        int personID = manager.getID();
        String name = manager.getFirstName()+" "+manager.getLastName();
        showIDLabel = new JLabel(personID + "");
        showNameLabel = new JLabel(name + "");
        personIDTextField = new JTextField();
        textTextField = new JTextField();
        back = new JButton("Back");
        send = new JButton("Send");
        this.manager = manager;
        dao = new PeopleDao();
        container.setLayout(null);
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    private void setLocationAndSize(){
        personIDLabel.setBounds(100, 50, 100, 30);
        nameLabel.setBounds(300, 50, 100, 30);
        showIDLabel.setBounds(100,100,100,30);
        showNameLabel.setBounds(300,100,100,30);
        customerIDLabel.setBounds(150, 150, 100, 40);
        textLabel.setBounds(150,200,50,40);
        personIDTextField.setBounds(260,150,200,40);
        textTextField.setBounds(210,200,600,40);
        back.setBounds(800,50,100,40);
        send.setBounds(800,500,100,40);
    }

    private void addComponentsToContainer(){
        container.add(personIDLabel);
        container.add(nameLabel);
        container.add(showIDLabel);
        container.add(showNameLabel);
        container.add(textLabel);
        container.add(customerIDLabel);
        container.add(personIDTextField);
        container.add(textTextField);
        container.add(send);
        container.add(back);
        add(container);
    }

    private void addActionEvent(){
        back.addActionListener(this);
        send.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            new ManagerFrame(manager);
            dispose();
        }else if(e.getSource() == send){
            String customerIDText = personIDTextField.getText();
            String textText = textTextField.getText();
            if(customerIDText.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter a customer ID!");
            }else if(textText.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter a message!");
            }else {
                try {
                    int cid = Integer.parseInt(customerIDText);
                    Customer customer = dao.getCustomer(cid);
                    // Todo
                    if(customer == null){
                        JOptionPane.showMessageDialog(this, "Cannot find the customer!");
                    }else{
                        manager.sendMessage(customer, textText);
                        JOptionPane.showMessageDialog(this, "Message sent successfully!");
                    }


                } catch (Exception ex) {
                    //JOptionPane.showMessageDialog(this, ex.toString());
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args){
        // Todo
        int ID = 1;
        PeopleDao dao = new PeopleDao();
        Manager manager = dao.getManager(ID);
        SendMessageFrame frame = new SendMessageFrame(manager);
        frame.setVisible(true);
    }
}
