package model;

import org.json.JSONObject;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnswersTest {
    private Answers testAnswers;
    private Student testStudent;
    private Assignment testAssignment;

    @BeforeEach
    void runBefore() {
        testStudent = new Student("Jack", "jackDo", "123456");
        testAssignment = new Assignment("EDX Cs Homework", "Homework", 60, 7, 100);
        testAnswers = new Answers(testStudent, testAssignment);
    }

    @Test
    void testAddRemoveAnswer() {
        testAnswers.addAnswer("Answer 1");
        List<String> answers = testAnswers.getAnswer();
        assertEquals(1, answers.size());
        assertEquals("Answer 1", answers.get(0));
        testAnswers.removeAnswer("Answer 1");
        testAnswers.addAnswer("Answer 3");
        testAnswers.removeAnswer("Answer 2");
        answers = testAnswers.getAnswer();
        assertEquals(1, answers.size());
        assertEquals("Answer 3", answers.get(0));
    }

    @Test
    void testChangeAnswer() {
        testAnswers.addAnswer("Answer 1");
        testAnswers.changeAnswer(0, "Updated Answer");
        List<String> answers = testAnswers.getAnswer();
        assertEquals(1, answers.size());
        assertEquals("Updated Answer", answers.get(0));
    }

    @Test
    void testGrade() {
        Question question1 = new LongAnswer("What color's your Bugatti", "Bronze", 5);
        Question question2 = new LongAnswer("How fast is Jack's Porsche?", "300", 20);
        Question question3 = new LongAnswer("Who is the richest person", "Jack Do", 10);
        testAssignment.addQuestion(question1);
        testAssignment.addQuestion(question2);
        testAssignment.addQuestion(question3);
        testAnswers.addAnswer("Bronze");
        testAnswers.addAnswer(1, null);
        testAnswers.addAnswer(1, "200");
        double grade = testAnswers.grade();
        assertEquals(14.285714285714285, grade); // Check if the calculated grade is as expected
    }

    @Test
    void testSettersAndGetters() {
        Student newStudent = new Student("Mai", "Jasmine", "234567");
        Assignment newAssignment = new Assignment("Assignment 2", "Quiz", 30, 5, 50);
        testAnswers.setStudent(newStudent);
        testAnswers.setAssignment(newAssignment);
        assertEquals(newStudent, testAnswers.getStudent());
        assertEquals(newAssignment, testAnswers.getAssignment());
    }
}
