package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the visual user interface for the Teacher panel with course buttons and other action buttons
public class TeacherPanel extends JPanel {
    private MainGUI parent;
    private Teacher teacher;

    // EFFECTS: Construct a Teacher Panel with teacher information on top, list of courses in center and action buttons
    @SuppressWarnings("methodlength")
    public TeacherPanel(MainGUI parent, Teacher teacher) {
        this.parent = parent;
        this.teacher = teacher;

        setLayout(new BorderLayout());

        // Display teacher information
        JTextArea teacherInfoTextArea = new JTextArea();
        teacherInfoTextArea.append("Name: " + teacher.getName() + "\n");
        teacherInfoTextArea.append("Username: " + teacher.getUserName() + "\n");
        teacherInfoTextArea.append("Role: " + teacher.getRole() + "\n");
        teacherInfoTextArea.append("ID: " + teacher.getId() + "\n");

        add(teacherInfoTextArea, BorderLayout.NORTH);

        // Display courses
        JPanel courseButtonPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding

        int buttonWidth = 150;
        int buttonHeight = 30;

        for (Course course : teacher.getCourse()) {
            JButton courseButton = new JButton(course.getName());
            courseButton.addActionListener(new CourseButtonListener(course));
            courseButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            courseButtonPanel.add(courseButton, gbc);
            gbc.gridy++;
        }

        add(new JScrollPane(courseButtonPanel), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Use FlowLayout for the button panel

        // Create new Course button
        JButton createCourseButton = new JButton("Create new Course");
        createCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateCoursePanel panel = new CreateCoursePanel(parent, teacher);
                parent.getContentPane().removeAll();
                parent.getContentPane().add(panel);
                parent.revalidate();
                parent.repaint();
            }
        });

        buttonPanel.add(createCourseButton);

        // Back button
        JButton backButton = new JButton("Log out");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove existing components and show the login/signup panel
                parent.getContentPane().removeAll();
                parent.showLoginPanel();
                parent.revalidate();
                parent.repaint();
            }
        });

        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Creates a Course Button Listener based on Action Listener
    private class CourseButtonListener implements ActionListener {
        private Course course;

        // EFFECTS: Construct a course listener
        public CourseButtonListener(Course course) {
            this.course = course;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Open the CoursePanel for the selected course
            TeacherCoursePanel coursePanel = new TeacherCoursePanel(parent, teacher, course);
            parent.getContentPane().removeAll();
            parent.getContentPane().add(coursePanel);
            parent.revalidate();
            parent.repaint();
        }
    }
}