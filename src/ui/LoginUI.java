package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;

    public LoginUI() {

        setTitle("Login");
        setSize(300, 200);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(20, 20, 80, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 20, 150, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(20, 60, 80, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 60, 150, 25);
        add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(100, 100, 100, 30);
        add(loginBtn);

        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                UserDAO dao = new UserDAO();
                User user = dao.login(username, password);

                if (user != null) {

                    JOptionPane.showMessageDialog(null, "Login Successful!");

                    // Use role
                    if (user.getRole().equals("ADMIN")) {
                        new AdminUI();
                    } else if (user.getRole().equals("USER")) {
                        new UserUI();
                    } else {
                        new TeamUI(user);
                    }

                    dispose();

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Credentials");
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginUI();
    }
}