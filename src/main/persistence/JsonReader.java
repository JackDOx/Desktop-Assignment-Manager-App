package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import model.*;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads teachers from file and returns it;
    // throws IOException if an error occurs reading data from file
    public List<Teacher> readTeacher() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTeacher(jsonObject);
    }

    public List<Student> readStudent() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStudentList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses student from JSON object and returns it
    public List<Student> parseStudentList(JSONObject jsonObject) {
        JSONArray lop = jsonObject.getJSONArray("students");
        List<Student> result = new ArrayList<>();
        for (Object s : lop) {
            result.add(parseStudent((JSONObject) s));
        }

        return result;
    }

    // EFFECTS: parses student from JSON object and returns it
    @SuppressWarnings("methodlength")
    public Student parseStudent(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String userName = jsonObject.getString("userName");
        String password = jsonObject.getString("password");
        String role = jsonObject.getString("role");
        int id = jsonObject.getInt("id");

        Student s = new Student(id, name, userName, password);
        JSONArray gradesArray = jsonObject.getJSONArray("grades");
        for (Object grade : gradesArray) {
            s.addGrade(parseGrade((JSONObject) grade));
        }

        JSONArray coursesArray = jsonObject.getJSONArray("courses");
        for (int i = 0; i < coursesArray.length(); i++) {
            s.addCourseId(coursesArray.getInt(i));
        }

        JSONArray assignmentsArray = jsonObject.getJSONArray("assignments");
        for (Object assignment : assignmentsArray) {
            s.addAssignment(parseAssignment((JSONObject) assignment));
        }
        return s;
    }

    @SuppressWarnings("methodlength")
    public List<Teacher> parseTeacher(JSONObject jsonObject) {
        JSONArray lop = jsonObject.getJSONArray("teachers");
        List<Teacher> result = new ArrayList<>();
        for (int o = 0; o < lop.length(); o++) {
            JSONObject json = lop.getJSONObject(o);
            String name = json.getString("name");
            String userName = json.getString("userName");
            String password = json.getString("password");
            String role = json.getString("role");
            int id = json.getInt("id");

            Teacher t = new Teacher(id, name, userName, password);
            result.add(t);

            JSONArray coursesArray = json.getJSONArray("courses");
            for (int i = 0; i < coursesArray.length(); i++) {
                t.addCourse(parseCourse(coursesArray.getJSONObject(i)));
            }

        }
        return result;
    }


    // Assuming you have similar methods to parse Grade, Course, and Assignment objects
    public Grade parseGrade(JSONObject jsonObject) {
        double grade = jsonObject.getDouble("grade");
        Assignment assignment = parseAssignment(jsonObject.getJSONObject("assignment"));
        Grade g = new Grade(grade, assignment);
        return g; // Placeholder for your actual implementation
    }

    public Course parseCourse(JSONObject jsonObject) {
        int id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");

        Course c = new Course(id, name, description);

        JSONArray students = jsonObject.getJSONArray("students");
        for (Object s : students) {
            c.addStudent(parseStudent((JSONObject) s));
        }
        JSONArray assignmentsArray = jsonObject.getJSONArray("assignments");
        for (Object assignment : assignmentsArray) {
            c.addAssignment(parseAssignment((JSONObject) assignment));
        }
        return c; // Placeholder for your actual implementation
    }

    public Assignment parseAssignment(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String type = jsonObject.getString("type");
        int id = jsonObject.getInt("id");
        int duration = jsonObject.getInt("duration");
        int deadline = jsonObject.getInt("deadline");
        int worth = jsonObject.getInt("worth");

        Assignment result = new Assignment(id, name, type, duration, deadline,worth);

        JSONArray questions = jsonObject.getJSONArray("questions");
        for (Object q : questions) {
            result.addQuestion(parseQuestion((JSONObject) q));
        }
        return result; // Placeholder for your actual implementation
    }

    public Question parseQuestion(JSONObject jsonObject) {
        Question q = null;
        try {
            String quest = jsonObject.getString("quest");
            String key = jsonObject.getString("key");
            int weight = jsonObject.getInt("weight");
            MultipleChoice mc = new MultipleChoice(quest, key, weight);
            JSONArray choices = jsonObject.getJSONArray("choices");
            for (int i = 0; i < choices.length(); i++) {
                mc.addChoice(choices.getString(i));
            }
            q = mc;
        } catch (JSONException e) {
            String quest = jsonObject.getString("quest");
            String key = jsonObject.getString("key");
            int weight = jsonObject.getInt("weight");
            LongAnswer la = new LongAnswer(quest, key, weight);
            q = la;
        }
        return q;
    }

}
