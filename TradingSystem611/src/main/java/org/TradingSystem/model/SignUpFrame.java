package org.TradingSystem.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;


public class SignUpFrame extends JFrame implements ActionListener {

    private final JPanel container;
    private final JLabel firstNameLabel;
    private final JLabel lastNameLabel;
    private final JLabel usernameLabel;
    private final JLabel passwordLabel;
    private final JLabel passwordConfirmLabel;
    private final JLabel dateOfBirthLabel;
    private final JLabel ssnLabel;
    private final JTextField firstName;
    private final JTextField lastName;
    private final JTextField dateOfBirth;
    private final JTextField ssn;
    private final JTextField username;
    private final JPasswordField passwordConfirm;
    private final JPasswordField password;
    private final JButton createButton;
    private final JButton resetButton;
    private final JButton backButton;
    private PeopleDao peopleDao;

    SignUpFrame(){
        peopleDao = new PeopleDao();
        setTitle("Sign Up");
        setLocation(10,10);
        setSize(1000,800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);//User cannot resize the frame
        container = new JPanel();
        firstNameLabel = new JLabel("First Name");
        lastNameLabel = new JLabel("Last Name");
        dateOfBirthLabel = new JLabel("Date of Birth (DD/MM/YYYY)");
        ssnLabel = new JLabel("SSN");
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        passwordConfirmLabel = new JLabel("Confirm password");
        firstName = new JTextField();
        lastName = new JTextField();
        dateOfBirth = new JTextField();
        ssn = new JTextField();
        username = new JTextField();
        passwordConfirm = new JPasswordField();
        password = new JPasswordField();
        createButton = new JButton("CREATE ACCOUNT");
        resetButton = new JButton("RESET");
        backButton = new JButton("BACK");
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    private void addActionEvent() {
        createButton.addActionListener(this);
        resetButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    private void addComponentsToContainer() {
        container.add(firstNameLabel);
        container.add(lastNameLabel);
        container.add(dateOfBirthLabel);
        container.add(ssnLabel);
        container.add(usernameLabel);
        container.add(passwordLabel);
        container.add(passwordConfirmLabel);
        container.add(firstName);
        container.add(lastName);
        container.add(dateOfBirth);
        container.add(ssn);
        container.add(username);
        container.add(password);
        container.add(passwordConfirm);
        container.add(createButton);
        container.add(resetButton);
        container.add(backButton);
        add(container);
    }

    private void setLocationAndSize() {
        firstNameLabel.setBounds(300, 250, 100, 40);
        lastNameLabel.setBounds(300, 300, 100, 40);
        dateOfBirthLabel.setBounds(300,350,250,40);
        ssnLabel.setBounds(300,400,200,40);
        usernameLabel.setBounds(300, 450, 100, 40);
        passwordLabel.setBounds(300, 500, 100, 40);
        passwordConfirmLabel.setBounds(300, 550, 150, 40);
        firstName.setBounds(550, 250, 150, 30);
        lastName.setBounds(550, 300, 150, 30);
        ssn.setBounds(550,350,150,30);
        dateOfBirth.setBounds(550,400,150,30);
        username.setBounds(550, 450, 150, 30);
        password.setBounds(550, 500, 150, 30);
        passwordConfirm.setBounds(550, 550, 150, 30);
        createButton.setBounds(250, 600, 200, 35);
        resetButton.setBounds(550, 600, 100, 35);
        backButton.setBounds(750,600,200,35);
    }

    private void setLayoutManager() {
        container.setLayout(null);
    }

    private void reset(){
        firstName.setText("");
        lastName.setText("");
        dateOfBirth.setText("");
        ssn.setText("");
        username.setText("");
        password.setText("");
        passwordConfirm.setText("");
    }

    public void login(){
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
        this.setVisible(false);
    }

    //    TODO
    @Override
    public void actionPerformed(ActionEvent e) {
        //Coding Part of create button
        if (e.getSource() == createButton) {
            String firstname;
            String lastname;
            String dob;
            String ssnText;
            String userName;
            String pw;
            String pc;
            firstname = firstName.getText();
            lastname = lastName.getText();
            ssnText = ssn.getText();
            userName = username.getText();
            dob = dateOfBirth.getText();
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
            else if(dob.isEmpty()){
                JOptionPane.showMessageDialog(this,"Please enter your date of birth");
            }
            else if(ssnText.isEmpty()){
                JOptionPane.showMessageDialog(this,"Please enter your ssn");
            }
            else if(!Objects.equals(pc, pw)){
                JOptionPane.showMessageDialog(this, "Passwords do not match. Please re-enter passwords");
                password.setText("");
                passwordConfirm.setText("");
            }else if(peopleDao.createCustomer(lastname,userName,pw,dob,ssnText)){
                JOptionPane.showMessageDialog(this, "Sign up Successful");
                dispose();
                new LoginFrame();
            }
//            else{
//                // Replace this with your own code to handle sign up
//                JOptionPane.showMessageDialog(this,
//                        "Account created! Please login to your account");
//                dispose();
//                new LoginFrame();
//            }
        }
        //Coding Part of reset button
        if (e.getSource() == resetButton) {
            reset();
        }
        if(e.getSource() == backButton){
            login();
        }
    }


}