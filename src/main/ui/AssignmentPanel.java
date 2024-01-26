package ui;


import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.text.DecimalFormat;

// Represents the visual user interface for assignment panel
public class AssignmentPanel extends JPanel {
    private MainGUI parent;
    private Student student;
    private Assignment assignment;
    private Course course;

    // EFFECTS: construct an assignment panel with questions and input fields for answer and action buttons
    @SuppressWarnings("methodlength")
    public AssignmentPanel(MainGUI parent, Student student, Assignment assignment, Course course) {
        this.parent = parent;
        this.student = student;
        this.assignment = assignment;
        this.course = course;

        setLayout(new BorderLayout());

        // Display questions and answer input fields in a scrollable panel
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new GridBagLayout());

        List<Question> questions = assignment.getQuestionList();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        for (Question question : questions) {
            JLabel questionLabel = new JLabel("<html>" + question.getQuest() + "</html>");
            questionLabel.setPreferredSize(new Dimension(400, 50));
            questionLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JTextField answerTextField = new JTextField();
            answerTextField.setPreferredSize(new Dimension(400, 30));

            questionPanel.add(questionLabel, gbc);
            gbc.gridy++;
            questionPanel.add(answerTextField, gbc);
            gbc.gridy++;
        }

        JScrollPane scrollPane = new JScrollPane(questionPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Use FlowLayout for the button panel

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Question> questions = assignment.getQuestionList();
                Answers answers = new Answers(student, assignment);

                // Extract answers from text fields
                Component[] components = questionPanel.getComponents();
                for (int i = 1; i < components.length; i += 2) {
                    JTextField answerTextField = (JTextField) components[i];
                    answers.addAnswer(answerTextField.getText());
                }

                // Grade the answers, grade() already saves the grade to Student's grade list
                double grade = answers.grade();

                Grade result = new Grade(grade, assignment);

                // Save the newest state to the JSON data
                parent.saveState();
                // Notify the user (you can replace this with your desired feedback)

                // Format the double to two decimal places
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                String formattedValue = decimalFormat.format(grade);

                JOptionPane.showMessageDialog(AssignmentPanel.this, "Answers submitted. Grade: "
                        + formattedValue);

            }
        });

        buttonPanel.add(submitButton);


        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCoursePanel();
            }
        });

        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    // MODIFIES: this
    // EFFECTS: go back to course panel
    private void showCoursePanel() {
        CoursePanel coursePanel = new CoursePanel(parent, student, course);
        parent.getContentPane().removeAll();
        parent.getContentPane().add(coursePanel);
        parent.revalidate();
        parent.repaint();
    }
}