package persistence;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import model.*;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {
    private JsonReader reader;

    @Test
    void testParseStudent() {
        reader = new JsonReader("./data/testParseStudent.json");
        try {
            List<Student> students = reader.readStudent();
            assertEquals(2, students.size());

            // Test the first student
            Student student1 = students.get(0);
            assertEquals("Alice", student1.getName());
            assertEquals("alice123", student1.getUserName());
            assertEquals("password123", student1.getPassword());
            assertEquals(1, student1.getId());
            assertEquals(0, student1.getAssignment().size());

            // Test the second student
            Student student2 = students.get(1);
            assertEquals("Bob", student2.getName());
            assertEquals("bob456", student2.getUserName());
            assertEquals("password456", student2.getPassword());
            assertEquals(2, student2.getId());
            assertEquals(1, student2.getAssignment().size());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testParseTeacher() {
        reader = new JsonReader("./data/testParseTeacher.json");
        try {
            List<Teacher> teachers = reader.readTeacher();
            assertEquals(2, teachers.size());

            // Test the first teacher
            Teacher teacher1 = teachers.get(0);
            assertEquals("Teacher1", teacher1.getName());
            assertEquals("teacher1", teacher1.getUserName());
            assertEquals("password1", teacher1.getPassword());
            assertEquals(1, teacher1.getId());
            assertEquals(1, teacher1.getCourse().size());

            // Test the second teacher
            Teacher teacher2 = teachers.get(1);
            assertEquals("Teacher2", teacher2.getName());
            assertEquals("teacher2", teacher2.getUserName());
            assertEquals("password2", teacher2.getPassword());
            assertEquals(2, teacher2.getId());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testParseCourse() {
        reader = new JsonReader("./data/testParseCourse.json");
        Course c = reader.parseCourse(new JSONObject("{\"id\": 1001, \"name\": \"Mathematics\", " +
                "\"description\": \"Introduction to Mathematics\", \"students\": [" +
                "{\"name\": \"Alice\", \"userName\": \"alice123\", \"password\": \"password123\", \"role\": " +
                "\"student\", \"id\": 1, \"grades\": [], \"courses\": [], \"assignments\": []}], \"assignments\": [" +
                "{\"name\": \"Math Assignment\", \"type\": \"Math\", \"id\": 101, \"duration\": 60, \"deadline\":" +
                " 7, \"worth\": 100, \"questions\": []}]}"));

        assertEquals(1001, c.getId());
        assertEquals("Mathematics", c.getName());
        assertEquals(1, c.getAssignment().size());
        assertEquals(1, c.getStudent().size());

        c = reader.parseCourse(new JSONObject("{\"id\": 1002, \"name\": \"Mathematics\", \"description\": " +
                "\"Introduction to Mathematics\", \"students\": [], \"assignments\": []}"));
        assertEquals(1002, c.getId());
        assertEquals(0, c.getStudent().size());
        assertEquals(0, c.getAssignment().size());

    }

    @Test
    void testParseStudentList() {
        reader = new JsonReader("./data/testParseStudentList.json");
        try {
            List<Student> students = reader.readStudent();
            assertEquals(2, students.size());

            // Test the grades for the first student
            Student student1 = students.get(0);
            List<Grade> grades1 = student1.getGrades();
            assertEquals(2, grades1.size());
            assertEquals(95.0, grades1.get(0).getGrade());
            assertEquals(90.0, grades1.get(1).getGrade());

            // Test the grades for the second student
            Student student2 = students.get(1);
            List<Grade> grades2 = student2.getGrades();
            assertEquals(1, grades2.size());
            assertEquals(85.0, grades2.get(0).getGrade());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testParseGrade() {
        reader = new JsonReader("./data.testParseGrade.json");
        JSONObject gradeJson = new JSONObject("{\"grade\": 95.0, \"assignment\": {\"name\":" +
                " \"Math Assignment\", \"type\": \"Math\", \"id\": 101, \"duration\": 60, \"deadline\": 7, " +
                "\"worth\": 100, \"questions\": []}}");
        Grade grade = reader.parseGrade(gradeJson);
        assertEquals(95.0, grade.getGrade());
        Assignment assignment = grade.getAssignment();
        assertEquals("Math Assignment", assignment.getName());
        assertEquals("Math", assignment.getType());
        assertEquals(101, assignment.getId());
        assertEquals(60, assignment.getDuration());
        assertEquals(7, assignment.getDeadline());
        assertEquals(100, assignment.getWorth());
    }

    @Test
    void testParseAssignment() {
        reader = new JsonReader("./data.testParseAssignment.json");
        JSONObject assignmentJson = new JSONObject("{\"name\": \"Math Assignment\", \"type\":" +
                " \"Math\", \"id\": 101, \"duration\": 60, \"deadline\": 7, \"worth\": 100, \"questions\": " +
                "[{\"choices\":[],\"key\":\"\",\"weight\":0,\"quest\":\"\"}\n]}");
        Assignment assignment = reader.parseAssignment(assignmentJson);
        assertEquals("Math Assignment", assignment.getName());
        assertEquals("Math", assignment.getType());
        assertEquals(101, assignment.getId());
        assertEquals(60, assignment.getDuration());
        assertEquals(7, assignment.getDeadline());
        assertEquals(100, assignment.getWorth());
        assertEquals(1, assignment.getQuestionList().size());
    }

    @Test
    void testParseQuestion() {
        reader = new JsonReader("./data/testParseMc.json");
        JSONObject questionJsonMC = new JSONObject("{\"quest\": \"What is 2 + 2?\", \"key\": \"4\"," +
                " \"weight\": 5, \"choices\": [\"2\", \"3\", \"4\", \"5\"]}");
        Question questionMC = reader.parseQuestion(questionJsonMC);
        assertEquals("What is 2 + 2?", questionMC.getQuest());
        assertEquals("4", questionMC.getKey());
        assertEquals(5, questionMC.getWeight());
        assertEquals(true, questionMC instanceof MultipleChoice);

        reader = new JsonReader("./data/testParseLA.json");
        JSONObject questionJsonLA = new JSONObject("{\"quest\": \"Explain the concept of gravity.\", " +
                "\"key\": \"Complex explanation.\", \"weight\": 10}");
        Question questionLA = reader.parseQuestion(questionJsonLA);
        assertEquals("Explain the concept of gravity.", questionLA.getQuest());
        assertEquals("Complex explanation.", questionLA.getKey());
        assertEquals(10, questionLA.getWeight());
        assertEquals(true, questionLA instanceof LongAnswer);
    }
}
