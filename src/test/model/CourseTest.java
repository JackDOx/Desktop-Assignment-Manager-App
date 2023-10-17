package model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {

    @Test
    void testCourse() {
        Course course = new Course("Mathematics", "Advanced Mathematics Course");
        assertEquals("Mathematics", course.getName());
        assertEquals("Advanced Mathematics Course", course.getDescription());
        assertEquals(1002, course.getId());

        course.setName("Physics");
        course.setDescription("Basic Physics Course");
        assertEquals("Physics", course.getName());
        assertEquals("Basic Physics Course", course.getDescription());

        List<Student> los = new ArrayList<>();
        List<Assignment> loa = new ArrayList<>();

        Student student1 = new Student("John", "johnDoe", "123456");
        Student student2 = new Student("Jane", "janeDoe", "234567");
        Assignment assignment1 = new Assignment("Assignment 1", "Homework", 60, 7, 100);
        Assignment assignment2 = new Assignment("Assignment 2", "Quiz", 30, 5, 50);

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
}
