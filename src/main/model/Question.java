package model;

import org.json.JSONObject;

// Question Interface. Question can be mulitple choice or Long answer
public interface Question {

    public String getQuest();

    public void setQuest(String quest);

    public boolean isCorrect(String answer);

    public String getKey();

    public void setKey(String key);

    public int getWeight();

    public void setWeight(int weight);

    public JSONObject toJson();
}
