package model;

import java.util.List;
import java.util.ArrayList;

// A subclass of Person - Teacher whose role is "teacher" who has a specific userName and password to do the login
// Teacher has a unique ID and a list of Course that the Teacher is in charged of.
public class Teacher extends Person {
    private static int nextAssignmentId = 1000;
    private String role;
    private String name;
    private String userName;
    private String password;
    private int id;
    private List<Course> loc;

    // EFFECTS: construct a teacher with role set to "teacher", n unique id and an empty list of Course
    public Teacher(String name, String userName, String password) {
        this.role = "teacher";
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.id = nextAssignmentId++;
        this.loc = new ArrayList<>();
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

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

}
