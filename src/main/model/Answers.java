package model;

import java.util.List;
import java.util.ArrayList;

// A list of String which stores a Student's answers for a specific Assignment
public class Answers {
    private Student student;
    private Assignment assignment;
    private List<String> answer;

    // EFFECTS: Construct an empty list of String which has an initial size of the Assignment question size
    public Answers(Student student, Assignment assignment) {
        this.student = student;
        this.assignment = assignment;
        this.answer = new ArrayList<>(assignment.getQuestionList().size());
    }

    // MODIFIES: this
    // EFFECTS: calculate the grade of the answers based on the Assignment key and add grade to student list of grade
    public double grade() {
        double result = 0;
        double total = 0;
        List<Question> questionList = this.assignment.getQuestionList();

        for (int i = 0; i < questionList.size(); i++) {
            if (this.answer.get(i) != null) {
                if (questionList.get(i).isCorrect(this.answer.get(i))) {
                    result += questionList.get(i).getWeight();
                }
            }
            total += questionList.get(i).getWeight();
        }

        result = (result / total) * 100; //* this.assignment.getWorth();

        this.student.addGrade(new Grade(result, this.assignment, this.student));

        return result;
    }

    // EFFECTS: return the list of answer
    public List<String> getAnswer() {
        return this.answer;
    }

    // MODIFIES: this
    // EFFECTS: add answer to the answer list
    public void addAnswer(String a) {
        this.answer.add(a);
    }

    // REQUIRES: index >= 0
    // MODIFIES: this
    // EFFECTS: add answer to the answer list at a specific index
    public void addAnswer(int index, String a) {
        this.answer.add(index, a);
    }

    // REQUIRES: index >= 0
    // MODIFIES: this
    // EFFECTS: Remove the answer at index, replace with the newAnswer at that index
    public void changeAnswer(int index, String newAnswer) {
        this.answer.remove(index);
        this.answer.add(index, newAnswer);
    }

    // MODIFIES: this
    // EFFECTS: Remove the specific answer
    public void removeAnswer(String a) {
        if (this.answer.contains(a)) {
            this.answer.remove(a);
        }
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Assignment getAssignment() {
        return this.assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

}
