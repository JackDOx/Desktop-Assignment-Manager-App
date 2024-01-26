package model;

import java.util.List;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;

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

    // EFFECTS: construct a Course with a given id
    public Course(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.los = new ArrayList<>();
        this.loa = new ArrayList<>();
    }


    // EFFECTS: return the Json file of Course
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("name", this.name);
        json.put("description", this.description);
        json.put("students", studentToJsonArray());
        json.put("assignments", assignmentToJsonArray());
        return json;
    }

    // EFFECTS: return the json file of list of student's ids
    public JSONArray studentToJsonArray() {
        JSONArray result = new JSONArray();
        for (Student a : this.los) {
            result.put(a.toJson());
        }
        return result;
    }

    // EFFECTS: return the json file of list of assignment
    public JSONArray assignmentToJsonArray() {
        JSONArray result = new JSONArray();
        for (Assignment a : this.loa) {
            result.put(a.toJson());
        }
        return result;
    }



    // MODIFIES: this
    // EFFECTS: add s to list of Student, and add all the assignments in the Course to s' list of Assignment
    public void addStudent(Student s) {
        if (!this.los.contains(s)) {
            this.los.add(s);
            s.addCourseId(this.id);
            for (Assignment a : loa) {
                s.addAssignment(a);
            }

            Event event = new Event("Student " + s.getName() + "(id: " + s.getId() + ") added to Course "
                    + this.name);
            EventLog.getInstance().logEvent(event);
        }
    }

    // MODIFIES: this
    // EFFECTS: remove s from the list and remove all the assignments in the Course from s' list of Assignment
    public void removeStudent(Student s) {
        if (this.los.contains(s)) {
            this.los.remove(s);
            s.removeCourseId(this.id);
            for (Assignment a : loa) {
                s.removeAssignment(a);
            }

            Event event = new Event("Student " + s.getName() + "(id: " + s.getId() + ") removed from Course "
                    + this.name);
            EventLog.getInstance().logEvent(event);
        }
    }

    // MODIFIES: this
    // EFFECTS: add as to list of Assignment, and add that assignment to all s' list of Assignment
    public void addAssignment(Assignment as) {
        this.loa.add(as);
        for (Student s : this.los) {
            s.addAssignment(as);
        }

        Event event = new Event("Assignment " + as.getName() + " added to Course");
        EventLog.getInstance().logEvent(event);
    }

    // MODIFIES: this
    // EFFECTS: remove as to list of Assignment, and remove that assignment to all s' list of Assignment
    public void removeAssignment(Assignment as) {
        if (this.loa.contains(as)) {
            this.loa.remove(as);
            for (Student s : this.los) {
                s.removeAssignment(as);
            }

            Event event = new Event("Assignment " + as.getName() + " removed from Course");
            EventLog.getInstance().logEvent(event);
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
        String oldName = this.name;
        this.name = name;

        Event event = new Event("Course " + oldName + "'s name changed to " + this.name);
        EventLog.getInstance().logEvent(event);
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;

        Event event = new Event("Course " + this.name + "'s description changed to " + description);
        EventLog.getInstance().logEvent(event);
    }
}
