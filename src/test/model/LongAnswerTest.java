package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LongAnswerTest {

    @Test
    void testLongAnswer() {
        LongAnswer longAnswer = new LongAnswer("What color's your Bugatti?", "Bronze", 5);
        assertEquals("What color's your Bugatti?", longAnswer.getQuest());
        assertEquals("Bronze", longAnswer.getKey());
        assertEquals(5, longAnswer.getWeight());

        longAnswer.setQuest("What car does Jack Do own?");
        longAnswer.setKey("Porsche Cayenne GTS");
        longAnswer.setWeight(30);

        assertEquals("What car does Jack Do own?", longAnswer.getQuest());
        assertEquals("Porsche Cayenne GTS", longAnswer.getKey());
        assertEquals(30, longAnswer.getWeight());
    }

    @Test
    void testIsCorrect() {
        LongAnswer longAnswer = new LongAnswer("What car does Jack Do own?", "Porsche Cayenne GTS", 30);
        assertTrue(longAnswer.isCorrect("Porsche Cayenne GTS"));
        assertFalse(longAnswer.isCorrect("Toyota Land Cruiser Lc300"));
    }
}
