package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the visual user interface for the home login and signup panel
public class LoginSignupPanel extends JPanel {
    private JButton loginButton;
    private JButton signupButton;
    private MainGUI parent;

    // EFFECTS: construct a login signup panel with 2 buttons - login and signup
    @SuppressWarnings("methodlength")
    public LoginSignupPanel(MainGUI parent) {
        this.parent = parent;
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Set insets to provide some spacing

        loginButton = new JButton("Login");
        signupButton = new JButton("Signup");

        // Set preferred size for buttons
        Dimension buttonSize = new Dimension(100, 30);
        loginButton.setPreferredSize(buttonSize);
        signupButton.setPreferredSize(buttonSize);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showLoginPanel();
            }
        });

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showSignupPanel();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(loginButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(signupButton, gbc);
    }

}