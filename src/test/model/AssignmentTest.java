package model;

import org.json.JSONObject;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AssignmentTest {
    private Assignment testAssignment;
    private Assignment testAssignment2;
    private MultipleChoice mc;
    private LongAnswer la;

    @BeforeEach
    void runBefore() {
        testAssignment = new Assignment("Assignment 1", "Homework", 60, 7, 100);
        mc = new MultipleChoice("What is 2 + 2?", "4", 5);
        la = new LongAnswer("Explain the process of photosynthesis", "Carbon dioxide and water are " +
                "converted into glucose and oxygen through the energy of sunlight", 15);
    }

    @Test
    void testConstructor() {
        testAssignment2 = new Assignment(1050, "Final Exam", "Exam", 120, 12, 10);
        assertEquals(1050, testAssignment2.getId());
        assertEquals("Exam", testAssignment2.getType());
        assertEquals("Assignment 1", testAssignment.getName());
        assertEquals("Homework", testAssignment.getType());
        assertEquals(7, testAssignment.getDeadline());
        assertEquals(1017, testAssignment.getId());
        assertEquals(60, testAssignment.getDuration());
        assertEquals(100, testAssignment.getWorth());
        List<Question> loq = testAssignment.getQuestionList();
        assertEquals(0, loq.size());
    }

    @Test
    void testAddRemoveQuestion() {
        Question question = new LongAnswer("How fast is Jack's Porsche?", "300", 20);
        testAssignment.addQuestion(0, question);
        List<Question> loq = testAssignment.getQuestionList();
        assertEquals(1, loq.size());
        assertEquals(question, loq.get(0));
        testAssignment.removeQuestion(question);
        loq = testAssignment.getQuestionList();
        assertEquals(0, loq.size());

        testAssignment.addQuestion(question);
        testAssignment.addQuestion(question);
        assertEquals(1, testAssignment.getQuestionList().size());
        assertEquals(question, testAssignment.getQuestionList().get(0));

        Question question2 = new LongAnswer("Test dun for remove a not existing question", "NA", 10);
        testAssignment.removeQuestion(question2);
        assertEquals(1, testAssignment.getQuestionList().size());
        assertEquals(question, testAssignment.getQuestionList().get(0));
    }

    @Test
    void testChangeQuestionPosition() {
        Question question1 = new LongAnswer("What color's your Bugatti", "Bronze", 5);
        Question question2 = new LongAnswer("How fast is Jack's Porsche?", "300", 20);
        testAssignment.addQuestion(question1);
        testAssignment.addQuestion(question2);
        List<Question> loq = testAssignment.getQuestionList();
        assertEquals(2, loq.size());
        testAssignment.changeQuestionPosition(0, 1);
        loq = testAssignment.getQuestionList();
        assertEquals(question2, loq.get(0));
        assertEquals(question1, loq.get(1));
    }

    @Test
    void testSettersAndGetters() {
        testAssignment.setName("Assignment 2");
        assertEquals("Assignment 2", testAssignment.getName());
        testAssignment.setType("Quiz");
        assertEquals("Quiz", testAssignment.getType());
        testAssignment.setDeadline(5);
        assertEquals(5, testAssignment.getDeadline());
        testAssignment.setDuration(90);
        assertEquals(90, testAssignment.getDuration());
        testAssignment.setWorth(50);
        assertEquals(50, testAssignment.getWorth());
    }

    @Test
    void testToJson() {
        JSONObject jsonObject = testAssignment.toJson();
        assertEquals("Assignment 1", jsonObject.getString("name"));
        assertEquals("Homework", jsonObject.getString("type"));
        assertEquals(7, jsonObject.getInt("deadline"));
        assertEquals(60, jsonObject.getInt("duration"));
        assertEquals(100, jsonObject.getInt("worth"));
        assertEquals(1015, jsonObject.getInt("id"));
        assertEquals(0, jsonObject.getJSONArray("questions").length());
    }

    @Test
    void testQuestionToJsonArray() {
        testAssignment.addQuestion(mc);
        testAssignment.addQuestion(la);
        JSONArray jsonArray = testAssignment.questionToJsonArray();
        assertEquals(2, jsonArray.length());
        JSONObject q1 = jsonArray.getJSONObject(0);
        JSONObject q2 = jsonArray.getJSONObject(1);
        assertTrue(q1.has("choices"));
        assertTrue(q2.has("key"));
    }
}
