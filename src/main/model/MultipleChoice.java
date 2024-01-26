package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;

// Multiple Choice question class that has a question as String, a list of choices, a key to check accuracy of answer
// and the weight of this question
public class MultipleChoice implements Question {
    private String quest;
    private List<String> choices;
    private String key;
    private int weight;

    // REQUIRES: weight >= 0
    // EFFECTS: construct MultipleChoice with an empty list of choices
    public MultipleChoice(String quest, String key, int weight) {
        this.quest = quest;
        choices = new ArrayList<>();
        this.key = key;
        this.weight = weight;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("quest", this.quest);
        json.put("key", this.key);
        json.put("weight", this.weight);

        JSONArray res = new JSONArray();
        for (String s : this.choices) {
            res.put(s);
        }
        json.put("choices", res);

        return json;
    }

    // EFFECTS: return true if provided answer is equals to this.key
    public boolean isCorrect(String answer) {
        return (answer.equals(this.key));
    }

    public List<String> getChoices() {
        return this.choices;
    }

    // MODIFIES: this
    // EFFECTS: add c to list of choices
    public void addChoice(String c) {
        this.choices.add(c);
    }

    // REQUIRES: index >= 0
    // MODIFIES: this
    // EFFECTS: add choice to the list of choice at the specific index
    public void addChoice(int index, String c) {
        this.choices.add(index, c);
    }

    // REQUIRES: from >= 0, to >= 0
    // MODIFIES: this
    // EFFECTS: change the position of choice at index from to index to in the list of choice
    public void changeChoicePosition(int from, int to) {
        String x = this.choices.get(from);
        this.choices.remove(x);
        this.choices.add(to, x);
    }

    @Override
    public String getQuest() {
        return this.quest;
    }

    @Override
    public void setQuest(String quest) {
        this.quest = quest;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
