package org.TradingSystem.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MessagePopup extends JDialog {

    public MessagePopup(CustomerHomePage customerHomePage, Customer customer)  {

        JButton deleteButton = new JButton("Delete");
        JTextArea message = new JTextArea("");
        List<Message> getMessagesInInbox = MessageCenter.getInstance().getMessagesInInbox(customer.getID());
        for(Message messagedisplay:getMessagesInInbox ){
            message.append(String.valueOf(messagedisplay));
        }
        // Configure the layout of the pop-up window
        setLayout(new FlowLayout());
        add(message);
        add(deleteButton);
        setSize(300, 150);
        setLocationRelativeTo(customerHomePage);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform the deposit action
                for(Message messagedisplay:getMessagesInInbox ){
                    MessageCenter.getInstance().deleteMessage(messagedisplay);
                }

            }

        });



    }


}
