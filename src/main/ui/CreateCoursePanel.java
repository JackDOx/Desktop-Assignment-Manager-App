package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the visual user interface for the Create Course panel form
public class CreateCoursePanel extends JPanel {
    private MainGUI parent;
    private Teacher teacher;

    private JTextField courseNameField;
    private JTextArea courseDescriptionArea;

    // EFFECTS: construct a create-course panel with 2 text fields and action buttons
    @SuppressWarnings("methodlength")
    public CreateCoursePanel(MainGUI parent, Teacher teacher) {
        this.parent = parent;
        this.teacher = teacher;

        setLayout(new BorderLayout());

        // Create Course form
        JPanel formPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding

        JLabel nameLabel = new JLabel("Course Name:");
        courseNameField = new JTextField();
        courseNameField.setPreferredSize(new Dimension(200, 30));
        JLabel descriptionLabel = new JLabel("Course Description:");
        courseDescriptionArea = new JTextArea();
        // Make the course description automatically wrap when reaches input box border
        courseDescriptionArea.setLineWrap(true);
        courseDescriptionArea.setWrapStyleWord(true);

        courseDescriptionArea.setPreferredSize(new Dimension(200, 100));

        // Use GridBagConstraints to control the layout
        formPanel.add(nameLabel, gbc);
        gbc.gridy++;
        formPanel.add(courseNameField, gbc);
        gbc.gridy++;
        formPanel.add(descriptionLabel, gbc);
        gbc.gridy++;
        gbc.weighty = 1.0; // Allow vertical expansion
        formPanel.add(new JScrollPane(courseDescriptionArea), gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Use FlowLayout for the button panel

        // Create Course button
        JButton createCourseButton = new JButton("Create Course");
        createCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseName = courseNameField.getText();
                String courseDescription = courseDescriptionArea.getText();

                // Implement logic to create a new course and add it to the teacher's courses
                Course newCourse = new Course(courseName, courseDescription);
                teacher.addCourse(newCourse);
                parent.addCourse(newCourse);
                parent.saveState();

                // Show a message or go back to the teacher panel
                JOptionPane.showMessageDialog(CreateCoursePanel.this, "Course created successfully!");
                showTeacherPanel();
            }
        });

        buttonPanel.add(createCourseButton);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTeacherPanel();
            }
        });

        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: go back to Teacher panel
    private void showTeacherPanel() {
        TeacherPanel panel = new TeacherPanel(parent, teacher);
        parent.getContentPane().removeAll();
        parent.getContentPane().add(panel);
        parent.revalidate();
        parent.repaint();
    }
}
