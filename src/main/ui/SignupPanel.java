package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.*;

// Represents the visual user interface for the signup panel
public class SignupPanel extends JPanel {
    private JTextField nameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton signupButton;
    private JButton backButton;
    private MainGUI parent;

    // EFFECTS: construct a signup panel with input fields and role choosing between student and teacher
    @SuppressWarnings("methodlength")
    public SignupPanel(MainGUI parent) {
        this.parent = parent;
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Set insets for spacing

        nameField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        roleComboBox = new JComboBox<>(new String[]{"Student", "Teacher"});
        signupButton = new JButton("Signup");
        backButton = new JButton("Back");

        // Set preferred size for text fields, combo box, and buttons
        Dimension textFieldSize = new Dimension(200, 30);
        Dimension comboBoxSize = new Dimension(200, 30);
        Dimension buttonSize = new Dimension(100, 30);

        nameField.setPreferredSize(textFieldSize);
        usernameField.setPreferredSize(textFieldSize);
        passwordField.setPreferredSize(textFieldSize);
        roleComboBox.setPreferredSize(comboBoxSize);
        signupButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String enteredName = nameField.getText();
                String enteredUsername = usernameField.getText();
                String enteredPassword = new String(passwordField.getPassword());
                String selectedRole = (String) roleComboBox.getSelectedItem();

                if (isValidSignup(enteredName, enteredUsername, enteredPassword, selectedRole)) {
                    // Perform the signup action
                    if (selectedRole.equals("Student")) {
                        int nextId = parent.getStudentList().get(parent.getStudentList().size() - 1).getId() + 1;
                        Person s = new Student(nextId, enteredName, enteredUsername, enteredPassword);
                        parent.getPersonList().add(s);
                        parent.getStudentList().add((Student) s);
                        parent.setCurrentPerson(s);
                        parent.getContentPane().removeAll();
                        parent.getContentPane().add(new StudentPanel(parent, (Student) s));
                    } else if (selectedRole.equals("Teacher")) {
                        int nextId = parent.getTeacherList().get(parent.getTeacherList().size() - 1).getId() + 1;
                        Person t = new Teacher(nextId, enteredName, enteredUsername, enteredPassword);
                        parent.getPersonList().add(t);
                        parent.getTeacherList().add((Teacher) t);
                        parent.setCurrentPerson(t);
                        parent.getContentPane().removeAll();
                        parent.getContentPane().add(new TeacherPanel(parent, (Teacher) t));
                    }

                    //save Current State to JSON file
                    parent.saveState();
                    parent.revalidate();
                    parent.repaint();
                    JOptionPane.showMessageDialog(SignupPanel.this, "Signup successful!");
                    // Perform additional actions upon successful signup if needed
                } else {
                    JOptionPane.showMessageDialog(SignupPanel.this,
                            "Username already exists or fields are empty");
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
        add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(nameField, gbc);

        // Row 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(usernameField, gbc);

        // Row 3
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(passwordField, gbc);

        // Row 4
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Role:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(roleComboBox, gbc);

        // Row 5
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel(), gbc); // Empty label for spacing

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(signupButton, gbc);

        // Row 6
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel(), gbc); // Empty label for spacing

        gbc.gridx = 1;
        gbc.gridy = 5;
        add(backButton, gbc);
    }

    // EFFECTS: return true if entered fields are not empty and userName does not exist in personList
    private boolean isValidSignup(String enteredName, String enteredUsername,
                                  String enteredPassword, String selectedRole) {
        if (enteredName.isEmpty() || enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
            return false; // Fields are empty
        }

        for (Person person : parent.getPersonList()) {
            if (person.getUserName().equals(enteredUsername)) {
                return false; // Username already exists
            }
        }
        return true;
    }
}
