package model;

import org.json.JSONArray;
import org.json.JSONObject;

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

    // EFFECTS: construct a teacher with a given id
    public Teacher(int id, String name, String userName, String password) {
        this.role = "teacher";
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.id = id;
        this.loc = new ArrayList<>();
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("role", this.role);
        json.put("name", this.name);
        json.put("userName", this.userName);
        json.put("password", this.password);
        json.put("courses", courseToJsonArray());

        return json;
    }

    public JSONArray courseToJsonArray() {
        JSONArray result = new JSONArray();
        for (Course c : this.loc) {
            result.put(c.toJson());
        }
        return result;
    }

    // MODIFIES: this
    // EFFECTS: add c to loc
    public void addCourse(Course c) {
        this.loc.add(c);

        Event event = new Event("Course " + c.getName() + "(id : " + c.getId()
                + " ) added to teacher " + this.name + " course list.");
        EventLog.getInstance().logEvent(event);
    }

    // MODIFIES: this
    // EFFECTS: remove c from loc
    public void removeCourse(Course c) {
        this.loc.remove(c);

        Event event = new Event("Course " + c.getName() + "(id : " + c.getId()
                + " ) removed from teacher " + this.name + " course list.");
        EventLog.getInstance().logEvent(event);
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

        Event event = new Event("Teacher " + this.name + "'s role changed to "
                + role);
        EventLog.getInstance().logEvent(event);
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;

        Event event = new Event("Teacher " + oldName + "'s name changed to "
                + name);
        EventLog.getInstance().logEvent(event);
    }

    @Override
    public int getId() {
        return this.id;
    }

}
