package model;

// Grade class that contains a grade in double type, the assignment that this grade belongs to and a Student that this
// grade belongs to
public class Grade {
    private double grade;
    private Assignment assignment;
    private Student student;

    // REQUIRES: grade >= 0.0
    // EFFECTS: construct a Grade
    public Grade(double grade,Assignment assignment, Student student) {
        this.grade = grade;
        this.assignment = assignment;
        this.student = student;
    }

    public double getGrade() {
        return this.grade;
    }

    public Student getStudent() {
        return this.student;
    }

    public Assignment getAssignment() {
        return this.assignment;
    }
}
