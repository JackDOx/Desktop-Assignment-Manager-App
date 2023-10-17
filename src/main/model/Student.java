package model;

import java.util.List;
import java.util.ArrayList;

// A Student class that extends Person class, which has userName and password for authorization, a list of Grade that
// student is granted after finished an Assignment, a list of Course the student is enrolled in, and a list of
// Assignment to work on
public class Student extends Person {
    private static int nextStudentId = 1000;
    private String role;
    private String name;
    private String userName;
    private String password;
    private List<Grade> grades;
    private List<Course> loc;
    private List<Assignment> loa;
    private int id;

    // EFFECTS: construct a student with a unique id, an empty list of grade, list of Course and list of Assignment
    public Student(String name, String userName, String password) {
        this.role = "student";
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.id = nextStudentId++;
        this.grades = new ArrayList<>();
        this.loc = new ArrayList<>();
        this.loa = new ArrayList<>();
    }

    // EFFECTS: calculate the student overall based on list of Grade and return the result
    public double calculateOverall() {
        double result = 0;
        for (Grade g : this.grades) {
            result += g.getGrade();
        }

        // In reality, remove this as the granted % is calculated in Answer.grade()
        result = result / (this.grades.size());
        return result;
    }

    // MODIFIES: this
    // EFFECTS: add as to loa
    public void addAssignment(Assignment as) {
        this.loa.add(as);
    }

    // MODIFIES: this
    // EFFECTS: remove as from loa
    public void removeAssignment(Assignment as) {
        this.loa.remove(as);
    }

    public List<Assignment> getAssignment() {
        return this.loa;
    }

    // MODIFIES: this
    // EFFECTS: add c to loc
    public void addCourse(Course c) {
        this.loc.add(c);
    }

    // MODIFIES: this
    // EFFECTS: remove c from loc
    public void removeCourse(Course c) {
        this.loc.remove(c);
    }

    public List<Course> getCourse() {
        return this.loc;
    }


    // Getter for 'userName'
    public String getUserName() {
        return userName;
    }

    // Setter for 'userName'
    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Getter for 'passWord'
    public String getPassword() {
        return password;
    }

    // Setter for 'passWord'
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return this.id;
    }

    // MODIFIES: this
    // EFFECTS: add grade to grades
    public void addGrade(Grade grade) {
        this.grades.add(grade);
    }

    // REQUIRES: index >= 0
    // MODIFIES: this
    // EFFECTS: add grade to grades at index index
    public void addGrade(int index, Grade grade) {
        this.grades.add(index, grade);
    }

    // REQUIRES: this.contains(grade)
    // MODIFIES: this
    // EFFECTS: remove grade from list of grades
    public void removeGrade(Grade grade) {
        this.grades.remove(grade);
    }

    public List<Grade> getGrades() {
        return this.grades;
    }

}
