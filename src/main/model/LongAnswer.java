package model;

// LongAnswer question type that has a question as String, a key and the weight of this question
public class LongAnswer implements Question {
    private String quest;
    private String key;
    private int weight;

    // REQUIRES: weight > = 0
    // EFFECTS: construct a LongAnswer
    public LongAnswer(String quest, String key, int weight) {
        this.quest = quest;
        this.key = key;
        this.weight = weight;
    }

    // EFFECTS: return true if the provided String is equals to this.key
    public boolean isCorrect(String answer) {
        return (answer.equals(this.key));
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
