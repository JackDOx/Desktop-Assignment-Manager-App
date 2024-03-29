package model;

import org.json.JSONObject;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Timeout(value=3)
public class GradeTest {
    private Grade testGrade;
    private Assignment a;

    @BeforeEach
    void runBefore() {
        a = new Assignment("quiz1", "quiz", 15, 1630, 10);
        testGrade = new Grade(88.5, a);
    }

    @Test
    void testGrade() {
        assertEquals(88.5, testGrade.getGrade());
        assertEquals(a, testGrade.getAssignment());
    }
}
