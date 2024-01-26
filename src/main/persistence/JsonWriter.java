package persistence;

import model.*;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.List;
import java.util.ArrayList;

import java.io.*;

// Represents a writer that writes JSON representation of workroom to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Teacher to file
    public void writeStudent(List<Student> los) {
        JSONArray array = new JSONArray();
        for (Student s : los) {
            JSONObject json = s.toJson();
            array.put(json);
        }
        JSONObject result = new JSONObject();
        result.put("students", array);
        saveToFile(result.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Teacher to file
    public void writeTeacher(List<Teacher> lot) {
        JSONArray array = new JSONArray();
        for (Teacher t : lot) {
            JSONObject json = t.toJson();
            array.put(json);
        }
        JSONObject result = new JSONObject();
        result.put("teachers", array);
        saveToFile(result.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}