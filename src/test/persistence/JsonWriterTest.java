package persistence;

import model.Student;
import model.Teacher;
import org.junit.jupiter.api.Test;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("FileNotFoundException was expected");
        } catch (FileNotFoundException e) {
            // pass
        }
    }

    @Test
    void testWriteStudent() {
        try {
            List<Student> students = new ArrayList<>();
            students.add(new Student("John Doe", "johndoe", "password"));
            students.add(new Student("Jane Doe", "janedoe", "password"));

            JsonWriter writer = new JsonWriter("./data/testWriteStudent.json");
            writer.open();
            writer.writeStudent(students);
            writer.close();
        } catch (FileNotFoundException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriteTeacher() {
        try {
            List<Teacher> teachers = new ArrayList<>();
            teachers.add(new Teacher("James", "james123", "password"));
            teachers.add(new Teacher("JinDong", "jindong123", "password"));

            JsonWriter writer = new JsonWriter("./data/testWriteTeacher.json");
            writer.open();
            writer.writeTeacher(teachers);
            writer.close();
        } catch (FileNotFoundException e) {
            fail("Exception should not have been thrown");
        }
    }
}
