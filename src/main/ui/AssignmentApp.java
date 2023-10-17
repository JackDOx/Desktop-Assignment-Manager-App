package ui;

import model.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class AssignmentApp {
    private Student jack;
    private Student mike;
    private Student bugatti;
    private Assignment quiz1;
    private Teacher james;
    private Course math;
    private Person currentUser;
    private List<Person> personList;
    Scanner input;

    public AssignmentApp() {
        runAssignment();
    }

    // Looping and running the application, receiving command and executing command
    @SuppressWarnings("methodlength")
    public void runAssignment() {
        boolean status = false;
        String command = "";
        init();
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
                } else {
                    processStudentCommand(command);
                }
            }

        }
    }

    // Process the command when the current user is a Student
    public void processStudentCommand(String command) {
        if (command.equals("c")) {
            for (Course c : this.currentUser.getCourse()) {
                System.out.println(c.getName() + "(id: " + c.getId() + "): " + c.getDescription());
                System.out.println("Assignments: ");
                for (Assignment a : c.getAssignment()) {
                    System.out.println("Id: " + a.getId() + ", name: " + a.getName() + ", type: "
                            + a.getType() + ", " + a.getWorth() + ", Duration: " + a.getDuration());
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
            Answers answer = new Answers((Student) this.currentUser, currentAssignment);
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
            this.currentUser.addCourse(new Course(name, description));
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
        for (Course c : this.currentUser.getCourse()) {
            System.out.println(c.getName() + "(id: " + c.getId() + "): " + c.getDescription() + ", Students: ");
            for (Student s : c.getStudent()) {
                System.out.println(s.getName() + " (id: " + s.getId() + ")");
            }
        }
    }

    // Process "g" command for teacher
    public void processTeacherCommandG() {
        for (Course c : this.currentUser.getCourse()) {
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
        for (Course c : this.currentUser.getCourse()) {
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
        for (Course c : this.currentUser.getCourse()) {
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
            for (Course c : this.currentUser.getCourse()) {
                if (s.getRole().equals("student") && s.getId() == studentId && c.getId() == courseId) {
                    c.addStudent((Student) s);
                }
            }
        }
    }

    // Process "s" command for teacher
    public void processTeacherCommandS() {
        for (Course c : this.currentUser.getCourse()) {
            System.out.println(c.getName() + "(id: " + c.getId() + "): " + c.getDescription());
            System.out.println("Assignments: ");
            for (Assignment a : c.getAssignment()) {
                System.out.println("Id: " + a.getId() + ", name: " + a.getName() + ", type: "
                        + a.getType() + ", " + a.getWorth() + ", Duration: " + a.getDuration());
            }
        }
    }

    // Initialize Students, Teacher, 1 Assignment, 1 Course, a person list and input scanner
    @SuppressWarnings("methodlength")
    public void init() {
        jack = new Student("Jack", "jackDo", "123456");
        mike = new Student("Mike", "mikeDo", "123456");
        bugatti = new Student("Bugatti", "bugattiDo", "123456");
        james = new Teacher("James", "jamesDo", "123456");

        quiz1 = new Assignment("Quiz1", "Quiz", 5, 1600, 100);
        Question q1 = new LongAnswer("What color's your Bugatti?", "Bronze", 5);
        Question q2 = new LongAnswer("How fast if your Bugatti?", "300", 10);
        quiz1.addQuestion(q1);
        quiz1.addQuestion(q2);

        personList = new ArrayList<>();
        math = new Course("MATH 200", "Analytic geometry in 2 and 3 dimensions, ");
        math.addAssignment(quiz1);

        math.addStudent(jack);
        math.addStudent(mike);
        math.addStudent(bugatti);

        james.addCourse(math);

        personList.add(jack);
        personList.add(mike);
        personList.add(james);
        personList.add(bugatti);

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
            this.currentUser = new Student(name, userName, password);
        } else if (role.equals("2")) {
            this.currentUser = new Teacher(name, userName, password);;
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
        System.out.println("\to -> log out");
        System.out.println("\tq -> quit");
    }

    // Command menu for Student
    private void displayMenuStudent() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> choose an assignment to work on");
        System.out.println("\tc -> view Course List and Assignments in each Course");
        System.out.println("\tv -> view overall grade");
        System.out.println("\to -> log out");
        System.out.println("\tq -> quit");
    }
}
