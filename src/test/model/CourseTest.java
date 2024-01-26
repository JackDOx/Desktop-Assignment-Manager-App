package model;

import org.json.JSONObject;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {
    private Course course;
    private Course course2;
    private Student student1;
    private Student student2;
    private Assignment assignment1;
    private Assignment assignment2;

    @BeforeEach
    void runBefore() {
        course = new Course("Math", "Advanced Mathematics Course");
        student1 = new Student("John", "johnDoe", "123456");
        student2 = new Student("Jane", "janeDoe", "234567");
        assignment1 = new Assignment("Assignment 1", "Homework", 60, 7, 100);
        assignment2 = new Assignment("Assignment 2", "Quiz", 30, 5, 50);
    }
    @Test
    void testCourse() {
        assertEquals("Math", course.getName());
        assertEquals("Advanced Mathematics Course", course.getDescription());
        assertEquals(1002, course.getId());
        course2 = new Course(1005, "Phys", "Electric Physic");
        assertEquals(1005, course2.getId());
        assertEquals("Phys", course2.getName());

        course.setName("Physics");
        course.setDescription("Basic Physics Course");
        assertEquals("Physics", course.getName());
        assertEquals("Basic Physics Course", course.getDescription());

        List<Student> los = new ArrayList<>();
        List<Assignment> loa = new ArrayList<>();

        course.addStudent(student1);
        course.addStudent(student1);
        los.add(student1);
        assertEquals(los, course.getStudent());
        assertEquals(0, student1.getAssignment().size());

        course.removeStudent(student1);
        course.removeStudent(student2);
        los.remove(student1);
        assertEquals(los, course.getStudent());

        course.removeAssignment(assignment1);
        assertEquals(0, course.getAssignment().size());

        course.addStudent(student1);
        los.add(student1);
        course.addAssignment(assignment1);
        loa.add(assignment1);
        assertEquals(loa, course.getAssignment());
        assertEquals(1, student1.getAssignment().size());
        course.addStudent(student2);
        los.add(student2);
        course.addAssignment(assignment2);
        loa.add(assignment2);
        assertEquals(2, student2.getAssignment().size());

        course.removeAssignment(assignment1);
        loa.remove(assignment1);
        assertEquals(loa, course.getAssignment());

        course.removeStudent(student1);
        assertEquals(0, student1.getAssignment().size());
    }

    @Test
    void testToJson() {
        JSONObject jsonObject = course.toJson();
        assertEquals("Math", jsonObject.getString("name"));
        assertEquals("Advanced Mathematics Course", jsonObject.getString("description"));
        assertEquals(1003, jsonObject.getInt("id"));
        assertEquals(0, jsonObject.getJSONArray("students").length());
        assertEquals(0, jsonObject.getJSONArray("assignments").length());
    }

    @Test
    void testStudentToJsonArray() {
        course.addStudent(student1);
        JSONArray jsonArray = course.studentToJsonArray();
        assertEquals(1, jsonArray.length());
        assertEquals(student1.getName(), jsonArray.getJSONObject(0).getString("name"));

        course.addStudent(student2);
        jsonArray = course.studentToJsonArray();
        assertEquals(2, jsonArray.length());
        assertEquals(student2.getName(), jsonArray.getJSONObject(1).getString("name"));
    }

    @Test
    void testAssignmentToJsonArray() {
        course.addAssignment(assignment1);
        JSONArray jsonArray = course.assignmentToJsonArray();
        assertEquals(1, jsonArray.length());
        assertEquals(assignment1.getName(), jsonArray.getJSONObject(0).getString("name"));

        course.addAssignment(assignment2);
        jsonArray = course.assignmentToJsonArray();
        assertEquals(2, jsonArray.length());
        assertEquals(assignment2.getName(), jsonArray.getJSONObject(1).getString("name"));
    }
}
