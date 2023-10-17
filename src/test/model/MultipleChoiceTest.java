package model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MultipleChoiceTest {

    @Test
    void testMultipleChoice() {
        MultipleChoice multipleChoice = new MultipleChoice("What color's your Bugatti?", "2", 5);
        assertEquals("What color's your Bugatti?", multipleChoice.getQuest());
        assertEquals("2", multipleChoice.getKey());
        assertEquals(5, multipleChoice.getWeight());

        multipleChoice.setQuest("What car does Jack Do own?");
        multipleChoice.setKey("3");
        multipleChoice.setWeight(30);

        assertEquals("What car does Jack Do own?", multipleChoice.getQuest());
        assertEquals("3", multipleChoice.getKey());
        assertEquals(30, multipleChoice.getWeight());

        List<String> choices = new ArrayList<>();
        choices.add("Toyota Land Cruiser Lc300");
        choices.add("Lamborghini");
        choices.add("Porsche Cayenne GTS");
        multipleChoice.addChoice("Toyota Land Cruiser Lc300");
        multipleChoice.addChoice(1, "Lamborghini");
        multipleChoice.addChoice(2, "Porsche Cayenne GTS");
        assertEquals(choices, multipleChoice.getChoices());

        multipleChoice.changeChoicePosition(2, 0);
        List<String> newChoices = new ArrayList<>();
        newChoices.add("Porsche Cayenne GTS");
        newChoices.add("Toyota Land Cruiser Lc300");
        newChoices.add("Lamborghini");
        assertEquals(newChoices, multipleChoice.getChoices());
    }

    @Test
    void testIsCorrect() {
        MultipleChoice multipleChoice = new MultipleChoice("What car does Jack Do own?", "2", 5);
        assertTrue(multipleChoice.isCorrect("2"));
        assertFalse(multipleChoice.isCorrect("1"));
    }
}
