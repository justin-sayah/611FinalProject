package org.TradingSystem.views;

import org.TradingSystem.model.Customer;
import org.TradingSystem.model.Manager;

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
    private Manager manager;
    private Customer customer;
    private JLabel messageLabel;
    private JPanel customerInfoPanel;
    private JPanel topPanel;
    private JLabel customerName;
    private JPanel buttonPanel;
    private JPanel bottomPanel;

    public SendMessageFrame(Manager manager, Customer customer){
        setTitle("Send Message");
        setVisible(true);
        setLocation(10,10);
        setSize(1000,800);
        setResizable(false);
        container = new JPanel();
        container.setPreferredSize(new Dimension(1000,800));
        personIDLabel = new JLabel("   PersonID:");
        nameLabel = new JLabel("Name:");
        customerIDLabel = new JLabel("CustomerID:");
        customerName = new JLabel(customer.getLastName()+", "+customer.getFirstName());
        textLabel = new JLabel("Text");
        int personID = manager.getID();
        String name = manager.getFirstName()+" "+manager.getLastName();
        showIDLabel = new JLabel(personID + "");
        showNameLabel = new JLabel(name + "");
        personIDTextField = new JTextField();
        textTextField = new JTextField();
        textTextField.setPreferredSize(new Dimension(400,400));
        back = new JButton("BACK");
        back.setFont(new Font("Verdana", Font.PLAIN, 20));
        send = new JButton("SEND");
        send.setFont(new Font("Verdana", Font.PLAIN, 20));
        this.manager = manager;
        this.customer = customer;

        topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(1000,170));
        customerInfoPanel = new JPanel(new GridLayout(1,6));
        customerInfoPanel.setPreferredSize(new Dimension(1000,20));

        messageLabel = new JLabel("SEND MESSAGE",JLabel.CENTER);
        messageLabel.setFont(new Font("Verdana", Font.PLAIN, 50));
        messageLabel.setPreferredSize(new Dimension(1000,150));
        messageLabel.setForeground(Color.red);
        messageLabel.setOpaque(true);
        messageLabel.setBackground(Color.blue);

        buttonPanel = new JPanel(new GridLayout(2,1));
        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(550,550));

        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    private void setLocationAndSize(){
        customerInfoPanel.add(personIDLabel);
        customerInfoPanel.add(showIDLabel);
        customerInfoPanel.add(nameLabel);
        customerInfoPanel.add(showNameLabel);
        customerInfoPanel.add(customerIDLabel);
        customerInfoPanel.add(customerName);

        topPanel.add(messageLabel,BorderLayout.NORTH);
        topPanel.add(customerInfoPanel,BorderLayout.SOUTH);

        buttonPanel.add(send);
        buttonPanel.add(back);
        JPanel textField = new JPanel();
        textField.add(textLabel, BorderLayout.NORTH);
        textField.add(textTextField, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        bottomPanel.add(textField,BorderLayout.CENTER);

    }

    private void addComponentsToContainer(){

        container.add(topPanel);
        container.add(bottomPanel);
        add(container);
    }

    private void addActionEvent(){
        back.addActionListener(this);
        send.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            new CustomerInformationFrame(manager);
            dispose();
        }else if(e.getSource() == send){
            //String customerIDText = personIDTextField.getText();
            String textText = textTextField.getText();
            if(textText.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter a message!");
            }else {
                try {

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

}
