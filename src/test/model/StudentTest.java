package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@Timeout(3)
public class StudentTest {
    private Student testStudent;
    private Student testStudent2;
    private Assignment a;

    @BeforeEach
    void runBefore() {
        testStudent = new Student("Alice", "alice123", "password123");
        a = new Assignment("quiz1", "quiz", 15, 1630, 30);
    }

    @Test
    void testConstructor() {
        testStudent2 = new Student(1050, "Mai", "mai123", "password123");
        assertEquals("Mai", testStudent2.getName());
        assertEquals("student", testStudent2.getRole());
        assertEquals(1050, testStudent2.getId());
        assertEquals("Alice", testStudent.getName());
        assertEquals("student", testStudent.getRole());
        assertEquals("password123", testStudent.getPassword());
        assertEquals("alice123", testStudent.getUserName());
        assertEquals(1009, testStudent.getId());
        testStudent.setName("Bob");
        testStudent.setRole("student");
        testStudent.setUserName("bob456");
        testStudent.setPassword("newPassword");

        List<Grade> grades = testStudent.getGrades();
        assertEquals(0, grades.size());

        List<Integer> courses = testStudent.getCourseId();
        assertEquals(0, courses.size());

        List<Assignment> assignments = testStudent.getAssignment();
        assertEquals(0, assignments.size());

        assertEquals("Bob", testStudent.getName());
        assertEquals("student", testStudent.getRole());
        assertEquals("newPassword", testStudent.getPassword());
        assertEquals("bob456", testStudent.getUserName());
    }

    @Test
    void testEquals() {
        testStudent2 = new Student(1009, "Mai", "mai123", "password123");
        Student testStudent3 = new Student(1009, "Mai", "mai123", "password123");
        assertFalse(testStudent.equals(testStudent2));
        assertEquals(1009, testStudent3.getId());
        assertEquals(1009, testStudent2.getId());
        assertTrue(testStudent3.equals(testStudent2));
        assertFalse(testStudent3.equals(a));
        assertFalse(testStudent.equals(null));
        assertEquals(Objects.hash(1009), testStudent2.hashCode());
    }

    @Test
    void testToJson() {
        testStudent.addCourseId(1000);
        testStudent.addCourseId(1002);
        testStudent.addAssignment(a);
        Grade grade1 = new Grade(90.0, a);
        testStudent.addGrade(grade1);
        JSONObject json = testStudent.toJson();

        assertEquals("Alice", json.getString("name"));
        assertEquals("student", json.getString("role"));
        assertEquals("alice123", json.getString("userName"));
        assertEquals("password123", json.getString("password"));
        assertEquals(1008, json.getInt("id"));

        JSONArray gradesArray = json.getJSONArray("grades");
        assertEquals(1, gradesArray.length());
        JSONObject gradeObject = gradesArray.getJSONObject(0);
        assertEquals(90.0, gradeObject.getDouble("grade"), 0.01);

        JSONArray coursesArray = json.getJSONArray("courses");
        assertEquals(2, coursesArray.length());
        assertEquals(1000, coursesArray.getInt(0));
        assertEquals(1002, coursesArray.getInt(1));

        JSONArray assignmentsArray = json.getJSONArray("assignments");
        assertEquals(1, assignmentsArray.length());
        JSONObject assignmentObject = assignmentsArray.getJSONObject(0);
        assertEquals("quiz1", assignmentObject.getString("name"));
        assertEquals("quiz", assignmentObject.getString("type"));
        assertEquals(15, assignmentObject.getInt("duration"));
        assertEquals(1630, assignmentObject.getInt("deadline"));
        assertEquals(30, assignmentObject.getInt("worth"));
    }

    @Test
    void testGradesToJsonArray() {
        Grade grade1 = new Grade(90.0, a);
        testStudent.addGrade(grade1);
        JSONArray gradesArray = testStudent.gradesToJsonArray();

        assertEquals(1, gradesArray.length());
        JSONObject gradeObject = gradesArray.getJSONObject(0);
        assertEquals(90.0, gradeObject.getDouble("grade"), 0.01);
    }

    @Test
    void testCoursesToJsonArray() {
        testStudent.addCourseId(1000);
        testStudent.addCourseId(1002);
        JSONArray coursesArray = testStudent.coursesToJsonArray();

        assertEquals(2, coursesArray.length());
        assertEquals(1000, coursesArray.getInt(0));
        assertEquals(1002, coursesArray.getInt(1));
    }

    @Test
    void testAssignmentsToJsonArray() {
        testStudent.addAssignment(a);
        JSONArray assignmentsArray = testStudent.assignmentsToJsonArray();

        assertEquals(1, assignmentsArray.length());
        JSONObject assignmentObject = assignmentsArray.getJSONObject(0);
        assertEquals("quiz1", assignmentObject.getString("name"));
        assertEquals("quiz", assignmentObject.getString("type"));
        assertEquals(15, assignmentObject.getInt("duration"));
        assertEquals(1630, assignmentObject.getInt("deadline"));
        assertEquals(30, assignmentObject.getInt("worth"));
    }



    @Test
    void testAddRemoveGrade() {
        Grade grade1 = new Grade(90.0, a);
        Grade grade2 = new Grade(85.5, a);

        testStudent.addGrade(grade1);
        List<Grade> grades = testStudent.getGrades();
        assertEquals(1, grades.size());
        assertEquals(grade1, grades.get(0));

        testStudent.addGrade(1, grade2);
        grades = testStudent.getGrades();
        assertEquals(2, grades.size());
        assertEquals(grade2, grades.get(1));

        testStudent.removeGrade(grade1);
        grades = testStudent.getGrades();
        assertEquals(1, grades.size());
        assertEquals(grade2, grades.get(0));
    }

    @Test
    void testCalculateOverall() {
        assertEquals(0, testStudent.calculateOverall());
        Grade grade1 = new Grade(90.0, a);
        Grade grade2 = new Grade(85.5, a);

        testStudent.addGrade(grade1);
        testStudent.addGrade(grade2);

        double overall = testStudent.calculateOverall();
        assertEquals(87.75, overall, 0.01);
    }

    @Test
    void testAddRemoveAssignment() {
        Assignment assignment1 = new Assignment("quiz1", "quiz", 15, 1630, 30);
        Assignment assignment2 = new Assignment("quiz2", "quiz", 15, 1630, 30);

        testStudent.addAssignment(assignment1);
        List<Assignment> assignments = testStudent.getAssignment();
        assertEquals(1, assignments.size());
        assertEquals(assignment1, assignments.get(0));

        testStudent.addAssignment(assignment2);
        assignments = testStudent.getAssignment();
        assertEquals(2, assignments.size());
        assertEquals(assignment2, assignments.get(1));

        testStudent.removeAssignment(assignment1);
        assignments = testStudent.getAssignment();
        assertEquals(1, assignments.size());
        assertEquals(assignment2, assignments.get(0));
    }

    @Test
    void testAddRemoveCourse() {
        testStudent.addCourseId(1000);
        List<Integer> courses = testStudent.getCourseId();
        assertEquals(1, courses.size());
        assertEquals(1000, courses.get(0));

        testStudent.addCourseId(1002);
        courses = testStudent.getCourseId();
        assertEquals(2, courses.size());
        assertEquals(1002, courses.get(1));

        testStudent.removeCourseId(1002);
        courses = testStudent.getCourseId();
        assertEquals(1, courses.size());
        assertEquals(1000, courses.get(0));
    }
}
