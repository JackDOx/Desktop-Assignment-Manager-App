package model;

import java.util.List;
import java.util.ArrayList;

// A Course class that has a unique ID. Course has a list of Student that are enrolled in the Course, and a list of
// Assignment that require Students to work on them
public class Course {
    private static int nextAssignmentId = 1000;
    private int id;
    private String name;
    private String description;
    private List<Student> los;
    private List<Assignment> loa;

    // EFFECTS: construct a Course with an empty list of Student and an empty list of Assignment
    public Course(String name, String description) {
        this.id = nextAssignmentId++;
        this.name = name;
        this.description = description;
        this.los = new ArrayList<>();
        this.loa = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add s to list of Student, and add all the assignments in the Course to s' list of Assignment
    public void addStudent(Student s) {
        if (!this.los.contains(s)) {
            this.los.add(s);
            s.addCourse(this);
            for (Assignment a : loa) {
                s.addAssignment(a);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: remove s from the list and remove all the assignments in the Course from s' list of Assignment
    public void removeStudent(Student s) {
        if (this.los.contains(s)) {
            this.los.remove(s);
            for (Assignment a : loa) {
                s.removeAssignment(a);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: add as to list of Assignment, and add that assignment to all s' list of Assignment
    public void addAssignment(Assignment as) {
        this.loa.add(as);
        for (Student s : this.los) {
            s.addAssignment(as);
        }
    }

    // MODIFIES: this
    // EFFECTS: remove as to list of Assignment, and remove that assignment to all s' list of Assignment
    public void removeAssignment(Assignment as) {
        if (this.loa.contains(as)) {
            this.loa.remove(as);
            for (Student s : this.los) {
                s.removeAssignment(as);
            }
        }
    }

    public List<Assignment> getAssignment() {
        return this.loa;
    }

    public List<Student> getStudent() {
        return this.los;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
