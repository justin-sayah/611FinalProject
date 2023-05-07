package org.TradingSystem.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame implements ActionListener {

    private final JPanel container;
    private final JLabel title;
    private final JLabel userLabel;
    private final JLabel passwordLabel;
    private final JTextField userTextField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton signupButton;
    private final JCheckBox showPassword;
    private PeopleDao peopleDao;

    private final JButton forgetPasswordButton;

//    private JRadioButton radioButton1;
//    private JPanel panel1;


    public LoginFrame() {
        peopleDao = new PeopleDao();
        setTitle("Login");
        setVisible(true);
        setLocation(10,10);
        setSize(1000,800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);//User cannot resize the frame
//        container = getContentPane();
        container = new JPanel(new BorderLayout());
        userLabel = new JLabel("USERNAME");
        userLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        passwordLabel = new JLabel("PASSWORD");
        passwordLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        userTextField = new JTextField();
        passwordField = new JPasswordField();
        title = new JLabel("Welcome to BU Stock Market",JLabel.CENTER);
        title.setForeground(Color.red);
        title.setFont(new Font("Verdana", Font.PLAIN, 50));
        title.setBounds(0,0,1000,200);
        title.setOpaque(true);
        title.setBackground(Color.BLUE);

        loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Verdana", Font.PLAIN, 15));
        signupButton = new JButton("SIGN UP");
        signupButton.setFont(new Font("Verdana", Font.PLAIN, 15));
        showPassword = new JCheckBox("Show Password");
        showPassword.setFont(new Font("Verdana", Font.PLAIN, 12));
        forgetPasswordButton  = new JButton("Reset Password");
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    private void addActionEvent() {
        loginButton.addActionListener(this);
        signupButton.addActionListener(this);
        showPassword.addActionListener(this);
    }

    private void addComponentsToContainer() {
        container.add(title);
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(signupButton);
        add(container);
    }

    private void setLocationAndSize() {
        userLabel.setBounds(250, 250, 200, 50);
        passwordLabel.setBounds(250, 400, 200, 50);
        userTextField.setBounds(550, 250, 200, 40);
        passwordField.setBounds(550, 400, 200, 40);
        showPassword.setBounds(550, 450, 200, 40);
        loginButton.setBounds(270, 600, 100, 40);
        signupButton.setBounds(600, 600, 100, 40);
    }

    private void setLayoutManager() {
        container.setLayout(null);
    }

    public void signup() {
        SignUpFrame signUpFrame = new SignUpFrame();
        signUpFrame.setVisible(true);
        this.setVisible(false);

    }

    //    TODO
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String userText = userTextField.getText();
            String pwdText = String.valueOf(passwordField.getPassword());

            if(userText.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter a username");
            }
            else if(pwdText.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter a password");
            }
            else {
                // replace this with your own code to check the username and password
                if(userText.equals("admin") && pwdText.equals("password")){
                    JOptionPane.showMessageDialog(this, "Login Successful");
                    Manager manager = new Manager(0000,"ad","min","admin","password","1","1");
                    new ManagerFrame(manager);
                    dispose();
                    // replace this with your own code to open the main menu or dashboard
                } else if(peopleDao.login(userText,pwdText)!= null){
                    JOptionPane.showMessageDialog(this, "Login Successful");
                    Customer customer = Customer.customerLogin(userText,pwdText);
                    new CustomerHomePage(customer);
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password");
                }
            }
        }

        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }

        if (e.getSource() == signupButton) {
            signup();
        }
    }

    public static void main(String[] args) {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
    }

}