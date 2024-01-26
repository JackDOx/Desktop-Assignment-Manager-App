package ui;

import model.*;
import persistence.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AssignmentApp {
    private static final String JSON_TEACHER = "./data/teacher.json";
    private static final String JSON_STUDENT = "./data/student.json";
    private Person currentUser;
    private List<Person> personList;
    private List<Student> los;
    private List<Teacher> lot;
    private List<Course> loc;
    private JsonWriter jsonTeacherWriter;
    private JsonWriter jsonStudentWriter;
    private JsonReader jsonTeacherReader;
    private JsonReader jsonStudentReader;
    Scanner input;

    public AssignmentApp() throws FileNotFoundException {
        lot = new ArrayList<>();
        loc = new ArrayList<>();
        los = new ArrayList<>();
        personList = new ArrayList<>();
        jsonTeacherWriter = new JsonWriter(JSON_TEACHER);
        jsonStudentWriter = new JsonWriter(JSON_STUDENT);
        jsonStudentReader = new JsonReader(JSON_STUDENT);
        jsonTeacherReader = new JsonReader(JSON_TEACHER);

        runAssignment();
    }

    // Looping and running the application, receiving command and executing command
    @SuppressWarnings("methodlength")
    public void runAssignment() {
        boolean status = false;
        String command = "";
        init();
        System.out.println("Press k to load state from file, press other keys to continue without loading");
        command = input.next();
        if (command.equals("k")) {
            loadState();
        }
        status = accountSystem();
        System.out.println("Logged in: " + status + " as " + this.currentUser.getRole());
        while (status) {
            if (this.currentUser.getRole().equals("teacher")) {
                displayMenuTeacher();
                command = input.next();
                if (command.equals("q")) {
                    status = false;
                } else if (command.equals("o")) {
                    status = accountSystem();
                } else if (command.equals("j")) {
                    saveState();
                } else if (command.equals("k")) {
                    loadState();
                } else {
                    processTeacherCommand(command);
                }
            } else if (this.currentUser.getRole().equals("student")) {
                displayMenuStudent();
                command = input.next();
                if (command.equals("q")) {
                    status = false;
                } else if (command.equals("o")) {
                    status = accountSystem();
                } else if (command.equals("j")) {
                    saveState();
                } else if (command.equals("k")) {
                    loadState();
                } else {
                    processStudentCommand(command);
                }
            }

        }
    }

    // Process the command when the current user is a Student
    public void processStudentCommand(String command) {
        if (command.equals("c")) {
            for (int i : ((Student) this.currentUser).getCourseId()) {
                for (Course c : loc) {
                    if (c.getId() == i) {
                        System.out.println(c.getName() + "(id: " + c.getId() + "): " + c.getDescription());
                        System.out.println("Assignments: ");
                        for (Assignment a : c.getAssignment()) {
                            System.out.println("Id: " + a.getId() + ", name: " + a.getName() + ", type: "
                                    + a.getType() + ", " + a.getWorth() + ", Duration: " + a.getDuration());
                        }
                    }
                }

            }
        } else if (command.equals("a")) {
            processStudentCommandA();


        } else if (command.equals("v")) {
            System.out.println("Your overall grade: ");
            System.out.println(((Student) this.currentUser).calculateOverall());
        }
    }

    // Process command "a" when current user is Student
    public void processStudentCommandA() {
        System.out.println("Enter assignment id: ");
        String assignmentId = input.next();
        Assignment currentAssignment = null;

        // Search the assignment with the given id in student's assignment list
        for (Assignment a : ((Student) this.currentUser).getAssignment()) {
            if (assignmentId.equals(String.valueOf(a.getId()))) {
                currentAssignment = a;
                break;
            }
        }

        if (currentAssignment != null) {
            Answers answer = new Answers(((Student) this.currentUser), currentAssignment);
            for (Question q : currentAssignment.getQuestionList()) {
                System.out.println(q.getQuest());
                String an = input.next();
                answer.addAnswer(an);
            }
            System.out.println("Your grade: " + answer.grade());
        } else {
            System.out.println("Found no assignment with given id!");
        }
    }

    // Process command when current user is Teacher
    public void processTeacherCommand(String command) {
        if (command.equals("a")) {
            processTeacherCommandA();
        } else if (command.equals("c")) {
            System.out.println("Enter course name: ");
            String name = input.next();
            System.out.println("Enter course description: ");
            String description = input.next();
            ((Teacher) this.currentUser).addCourse(new Course(name, description));
        } else if (command.equals("f")) {
            processTeacherCommandF();
        } else if (command.equals("r")) {
            processTeacherCommandR();
        } else if (command.equals("s")) {
            processTeacherCommandS();
        } else if (command.equals("v")) {
            processTeacherCommandV();
        } else if (command.equals("g")) {
            processTeacherCommandG();
        }
    }

    // Process "v" command for teacher
    public void processTeacherCommandV() {
        for (Course c : ((Teacher) this.currentUser).getCourse()) {
            System.out.println(c.getName() + "(id: " + c.getId() + "): " + c.getDescription() + ", Students: ");
            for (Student s : c.getStudent()) {
                System.out.println(s.getName() + " (id: " + s.getId() + ")");
            }
        }
    }

    // Process "g" command for teacher
    public void processTeacherCommandG() {
        for (Course c : ((Teacher) this.currentUser).getCourse()) {
            System.out.println(c.getName() + "(id: " + c.getId() + "): " + c.getDescription());
            System.out.println("STUDENT GRADE:");
            for (Student s : c.getStudent()) {
                System.out.println(s.getName() + " (id: " + s.getId() + ")" + ": " + s.calculateOverall());
            }
        }
    }

    // Process "a" command for teacher
    @SuppressWarnings("methodlength")
    public void processTeacherCommandA() {
        Course currentCourse = null;
        System.out.println("Enter assignment name: ");
        String name = input.next();
        System.out.println("Enter assignment type: ");
        String type = input.next();
        System.out.println("Enter assignment duration: ");
        int duration = Integer.valueOf(input.next());
        System.out.println("Enter assignment deadline: ");
        int deadline = Integer.valueOf(input.next());
        System.out.println("Enter assignment worth: ");
        int worth = Integer.valueOf(input.next());
        System.out.println("Course id to add to: ");
        String id = input.next();
        for (Course c : ((Teacher) this.currentUser).getCourse()) {
            if (id.equals(String.valueOf(c.getId()))) {
                currentCourse = c;
                Assignment veryNew = new Assignment(name, type, duration, deadline, worth);
                currentCourse.addAssignment(veryNew);
            }
        }
        if (currentCourse == null) {
            System.out.println("No Course found with given id!");
        } else {
            System.out.println("Assignment added successfully!");
        }
    }

    // Process "r" command for teacher
    public void processTeacherCommandR() {
        System.out.println("Enter assignment id: ");
        String assignmentId = input.next();
        Assignment currentAssignment = null;

        // Search the assignment with the given id in student's assignment list
        for (Course c : ((Teacher) this.currentUser).getCourse()) {
            for (Assignment a : c.getAssignment()) {
                if (assignmentId.equals(String.valueOf(a.getId()))) {
                    currentAssignment = a;
                    c.removeAssignment(a);
                    break;
                }
            }
        }

        if (currentAssignment == null) {
            System.out.println("No assignment with given id found!");
        }
    }

    // Process "f" command for Teacher
    public void processTeacherCommandF() {
        System.out.println("Enter student id: ");
        int studentId = Integer.valueOf(input.next());
        System.out.println("Enter course id: ");
        int courseId = Integer.valueOf(input.next());
        for (Person s : personList) {
            for (Course c : ((Teacher) this.currentUser).getCourse()) {
                if (s.getRole().equals("student") && s.getId() == studentId && c.getId() == courseId) {
                    c.addStudent((Student) s);
                }
            }
        }
    }

    // Process "s" command for teacher
    public void processTeacherCommandS() {
        for (Course c : ((Teacher) this.currentUser).getCourse()) {
            System.out.println(c.getName() + "(id: " + c.getId() + "): " + c.getDescription());
            System.out.println("Assignments: ");
            for (Assignment a : c.getAssignment()) {
                System.out.println("Id: " + a.getId() + ", name: " + a.getName() + ", type: "
                        + a.getType() + ", " + a.getWorth() + ", Duration: " + a.getDuration());
            }
        }
    }

    // Initialize Students, Teacher, 1 Assignment, 1 Course, a person list and input scanner
    public void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // Process login/ signup command for authorization
    public boolean accountSystem() {
        this.currentUser = null;
        System.out.println("Press q to Login, Press e to Signup");
        String command = input.next();
        if (command.equals("e")) {
            signup();
        } else if (command.equals("q")) {
            login();
        }

        if (this.currentUser != null) {
            return true;
        } else {
            return false;
        }
    }

    // Process signup - register a new Person
    public void signup() {
        System.out.println("REGISTER NEW ACCOUNT" + "\nUser Name: ");
        String userName = input.next();
        System.out.println("Password: ");
        String password = input.next();
        System.out.println("Name: ");
        String name = input.next();
        System.out.println("Role (1 for student, 2 for teacher): ");
        String role = input.next();

        if (role.equals("1")) {
            Student s = new Student(name, userName, password);
            this.los.add(s);
            this.currentUser = s;
        } else if (role.equals("2")) {
            Teacher t = new Teacher(name, userName, password);
            this.lot.add(t);
            this.currentUser = t;
        }
        personList.add(this.currentUser);
    }

    // Process login - find the Person with corresponding username and password in the person list
    public void login() {
        System.out.println("LOGIN" + "\nUser Name: ");
        String username = input.next();
        System.out.println("Password: ");
        String password = input.next();
        for (Person p : personList) {
            if (p.getUserName().equals(username) && p.getPassword().equals(password)) {
                this.currentUser = p;
                break;
            }
        }
    }

    // EFFECTS: save the current state to file
    private void saveState() {
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

    // Command menu for Teacher
    private void displayMenuTeacher() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> create Assignment and add to course");
        System.out.println("\tc -> create new Course");
        System.out.println("\tf -> add Student to a Course");
        System.out.println("\tr -> remove Assignment");
        System.out.println("\ts -> view Assignment List and Course List");
        System.out.println("\tv -> view student list");
        System.out.println("\tg -> view list of student overall grade");
        System.out.println("\tj -> save the current state to file");
        System.out.println("\tk -> load the state from file");
        System.out.println("\to -> log out");
        System.out.println("\tq -> quit");
    }

    // Command menu for Student
    private void displayMenuStudent() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> choose an assignment to work on");
        System.out.println("\tc -> view Course List and Assignments in each Course");
        System.out.println("\tv -> view overall grade");
        System.out.println("\tj -> save the current state to file");
        System.out.println("\tk -> load the state from file");
        System.out.println("\to -> log out");
        System.out.println("\tq -> quit");
    }
}
