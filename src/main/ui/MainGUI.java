package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import java.io.FileNotFoundException;
import java.io.IOException;


import model.*;
import persistence.*;

// Represents the main GUI that stores all current states of the program
public class MainGUI extends JFrame {
    private static final String JSON_TEACHER = "./data/teacher.json";
    private static final String JSON_STUDENT = "./data/student.json";
    private JPanel mainPanel;
    private LoginSignupPanel loginSignupPanel;
    private LoginPanel loginPanel;
    private SignupPanel signupPanel;
    private List<Person> personList;
    private List<Student> los;
    private List<Teacher> lot;
    private Person currentPerson;
    private List<Course> loc;
    private JsonWriter jsonTeacherWriter;
    private JsonWriter jsonStudentWriter;
    private JsonReader jsonTeacherReader;
    private JsonReader jsonStudentReader;
    Scanner input;

    @SuppressWarnings("methodlength")
    // EFFECTS: construct a GUI and load the state from JSON file into the fields
    public MainGUI() {
        super("Login or Signup");
        loc = new ArrayList<>();
        lot = new ArrayList<>();
        los = new ArrayList<>();
        personList = new ArrayList<>();
        jsonTeacherWriter = new JsonWriter(JSON_TEACHER);
        jsonStudentWriter = new JsonWriter(JSON_STUDENT);
        jsonStudentReader = new JsonReader(JSON_STUDENT);
        jsonTeacherReader = new JsonReader(JSON_TEACHER);
        loadState();


        // On application quit, print the Event Log to console and save the current State
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveState();
                EventLog eventLog = EventLog.getInstance();

                EventLogPrinter.printEventLog(eventLog);

                System.exit(0); // Terminate the application
            }
        });

        setPreferredSize(new Dimension(630, 600));
        mainPanel = new JPanel(new GridLayout(2, 1));

        loginSignupPanel = new LoginSignupPanel(this);

        mainPanel.add(loginSignupPanel);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
    }

    // EFFECTS: save the current state to file
    public void saveState() {
        try {
            jsonTeacherWriter.open();
            jsonStudentWriter.open();
            jsonTeacherWriter.writeTeacher(lot);
            jsonStudentWriter.writeStudent(los);
            jsonTeacherWriter.close();
            jsonStudentWriter.close();
            System.out.println("Saved to " + JSON_STUDENT + ", and " + JSON_TEACHER);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STUDENT + ", and " + JSON_TEACHER);
        }
    }

    // EFFECTS: load the students and teachers from file
    private void loadState() {
        try {
            List<Teacher> teachers = jsonTeacherReader.readTeacher();
            List<Student> students = jsonStudentReader.readStudent();
            lot = teachers;
            los = students;

            loadPersonList();
            System.out.println("Loaded from " + JSON_STUDENT + ", and " + JSON_TEACHER);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STUDENT + ", and " + JSON_TEACHER);
        }
    }

    // EFFECTS: add teachers and students to person list
    private void loadPersonList() {
        for (Teacher t : lot) {
            for (Course c : t.getCourse()) {
                if (!loc.contains(c)) {
                    loc.add(c);
                }
            }
            if (!personList.contains(t)) {
                personList.add(t);
            }
        }
        for (Student s : los) {
            if (!personList.contains(s)) {
                personList.add(s);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: display the login panel
    public void showLoginPanel() {
        mainPanel.setVisible(false);
        loginPanel = new LoginPanel(this);
        add(loginPanel);
        pack();
        setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: display the signup panel
    public void showSignupPanel() {
        mainPanel.setVisible(false);
        signupPanel = new SignupPanel(this);
        add(signupPanel);
        pack();
        setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: display the main panel which is the login signup panel
    public void showMainPanel() {
        mainPanel.setVisible(true);
        if (loginPanel != null) {
            remove(loginPanel);
            loginPanel = null;
        }
        if (signupPanel != null) {
            remove(signupPanel);
            signupPanel = null;
        }
        pack();
        setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: set the current Person to person (after login or signup)
    public void setCurrentPerson(Person person) {
        this.currentPerson = person;
    }

    // EFFECTS: return the current person
    public Person getCurrentPerson() {
        return this.currentPerson;
    }

    // EFFECTS: return the list of Person
    public List<Person> getPersonList() {
        return personList;
    }

    // EFFECTS: return the course list
    public List<Course> getCourseList() {
        return loc;
    }

    // MODIFIES: this
    // EFFECTS: add course to course list
    public void addCourse(Course c) {
        this.loc.add(c);
    }

    // EFFECTS: return the student list
    public List<Student> getStudentList() {
        return los;
    }

    // EFFECTS: return the teacher list
    public List<Teacher> getTeacherList() {
        return lot;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}
