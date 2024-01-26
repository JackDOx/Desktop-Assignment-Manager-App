package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.*;

// Represents the visual user interface for login panel
public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private MainGUI parent;

    // EFFECTS: construct a login panel with 2 text fields (username and password) and action buttons
    @SuppressWarnings("methodlength")
    public LoginPanel(MainGUI parent) {
        this.parent = parent;
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Set insets for spacing

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        backButton = new JButton("Back");

        // Set preferred size for text fields and buttons
        Dimension textFieldSize = new Dimension(200, 30);
        Dimension buttonSize = new Dimension(100, 30);

        usernameField.setPreferredSize(textFieldSize);
        passwordField.setPreferredSize(textFieldSize);
        loginButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String enteredUsername = usernameField.getText();
                String enteredPassword = new String(passwordField.getPassword());

                if (isValidLogin(enteredUsername, enteredPassword)) {
                    Person p = findPerson(parent.getPersonList(), enteredUsername, enteredPassword);
                    parent.setCurrentPerson(p);
                    parent.getContentPane().removeAll();

                    // Load the new Panel based on user type
                    if (p instanceof Student) {
                        parent.getContentPane().add(new StudentPanel(parent, (Student) p));
                    } else if (p instanceof Teacher) {
                        parent.getContentPane().add(new TeacherPanel(parent, (Teacher) p));
                    }
                    parent.revalidate();
                    parent.repaint();
                    JOptionPane.showMessageDialog(LoginPanel.this, "Login successful!");
                } else {
                    JOptionPane.showMessageDialog(LoginPanel.this,
                            "Invalid username or password");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showMainPanel();
            }
        });

        // Row 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        // Row 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        // Row 3
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel(), gbc); // Empty label for spacing

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(loginButton, gbc);

        // Row 4
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel(), gbc); // Empty label for spacing

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(backButton, gbc);
    }

    // EFFECTS: return the person if in the list of person
    private Person findPerson(List<Person> lop, String username, String password) {
        Person result = null;
        for (Person p : lop) {
            if (p.getUserName().equals(username) && p.getPassword().equals(password)) {
                result = p;
                break;
            }
        }
        return result;
    }

    // EFFECTS: return true if the entered username and password does belong to a person
    private boolean isValidLogin(String enteredUsername, String enteredPassword) {
        for (Person person : parent.getPersonList()) {
            if (person instanceof Student) {
                Student student = (Student) person;
                if (student.getUserName().equals(enteredUsername) && student.getPassword().equals(enteredPassword)) {
                    return true;
                }
            } else if (person instanceof Teacher) {
                Teacher teacher = (Teacher) person;
                if (teacher.getUserName().equals(enteredUsername) && teacher.getPassword().equals(enteredPassword)) {
                    return true;
                }
            }
        }
        return false;
    }
}
