package model;

import java.util.List;
import org.json.JSONObject;

// Grade class that contains a grade in double type, the assignment that this grade belongs to and a Student that this
// grade belongs to
public class Grade {
    private double grade;
    private Assignment assignment;

    // REQUIRES: grade >= 0.0
    // EFFECTS: construct a Grade
    public Grade(double grade,Assignment assignment) {
        this.grade = grade;
        this.assignment = assignment;
    }

    // EFFECTS: return the grade in JSON Object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("grade", this.grade);
        json.put("assignment", this.assignment.toJson());
        return json;
    }

    public double getGrade() {
        return this.grade;
    }

    public Assignment getAssignment() {
        return this.assignment;
    }
}
