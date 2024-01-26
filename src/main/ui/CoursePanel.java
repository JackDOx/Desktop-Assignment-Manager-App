package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import model.*;

// Represents the visual user interface for course panel
public class CoursePanel extends JPanel {
    private MainGUI parent;
    private Student student;
    private Course course;

    // EFFECTS: construct a course panel with list of course buttons in center and student's information on top
    @SuppressWarnings("methodlength")
    public CoursePanel(MainGUI parent, Student student, Course course) {
        this.parent = parent;
        this.student = student;
        this.course = course;

        setLayout(new BorderLayout());

        // Display course information
        JTextArea courseInfoTextArea = new JTextArea();
        courseInfoTextArea.append("Course ID: " + course.getId() + "\n");
        courseInfoTextArea.append("Course Name: " + course.getName() + "\n");
        courseInfoTextArea.append("Description: " + course.getDescription() + "\n");

        // Format the double to two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        // Print out Grades
        for (Grade g : student.getGrades()) {
            String formattedValue = decimalFormat.format(g.getGrade());
            courseInfoTextArea.append("Grade for " + g.getAssignment().getName() + ": "
                    + formattedValue);
        }

        add(courseInfoTextArea, BorderLayout.NORTH);

        // Display assignments
        JPanel assignmentPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding

        int buttonWidth = 150; // Set the width to your desired constant size
        int buttonHeight = 30; // Set the height to your desired constant size

        for (Assignment assignment : course.getAssignment()) {
            JButton assignmentButton = new JButton(assignment.getName());
            assignmentButton.addActionListener(new AssignmentButtonListener(assignment));
            assignmentButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            assignmentPanel.add(assignmentButton, gbc);
            gbc.gridy++;
        }

        add(new JScrollPane(assignmentPanel), BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStudentPanel();
            }
        });

        add(backButton, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: go back to the student panel
    private void showStudentPanel() {
        StudentPanel studentPanel = new StudentPanel(parent, student);
        parent.getContentPane().removeAll();
        parent.getContentPane().add(studentPanel);
        parent.revalidate();
        parent.repaint();
    }


    // The assignment button listener based on action listener
    private class AssignmentButtonListener implements ActionListener {
        private Assignment assignment;

        // EFFECTS: construct an assignment button listener
        public AssignmentButtonListener(Assignment assignment) {
            this.assignment = assignment;
        }

        // MODIFIES: this
        // EFFECTS: go to the assignment panel that belongs to this.assignment
        @Override
        public void actionPerformed(ActionEvent e) {
            // Open the AssignmentPanel for the selected assignment
            AssignmentPanel assignmentPanel = new AssignmentPanel(parent, student, assignment, course);
            parent.getContentPane().removeAll();
            parent.getContentPane().add(assignmentPanel);
            parent.revalidate();
            parent.repaint();
        }
    }
}