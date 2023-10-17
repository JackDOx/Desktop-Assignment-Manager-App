package model;

import java.util.*;

// Assignment class that contains a list of Question, duration, deadline and its weight out of the course
// that a student can work on. An assignment is unique with a unique ID and can be worked on by students.
public class Assignment {
    private static int nextAssignmentId = 1000;
    private String name;
    private String type;
    private int id;
    private int duration;
    private int deadline;
    private int worth;
    private List<Question> loq;

    // REQUIRES: duration > 0 , deadline > 0
    // EFFECTS: constructs an Assignment with unique id and an empty list of Question
    public Assignment(String name, String type, int duration, int deadline, int worth) {
        this.name = name;
        this.type = type;
        this.deadline = deadline;
        this.id = nextAssignmentId++;
        this.duration = duration;
        this.worth = worth;
        this.loq = new ArrayList<>();
    }

    public List<Question> getQuestionList() {
        return this.loq;
    }

    // MODIFIES: this
    // EFFECTS: add Question q to list of Question
    public void addQuestion(Question q) {
        if (!this.loq.contains(q)) {
            this.loq.add(q);
        }
    }

    // REQUIRES: index >= 0
    // MODIFIES: this
    // EFFECTS: add Question q to list of Question
    public void addQuestion(int index, Question q) {
//        if (!this.loq.contains(q)) {
//            this.loq.add(index, q);
//        }
        this.loq.add(index, q);
    }

    // MODIFIES: this
    // EFFECTS: remove Question q from list of Question
    public void removeQuestion(Question q) {
        if (this.loq.contains(q)) {
            this.loq.remove(q);
        }
    }

    // REQUIRES: from >=0, to >=0
    // MODIFIES: this
    // EFFECTS: remove Question q from list of Question
    public void changeQuestionPosition(int from, int to) {
        Question x = this.loq.get(from);
        this.loq.remove(x);
        this.loq.add(to, x);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDeadline() {
        return this.deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public int getId() {
        return this.id;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getWorth() {
        return this.worth;
    }

    public void setWorth(int worth) {
        this.worth = worth;
    }
}
