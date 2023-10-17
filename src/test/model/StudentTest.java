package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Timeout(3)
public class StudentTest {
    private Student testStudent;
    private Assignment a;

    @BeforeEach
    void runBefore() {
        testStudent = new Student("Alice", "alice123", "password123");
        a = new Assignment("quiz1", "quiz", 15, 1630, 30);
    }

    @Test
    void testConstructor() {
        assertEquals("Alice", testStudent.getName());
        assertEquals("student", testStudent.getRole());
        assertEquals("password123", testStudent.getPassword());
        assertEquals("alice123", testStudent.getUserName());
        assertEquals(1004, testStudent.getId());
        testStudent.setName("Bob");
        testStudent.setRole("student");
        testStudent.setUserName("bob456");
        testStudent.setPassword("newPassword");

        List<Grade> grades = testStudent.getGrades();
        assertEquals(0, grades.size());

        List<Course> courses = testStudent.getCourse();
        assertEquals(0, courses.size());

        List<Assignment> assignments = testStudent.getAssignment();
        assertEquals(0, assignments.size());

        assertEquals("Bob", testStudent.getName());
        assertEquals("student", testStudent.getRole());
        assertEquals("newPassword", testStudent.getPassword());
        assertEquals("bob456", testStudent.getUserName());
    }

    @Test
    void testAddRemoveGrade() {
        Grade grade1 = new Grade(90.0, a, testStudent);
        Grade grade2 = new Grade(85.5, a, testStudent);

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
        Grade grade1 = new Grade(90.0, a, testStudent);
        Grade grade2 = new Grade(85.5, a, testStudent);

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
        Course course1 = new Course("Math", "Calculus 1");
        Course course2 = new Course("Science", "Physics 101");

        testStudent.addCourse(course1);
        List<Course> courses = testStudent.getCourse();
        assertEquals(1, courses.size());
        assertEquals(course1, courses.get(0));

        testStudent.addCourse(course2);
        courses = testStudent.getCourse();
        assertEquals(2, courses.size());
        assertEquals(course2, courses.get(1));

        testStudent.removeCourse(course1);
        courses = testStudent.getCourse();
        assertEquals(1, courses.size());
        assertEquals(course2, courses.get(0));
    }
}
