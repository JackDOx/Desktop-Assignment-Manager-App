package ui;

import model.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

// Represents the visual interface for Teacher's specific Course panel
public class TeacherCoursePanel extends JPanel {
    private MainGUI parent;
    private Teacher teacher;
    private Course course;
    private JTable studentTable;

    // EFFECTS: construct a course panel for teacher with student table on right and actions buttons
    @SuppressWarnings("methodlength")
    public TeacherCoursePanel(MainGUI parent, Teacher teacher, Course course) {
        this.parent = parent;
        this.teacher = teacher;
        this.course = course;

        setLayout(new BorderLayout());

        // Display course information
        JTextArea courseInfoTextArea = new JTextArea();
        courseInfoTextArea.append("Course ID: " + course.getId() + "\n");
        courseInfoTextArea.append("Course Name: " + course.getName() + "\n");
        courseInfoTextArea.append("Description: " + course.getDescription() + "\n");
        courseInfoTextArea.append("Number of students enrolled: " + course.getStudent().size() + "\n");

        add(courseInfoTextArea, BorderLayout.NORTH);

        // Display student information in a chart using JTable
        studentTable = createStudentTable();
        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);

        // Create Assignment buttons
        JPanel assignmentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (Assignment assignment : course.getAssignment()) {
            JButton assignmentButton = new JButton(assignment.getName());
            assignmentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Implement logic to show AssignmentPanel for the selected assignment
                    showAssignmentPanel(assignment);
                }
            });
            assignmentPanel.add(assignmentButton);
        }

        // Create a JSplitPane to split the screen between assignmentPanel and studentTable
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, assignmentPanel, scrollPane);
        splitPane.setResizeWeight(0.5); // Set the initial size for each component
        add(splitPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Use FlowLayout

        // Create New Assignment button
        JButton newAssignmentButton = new JButton("Create New Assignment");
        newAssignmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to create a new assignment
                // For now, let's just show a message
                JOptionPane.showMessageDialog(TeacherCoursePanel.this, "Create New Assignment");
            }
        });
        buttonPanel.add(newAssignmentButton);

        // Add new student button
        JButton addStudentButton = new JButton("Add Student to Course");
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show input dialog to get student ID
                String studentIdInput = JOptionPane.showInputDialog(TeacherCoursePanel.this,
                        "Enter Student ID:");

                // Check if the user clicked OK and entered a student ID
                if (studentIdInput != null && !studentIdInput.trim().isEmpty()) {
                    // Implement logic to add the student to the course
                    try {
                        int studentId = Integer.parseInt(studentIdInput.trim());
                        Student studentToAdd = findStudentById(studentId);
                        if (studentToAdd != null && !course.getStudent().contains(studentToAdd)) {
                            // Add the student to the course and table
                            addStudentToCourse(studentToAdd);
                            parent.saveState();
                            JOptionPane.showMessageDialog(TeacherCoursePanel.this,
                                    "Student added to the course successfully!");
                        } else if (studentToAdd == null) {
                            JOptionPane.showMessageDialog(TeacherCoursePanel.this,
                                    "Student not found!");
                        } else {
                            JOptionPane.showMessageDialog(TeacherCoursePanel.this,
                                    "Student already added!");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(TeacherCoursePanel.this,
                                "Invalid student ID format!");
                    }
                } else {
                    // User clicked Cancel or entered an empty ID
                    JOptionPane.showMessageDialog(TeacherCoursePanel.this,
                            "Operation canceled.");
                }
            }
        });
        buttonPanel.add(addStudentButton);

        // Add new button for displaying grade distribution
        JButton gradeDistributionButton = new JButton("Show Grade Distribution");
        gradeDistributionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGradeDistribution();
            }
        });
        buttonPanel.add(gradeDistributionButton);

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
    // EFFECTS: return to the Teacher Panel
    private void showTeacherPanel() {
        TeacherPanel panel = new TeacherPanel(parent, teacher);
        parent.getContentPane().removeAll();
        parent.getContentPane().add(panel);
        parent.revalidate();
        parent.repaint();
    }

    // EFFECTS: turn to the specific assignment panel when pressed
    private void showAssignmentPanel(Assignment assignment) {
        JOptionPane.showMessageDialog(TeacherCoursePanel.this,
                "Show Assignment Panel for " + assignment.getName());
    }


    // EFFECTS: return a JTable based on the course's list of students
    private JTable createStudentTable() {
        // Get the list of students in the current course
        List<Student> students = new ArrayList<>();
        for (Student s : course.getStudent()) {
            for (Student c : parent.getStudentList()) {
                if (s.equals(c)) {
                    students.add(c);
                }
            }
        }

        // Define column names
        String[] columnNames = {"Student ID", "Student Name", "Overall"};

        // Create a DefaultTableModel
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Populate the data array with student information
        for (Student s : students) {
            Object[] rowData = {s.getId(), s.getName(), s.calculateOverall()};
            model.addRow(rowData);
        }

        return new JTable(model);
    }

    // REQUIRES: studentId > 0
    // EFFECTS: return the student with the given id
    private Student findStudentById(int studentId) {
        for (Student student : parent.getStudentList()) {
            if (student.getId() == studentId) {
                return student;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: add a student to the current course
    private void addStudentToCourse(Student s) {
        course.addStudent(s);

        // Update the JTable model with the new student
        DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
        model.addRow(new Object[]{s.getId(), s.getName(), s.calculateOverall()});

        // Refresh the JTable
        model.fireTableDataChanged();
    }

    // EFFECTS: display the grade distribution chart
    private void showGradeDistribution() {
        // Create a pie chart based on the grade distribution
        JFreeChart chart = createGradeDistributionChart();
        ChartPanel chartPanel = new ChartPanel(chart);

        // Show the chart in a dialog
        JOptionPane.showMessageDialog(this, chartPanel, "Grade Distribution", JOptionPane.PLAIN_MESSAGE);
    }

    // EFFECTS: represents the visual grade Distribution chart based on Letter Grade
    @SuppressWarnings("methodlength")
    private JFreeChart createGradeDistributionChart() {
        // Create a dataset for the grade distribution
        DefaultPieDataset dataset = new DefaultPieDataset();

        // Get the list of students in the current course
        List<Student> students = course.getStudent();

        // Count the occurrences of each grade
        int gradeA = 0;
        int gradeB = 0;
        int gradeC = 0;
        int gradeD = 0;
        int gradeF = 0;

        for (Student student : students) {
            double overallGrade = student.calculateOverall();
            if (overallGrade >= 90) {
                gradeA++;
            } else if (overallGrade >= 80) {
                gradeB++;
            } else if (overallGrade >= 70) {
                gradeC++;
            } else if (overallGrade >= 60) {
                gradeD++;
            } else {
                gradeF++;
            }
        }

        // Add data to the dataset
        dataset.setValue("A", gradeA);
        dataset.setValue("B", gradeB);
        dataset.setValue("C", gradeC);
        dataset.setValue("D", gradeD);
        dataset.setValue("F", gradeF);

        // Create a pie chart
        return ChartFactory.createPieChart(
                "Grade Distribution",
                dataset,
                true,  // Include legend
                true,
                false
        );
    }

}
