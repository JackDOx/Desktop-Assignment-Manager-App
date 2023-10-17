package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Timeout(value=3)
public class TeacherTest {
    private Teacher testTeacher;

    @BeforeEach
    void runBefore() {
        testTeacher = new Teacher("james", "jamesDo", "123456");
    }

    @Test
    void testConstructor() {
        assertEquals("james", testTeacher.getName());
        assertEquals("teacher", testTeacher.getRole());
        assertEquals("123456", testTeacher.getPassword());
        assertEquals("jamesDo", testTeacher.getUserName());
        testTeacher.setName("JinDong");
        testTeacher.setRole("teacher");
        testTeacher.setUserName("james123");
        testTeacher.setPassword("1234567");
        List<Course> loc = testTeacher.getCourse();
        assertEquals(0, loc.size());
        assertEquals("JinDong", testTeacher.getName());
        assertEquals("teacher", testTeacher.getRole());
        assertEquals(1001, testTeacher.getId());
        assertEquals("1234567", testTeacher.getPassword());
        assertEquals("james123", testTeacher.getUserName());
    }

    @Test
    void testAddRemoveCourse() {
        Course math = new Course("Math", "Calculus 3 with something");
        testTeacher.addCourse(math);
        List<Course> loc = testTeacher.getCourse();
        assertEquals(1, loc.size());
        assertEquals(math, loc.get(0));
        testTeacher.removeCourse(math);
        loc = testTeacher.getCourse();
        assertEquals(0, loc.size());
    }


}
