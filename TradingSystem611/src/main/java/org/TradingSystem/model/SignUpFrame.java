package org.TradingSystem.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;


public class SignUpFrame extends JFrame implements ActionListener {

    private final Container container;
    private final JLabel firstNameLabel;
    private final JLabel lastNameLabel;
    private final JLabel usernameLabel;
    private final JLabel passwordLabel;
    private final JLabel passwordConfirmLabel;
    private final JTextField firstName;
    private final JTextField lastName;
    private final JTextField username;
    private final JPasswordField passwordConfirm;
    private final JPasswordField password;
    private final JButton createButton;
    private final JButton resetButton;

    SignUpFrame(){
        setTitle("Sign Up");
        setVisible(true);
        setLocation(10,10);
        setSize(1000,800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);//User cannot resize the frame
        container = getContentPane();
        firstNameLabel = new JLabel("First Name");
        lastNameLabel = new JLabel("Last Name");
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        passwordConfirmLabel = new JLabel("Confirm password");
        firstName = new JTextField();
        lastName = new JTextField();
        username = new JTextField();
        passwordConfirm = new JPasswordField();
        password = new JPasswordField();
        createButton = new JButton("CREATE ACCOUNT");
        resetButton = new JButton("RESET");
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    private void addActionEvent() {
        createButton.addActionListener(this);
        resetButton.addActionListener(this);
    }

    private void addComponentsToContainer() {
        container.add(firstNameLabel);
        container.add(lastNameLabel);
        container.add(usernameLabel);
        container.add(passwordLabel);
        container.add(passwordConfirmLabel);
        container.add(firstName);
        container.add(lastName);
        container.add(username);
        container.add(password);
        container.add(passwordConfirm);
        container.add(createButton);
        container.add(resetButton);
    }

    private void setLocationAndSize() {
        firstNameLabel.setBounds(300, 250, 100, 40);
        lastNameLabel.setBounds(300, 300, 100, 40);
        usernameLabel.setBounds(300, 350, 100, 40);
        passwordLabel.setBounds(300, 400, 100, 40);
        passwordConfirmLabel.setBounds(300, 450, 150, 40);
        firstName.setBounds(550, 250, 150, 30);
        lastName.setBounds(550, 300, 150, 30);
        username.setBounds(550, 350, 150, 30);
        password.setBounds(550, 400, 150, 30);
        passwordConfirm.setBounds(550, 450, 150, 30);
        createButton.setBounds(300, 500, 200, 35);
        resetButton.setBounds(600, 500, 100, 35);
    }

    private void setLayoutManager() {
        container.setLayout(null);
    }

    private void reset(){
        firstName.setText("");
        lastName.setText("");
        username.setText("");
        password.setText("");
        passwordConfirm.setText("");
    }


    //    TODO
    @Override
    public void actionPerformed(ActionEvent e) {
        //Coding Part of create button
        if (e.getSource() == createButton) {
            String firstname;
            String lastname;
            String userName;
            String pw;
            String pc;
            firstname = firstName.getText();
            lastname = lastName.getText();
            userName = username.getText();
            pw = password.getText();
            pc = passwordConfirm.getText();
            if(firstname.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter a first name");
            }
            else if(lastname.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter a last name");
            }
            else if(userName.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter a username");
            }
            else if(pw.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter a password");
            }
            else if(!Objects.equals(pc, pw)){
                JOptionPane.showMessageDialog(this, "Passwords do not match. Please re-enter passwords");
                password.setText("");
                passwordConfirm.setText("");
            }
            else{
                // Replace this with your own code to handle sign up
                JOptionPane.showMessageDialog(this,
                        "Account created! Please login to your account");
                dispose();
                new LoginFrame();
            }
        }
        //Coding Part of reset button
        if (e.getSource() == resetButton) {
            reset();
        }
    }

    public static void main(String[] args) {
        SignUpFrame signUpFrame = new SignUpFrame();
        signUpFrame.setVisible(true);
    }
}