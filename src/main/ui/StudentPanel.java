package ui;

import model.Student;
import model.Course;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the visual user interface for student panel
public class StudentPanel extends JPanel {
    private MainGUI parent;
    private Student student;

    // EFFECTS: construct a student panel with list of Courses in center and action buttons
    @SuppressWarnings("methodlength")
    public StudentPanel(MainGUI parent, Student student) {
        this.parent = parent;
        this.student = student;

        setLayout(new BorderLayout());

        // Display student information
        JTextArea studentInfoTextArea = new JTextArea();
        studentInfoTextArea.append("Name: " + student.getName() + "\n");
        studentInfoTextArea.append("Username: " + student.getUserName() + "\n");
        studentInfoTextArea.append("Role: " + student.getRole() + "\n");
        studentInfoTextArea.append("ID: " + student.getId() + "\n");
        studentInfoTextArea.append("Overall:" + Double.toString(student.calculateOverall()) + "\n");

        add(studentInfoTextArea, BorderLayout.NORTH);

        // Display enrolled courses
        JPanel courseButtonPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding

        int buttonWidth = 150;
        int buttonHeight = 30;

        for (Course course : parent.getCourseList()) {
            if (course.getStudent().contains(student)) {
                JButton courseButton = new JButton(course.getName());
                courseButton.addActionListener(new CourseButtonListener(course));
                courseButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                courseButtonPanel.add(courseButton, gbc);
                gbc.gridy++;
            }
        }

        add(new JScrollPane(courseButtonPanel), BorderLayout.CENTER);

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

        add(backButton, BorderLayout.SOUTH);
    }

    // Creates a Course Button Listener based on Action Listener
    private class CourseButtonListener implements ActionListener {
        private Course course;

        // EFFECTS: Construct a course button listener
        public CourseButtonListener(Course course) {
            this.course = course;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Open the CoursePanel for the selected course
            CoursePanel coursePanel = new CoursePanel(parent, student, course);
            parent.getContentPane().removeAll();
            parent.getContentPane().add(coursePanel);
            parent.revalidate();
            parent.repaint();
        }
    }
}