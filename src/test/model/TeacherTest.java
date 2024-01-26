package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Timeout(value=3)
public class TeacherTest {
    private Teacher testTeacher;
    private Teacher testTeacher2;

    @BeforeEach
    void runBefore() {
        testTeacher = new Teacher("james", "jamesDo", "123456");
    }

    @Test
    void testConstructor() {
        testTeacher2 = new Teacher(1050, "jack",  "jackDo", "12345");
        assertEquals(1050, testTeacher2.getId());
        assertEquals("jack", testTeacher2.getName());
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
        assertEquals(1005, testTeacher.getId());
        assertEquals("1234567", testTeacher.getPassword());
        assertEquals("james123", testTeacher.getUserName());
    }

    @Test
    void testCourseToJsonArray() {
        Course math = new Course("Math", "Calculus 3 with something");
        testTeacher.addCourse(math);
        JSONArray jsonArray = testTeacher.courseToJsonArray();
        assertEquals(1, jsonArray.length());
        assertEquals(math.toJson().toString(), jsonArray.get(0).toString());
    }

    @Test
    void testToJson() {
        Course math = new Course("Math", "Calculus 3 with something");
        testTeacher.addCourse(math);
        JSONObject jsonObject = testTeacher.toJson();
        assertEquals(1004, jsonObject.getInt("id"));
        assertEquals("teacher", jsonObject.getString("role"));
        assertEquals("james", jsonObject.getString("name"));
        assertEquals("jamesDo", jsonObject.getString("userName"));
        assertEquals("123456", jsonObject.getString("password"));
        JSONArray expectedArray = new JSONArray();
        expectedArray.put(math.toJson());
        assertEquals(expectedArray.toString(), jsonObject.getJSONArray("courses").toString());
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
